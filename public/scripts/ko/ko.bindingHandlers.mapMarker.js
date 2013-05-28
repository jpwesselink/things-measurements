require(['knockout', 'jquery', 'google-maps-v3', 'underscore', 'markerwithlabel'], function(ko, $, maps, _, MarkerWithLabel) {
    var createMarkerImage = function (marker, percentages) {
            var values = _.values(percentages).join(",");
           
            var markerImage = new google.maps.MarkerImage(' http://chart.apis.google.com/chart?cht=p&chd=t:' + values + '&chs=100x100&chf=bg,s,ffffff00',
               new google.maps.Size(100, 100),
               new google.maps.Point(0, 0),
               new google.maps.Point(50, 100)
            );
            
            var shadow = new google.maps.MarkerImage(
              'images/shadow.png',
              new google.maps.Size(154,100),
              new google.maps.Point(0,0),
              new google.maps.Point(50,100)
            );
            
            var shape = {
              coord: [56,11,61,12,64,13,66,14,68,15,70,16,72,17,73,18,74,19,75,20,77,21,78,22,79,23,79,24,80,25,81,26,82,27,82,28,83,29,84,30,84,31,85,32,85,33,86,34,86,35,87,36,87,37,87,38,88,39,88,40,88,41,88,42,88,43,89,44,89,45,89,46,89,47,89,48,89,49,89,50,89,51,89,52,89,53,89,54,89,55,89,56,88,57,88,58,88,59,88,60,87,61,87,62,87,63,86,64,86,65,86,66,85,67,85,68,84,69,84,70,83,71,82,72,82,73,81,74,80,75,79,76,78,77,78,78,76,79,75,80,74,81,73,82,71,83,70,84,68,85,66,86,64,87,61,88,56,89,44,89,40,88,36,87,34,86,32,85,30,84,29,83,27,82,26,81,25,80,24,79,22,78,22,77,21,76,20,75,19,74,18,73,18,72,17,71,16,70,16,69,15,68,15,67,14,66,14,65,13,64,13,63,13,62,12,61,12,60,12,59,12,58,12,57,11,56,11,55,11,54,11,53,11,52,11,51,11,50,11,49,11,48,11,47,11,46,11,45,11,44,12,43,12,42,12,41,12,40,13,39,13,38,13,37,13,36,14,35,14,34,15,33,15,32,16,31,16,30,17,29,18,28,18,27,19,26,20,25,21,24,21,23,22,22,23,21,25,20,26,19,27,18,29,17,30,16,32,15,34,14,37,13,40,12,44,11,56,11],
              type: 'poly'
            };
            console.log(values);
            marker.setIcon(markerImage);
            marker.setShadow(shadow);
    };
    ko.bindingHandlers.mapMarker = {
        init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var options = valueAccessor();
            var location = options.location;
            var lat = ko.utils.unwrapObservable(location.lat);
            var lng = ko.utils.unwrapObservable(location.lng);
            var name = ko.utils.unwrapObservable(location.name);
            var percentages = ko.utils.unwrapObservable(location.percentages);
            var position = new google.maps.LatLng(lat, lng);
            var marker = new MarkerWithLabel({
                position: position,
                title: "name",
                labelContent: location.title(),
                labelClass: "mood-meter-location-label",
                labelAnchor: new google.maps.Point(50, 0)
            });
          options.markerClusterer.addMarker(marker);
          
            createMarkerImage(marker, location.percentages);
          bindingContext.$root.markerCache.set(name, marker)
        },
        update : function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var options = valueAccessor();
            var location = options.location;
            var name = ko.utils.unwrapObservable(location.name);
            var marker = bindingContext.$root.markerCache.get(name, marker);
             var percentages = ko.utils.unwrapObservable(location.percentages);
            //createMarkerImage(marker, location.percentages);
        }
        
    }  
});