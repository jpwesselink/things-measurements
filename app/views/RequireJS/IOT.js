define("IOT", ["jquery", "knockout", "underscore", "markerwithlabel", "infobox", "markerclusterer", "heatmap-gmaps", "bootstrap"], function($, ko, _, MarkerLabel, InfoBox, MarkerClusterer){
    var IOT;
    
    IOT = {
        data : {
            locations : []
        },
        actions : {},
        markerImages : {
            smileys: ['images/red-devastated-tiny-shadow.png', 'images/blue-unhappy-tiny-shadow.png', 'images/green-content-tiny-shadow.png', 'images/yellow-happy-tiny-shadow.png']
        },
        markers : {
            smileys : [],
            location : new google.maps.MarkerImage('/public/images/red-devastated-empty.png',
               new google.maps.Size(200, 200),
               new google.maps.Point(0, 0),
               new google.maps.Point(0, 200)
            ),
            locations : {}  
        },
        timeoutId : null,
        viewModel : {
        	socketData : ko.observableArray(),
        	hasGeoEnabled : ko.observable(false),
            lat : ko.observable(),
            lng: ko.observable(),
            value : ko.observable(),
            votedAt : ko.observable(),
            percentages : ko.observableDictionary({0:0,1:0,2:0,3:0}),
            moods : ko.observableArray(['devastated', 'general-sadness', 'pretty-happy', 'happiness-bordering-to-incontinence']),
            types : ko.observableArray(['danger', 'info', 'success', 'warning']),
            vote : function (value){
                IOT.vote(value);
            },
            map : {},
            markerClusterer : {},
            markers : ko.observableDictionary(),
            markerCache : ko.observableDictionary(),
            locations : ko.observableDictionary(),
            locationPercentages : ko.observableDictionary(),
            Marker : function (options) {
                this.lat = ko.observable(options.lat);
                this.lng = ko.observable(options.lng);
                this.name = ko.observable(options.name);
                this.title = ko.observable(options.title);
                this.percentages = options.percentages;
            }
        },
        init : function (options) {
        	this.viewModel.widthPercentages = ko.computed(function(){
        		var widthPercentages = this.percentages.values();
        		var cumulative = 0;
        		var that = this;
				_.map(this.percentages.values(), function(percentage, index, scope){
					if(index < scope.length-1){
						cumulative+=percentage;
					}
					return cumulative;
				});
				widthPercentages[widthPercentages.length-1] = 100 - cumulative;
        		return widthPercentages;
        	}, this.viewModel);
        	this.viewModel.socketDataShortened = ko.computed(function(){
        		return this.socketData().reverse().slice(0, 3);
        		
        	}, this.viewModel);
            if(options.data.locations){
                this.data.locations = options.data.locations;
            }
            if(options.actions){
                $.extend(this.actions, options.actions);
            }
            this.createMarkers();
            this.initMap();
           
            ko.applyBindings(this.viewModel);
            this.determineGeo();
            if(!this.initSocket()){
                this.initPolling();
            }
        },
        initMap : function(){
            var mapOptions = {
            		scrollwheel: false,	
            		
        		draggable: false,
                center: new google.maps.LatLng(-34.397, 150.644),
                zoom: 18,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                styles : [
                    {
                        "featureType": "landscape",
                        "stylers": [
                            { "hue": "#FFA800" },
                            { "saturation": 0 },
                            { "lightness": 0 },
                            { "gamma": 1 }
                        ]
                    },
                    {
                        "featureType": "road.highway",
                        "stylers": [
                            { "hue": "#FFB900" },
                            { "saturation": 0 },
                            { "lightness": -1.4210854715202004e-14 },
                            { "gamma": 1 }
                        ]
                    },
                    {
                        "featureType": "road.arterial",
                        "stylers": [
                            { "hue": "#FFB300" },
                            { "saturation": 0 },
                            { "lightness": 0 },
                            { "gamma": 1 }
                        ]
                    },
                    {
                        "featureType": "road.local",
                        "stylers": [
                            { "hue": "#FFA800" },
                            { "saturation": 0 },
                            { "lightness": 0 },
                            { "gamma": 1 }
                        ]
                    },
                    {
                        "featureType": "water",
                        "stylers": [
                            { "hue": "#FFC300" },
                            { "saturation": 0 },
                            { "lightness": 0 },
                            { "gamma": 1 }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "stylers": [
                            { "hue": "#FF8C00" },
                            { "saturation": 0 },
                            { "lightness": 0 },
                            { "gamma": 1 }
                        ]
                    }
                ]
            };
            
            map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
            var markerClusterer = new MarkerClusterer(map);
            this.viewModel.map = map;
            this.viewModel.markerClusterer = markerClusterer;
            
            google.maps.event.addListener(map, "bounds_changed", function() {
            	var bounds = map.getBounds();
        	});
        },
        
        createMarkers : function () {
            var that = this;
            $.each(this.markerImages.smileys, function (index, markerImage){
                that.markers.smileys.push(
                    new google.maps.MarkerImage(markerImage,
                       new google.maps.Size(49, 32),
                       new google.maps.Point(0,0),
                       new google.maps.Point(0, 32)
                   )
               )
            });
        },
        
        initSocket : function () {
            return this.openSocket(this.actions.MeasurementsFeed.FeedSocket.join());
        },
        openSocket : function (url) {
            var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
            if(!WS){
                return false;
            }
            var socket = new WS(url);
            var that = this;
            socket.onmessage = function(response) {
            	IOT.viewModel.socketData.push(response.data);
                var socketEvent = $.parseJSON(response.data);
                switch(socketEvent["class"]){
                    case "models.Feed$Vote":
                        that.parsePercentages(socketEvent.percentages);
                        if(socketEvent.measurement){
                            that.parseLatLng(socketEvent.measurement);
                        }
                    break;
                    case "models.Feed$LocationEvent":
                        
                    break;
                    case "models.Measurement":
                        that.parseLatLng(socketEvent);
                    break;
                }
               
            }
            socket.onopen = function () {
                that.fetchPercentages();
            }
            socket.onerror = function () {
                
            }
            socket.onclose = function () {
                // wait a bit
                socket.close();
                setTimeout(function (){
                    that.openSocket(url);
                }, 5000);
            }
            return socket;
        },
        parseLatLng : function (measurement){
            
            if(measurement){
                var location;
                if(this.currentMarker){
                    this.currentMarker.setMap(null);
                }
                if(measurement.location != undefined){
                    location = new google.maps.LatLng(measurement.location.lat, measurement.location.lng);
                    this.viewModel.locations.set(measurement.location.slug, measurement.location);
                    this.viewModel.locationPercentages.set(measurement.location.slug, measurement.location.percentages);
                    
                    /*
                     * 
                    var icon = this.markers.location;
                    var infowindow = new InfoBox({
                        content: "contentString",
                        boxClass: "info-box",
                        pixelOffset: new google.maps.Size(0, -100),
                        
                        infoBoxClearance: new google.maps.Size(10, 10),
                        isHidden: false,
                        pane: "floatPane",
                        enableEventPropagation: false
                    });
                    this.currentMarker = new google.maps.Marker({
                        position: location,
                        map: map
                    });
                    
                    infowindow.open(map, this.currentMarker);
                     */
                } else if(measurement.lat && measurement.lng) {
                    location = new google.maps.LatLng(measurement.lat, measurement.lng);
                    var icon = this.markers.smileys[measurement.value];
                    
                    this.currentMarker = new MarkerWithLabel({
                        position: location,
                        map: map,
                        icon: icon,
                        labelContent: i18n('label.sentiment.' + measurement.value),
                        labelAnchor: new google.maps.Point(0, 0),
                        labelClass: 'marker-label'
                    });
                
                } else {
                    return false;
                }
                a = location;
                map.panTo(location);
                map.setCenter(location);
            }
        },
        parsePercentages : function (percentages){
            for(var i = 0; i<4; i++){
                var percentage = percentages[i];
                if(!percentage){
                    percentage = 0;
                }
                this.viewModel.percentages.set(i, Math.round(percentage * 100)/100);
            }
        },
        initPolling : function () {
            this.fetchPercentages(true);
        },
        fetchPercentages : function (recurrent) {
            if(this.timeoutId){
                clearTimeout(this.timeoutId);
            }
            var that = this;
            $.ajax({
                url: this.actions.Measurements.percentages(),
                type : "GET",
                dataType: "json"
            }).done(function(data, textStatus, jqXHR){
                that.parsePercentages(data);
            }).always(function(){
                if(recurrent){
                    that.timeoutId = setTimeout(function(){
                        that.fetchPercentages();
                    }, 5000);
                }
            });
        },
        determineGeo : function () {
            var that = this;
            $.geolocation.get({
                success : function(geoLocation) {
                    that.viewModel.lat(geoLocation.coords.latitude);
                    that.viewModel.lng(geoLocation.coords.longitude);
                    that.viewModel.hasGeoEnabled(true);
                },
                error : function() {
                	that.viewModel.hasGeoEnabled(false);
                }
            });
        },
        vote : function (v){
            var that = this;
            $.ajax({
                url: this.actions.Application.vote({
                    value: v+"",
                    lng: that.viewModel.lng() + "",
                    lat: that.viewModel.lat() + ""
                }),
                type : "POST"
            }).fail(function(){
                
            }).done(function(data, textStatus, jqXHR){
                that.viewModel.value(v);
            });
        }
    };
    
    return IOT;
});
