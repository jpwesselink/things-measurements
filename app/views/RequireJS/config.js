requirejs.config({
    baseUrl: "/scripts",
    paths : {
        "jquery" : "jquery-1.8.1",
        "jquery.fittext" : "jquery/jquery.fittext",
        "jquery.timeago" : "jquery/jquery.timeago",
        "jquery.timeago.settings.strings" : "jquery/jquery.timeago.settings.strings",
        "jquery.cookie" : "jquery/jquery.cookie",
        "jquery.geolocation" : "jquery/jquery.geolocation",
        "jquery.peity" : "jquery/jquery.peity",
        "heatmap" : "heatmap/heatmap",
        "heatmap-gmaps" : "heatmap/heatmap-gmaps",
        "highlight" : "highlight.pack",
        "waypoints" : "jquery/waypoints",
        "waypoints-sticky" : "jquery/waypoints-sticky",
        "ko.mapping" : "ko/knockout.mapping-latest",
        "ko.observableDictionary" : "ko/ko.observableDictionary",
        "ko.underscoreTemplateEngine" : "ko/ko.underscoreTemplateEngine",
        "ko.bindingHandlers.peity" : "ko/ko.bindingHandlers.peity",
        "ko.bindingHandlers.timeagogo" : "ko/ko.bindingHandlers.timeagogo",
        "ko.bindingHandlers.highlight" : "ko/ko.bindingHandlers.highlight",
        "ko.bindingHandlers.mapMarker" : "ko/ko.bindingHandlers.mapMarker",
        "knockout" : "knockout-2.2.1",
        "markerwithlabel" : "google/markerwithlabel",
        "markerclusterer" : "google/markerclusterer",
        "infobox" : "google/infobox",
        "google-maps-v3" : "google/google-maps-v3",
        "bootstrap-affix" : "/js/bootstrap-affix",
        "bootstrap-transition" : "/js/bootstrap-transition",
        "bootstrap-alert" : "/js/bootstrap-alert",
        "bootstrap-modal" : "/js/bootstrap-modal",
        "bootstrap-dropdown" : "/js/bootstrap-dropdown",
        "bootstrap-scrollspy" : "/js/bootstrap-scrollspy",
        "bootstrap-tab" : "/js/bootstrap-tab",
        "bootstrap-tooltip" : "/js/bootstrap-tooltip",
        "bootstrap-popover" : "/js/bootstrap-popover",
        "bootstrap-button" : "/js/bootstrap-button",
        "bootstrap-collapse" : "/js/bootstrap-collapse",
        "bootstrap-carousel" : "/js/bootstrap-carousel",
        "bootstrap-typeahead" : "/js/bootstrap-typeahead",
        "underscore-string": "underscore/underscore.string"
    },
    shim : {
        "underscore" : {
          exports : "_"  
        },
        "underscore-string" : {
        	deps: ["underscore"]
        },
        "knockout" : {
            deps: ["jquery"]
        },
        "ko.mapping" : {
        	deps: ["knockout"]
        },
        "ko.underscoreTemplateEngine" : {
            deps : ["knockout", "underscore"]
        },
        "ko.bindingHandlers.timeagogo" : {
        	deps : ["knockout", "jquery.timeago", "jquery.timeago.settings.strings"]
        },
        "ko.bindingHandlers.highlight" : {
        	deps : ["knockout", "highlight"]
        },
        "ko.bindingHandlers.peity" : {
            deps : ["knockout", "jquery.peity", "underscore"]
        },
        "ko.bindingHandlers.mapMarker" : {
            deps : ["knockout", "google-maps-v3"]
        },
        "heatmap" : {
        	deps: ["jquery"]
        },
        "heatmap-gmaps" : {
        	deps: ["heatmap"]
        },
        "waypoints-sticky" : {
        	deps: ["waypoints"]
        },
        "jquery.timeago" : {
            deps: ["jquery"]
        },
        "jquery.timeago.settings.strings" : {
        	deps: ["jquery.timeago"]
        },
        "jquery.geolocation" : {
        	deps: ["jquery"],
        	exports : "jQuery.geolocation"
        },
    	"jquery.fittext" : {
    		deps: ["jquery"],
    	},
        "jquery.peity" : {
            deps: ["jquery"],
            exports : "jQuery.fn.peity"
        },
        "google-maps-v3" : {
            deps : ["async!https://maps.googleapis.com/maps/api/js?key=${play.configuration.getProperty('google.maps.api.key', '')}&sensor=false"]
        },
        "markerwithlabel" : { 
            deps: ["google-maps-v3"],
            exports: "MarkerWithLabel"
        },
        "markerclusterer" : { 
            deps: ["google-maps-v3"],
            exports: "MarkerClusterer"
        },
        "infobox" : { 
            deps: ["google-maps-v3"],
            exports: "InfoBox"
        },
        "bootstrap-affix" : {
            deps: ["jquery"],
            exports : "jQuery.fn.affix"
        },
        "bootstrap-transition" : {
            deps: ["jquery"],
            exports : "jQuery.support.transition"
        },
        "bootstrap-alert" : {
            deps: ["jquery"],
            exports : "jQuery.fn.alert"
        },
        "bootstrap-modal" : {
            deps: ["jquery"],
            exports : "jQuery.fn.modal"
        },
        "bootstrap-dropdown"  : {
            deps: ["jquery"],
            exports : "jQuery.fn.dropdown"
        },
        "bootstrap-scrollspy" : {
            deps: ["jquery"],
            exports : "jQuery.fn.scrollspy"
        },
        "bootstrap-tab" : {
            deps: ["jquery"],
            exports : "jQuery.fn.tab"
        },
        "bootstrap-tooltip" : {
            deps: ["jquery"],
            exports : "jQuery.fn.tooltip"
        },
        "bootstrap-popover" : {
            deps: ["jquery", "bootstrap-tooltip"],
            exports : "jQuery.fn.popover"
        },
        "bootstrap-button" : {
            deps: ["jquery"],
            exports : "jQuery.fn.button"
        },
        "bootstrap-collapse" : {
            deps: ["jquery"],
            exports : "jQuery.fn.collapse"
        },
        "bootstrap-carousel" : {
            deps: ["jquery"],
            exports : "jQuery.fn.carousel"
        },
        "bootstrap-typeahead" : {
            deps: ["jquery"],
            exports : "jQuery.fn.typeahead"
        },
        "bootstrap" : {
            deps : ["jquery"]
        },
        "IOT" : {
            deps : ["domReady", "jquery", "knockout", "ko.mapping", "underscore-string", "ko.observableDictionary", "ko.underscoreTemplateEngine",  "ko.bindingHandlers.mapMarker", "ko.bindingHandlers.highlight", "ko.bindingHandlers.peity", "markerwithlabel", "markerclusterer", "infobox", "jquery.geolocation", "waypoints-sticky", "jquery.fittext", "jquery.cookie", "ko.bindingHandlers.timeagogo", "highlight"],
        }
    }
}); 