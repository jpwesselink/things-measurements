requirejs.config({
    baseUrl: "/scripts",
    paths : {
        "jquery" : "jquery-1.8.1",
        "jquery.geolocation" : "jquery/jquery.geolocation",
        "jquery.peity" : "jquery/jquery.peity",
        "ko.observableDictionary" : "ko/ko.observableDictionary",
        "ko.underscoreTemplateEngine" : "ko/ko.underscoreTemplateEngine",
        "ko.bindingHandlers.peity" : "ko/ko.bindingHandlers.peity",
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
        "bootstrap-typeahead" : "/js/bootstrap-typeahead"
    },
    shim : {
        "underscore" : {
          exports : "_"  
        },
        "knockout" : {
            deps: ["jquery"]
        },
        "ko.underscoreTemplateEngine" : {
            deps : ["knockout", "underscore"]
        },
        "ko.bindingHandlers.peity" : {
            deps : ["knockout", "jquery.peity", "underscore"]
        },
        "ko.bindingHandlers.mapMarker" : {
            deps : ["knockout", "google-maps-v3"]
        },
        "jquery.geolocation" : {
            deps: ["jquery"],
            exports : "jQuery.geolocation"
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
            deps : ["domready", "jquery", "knockout", "ko.observableDictionary", "ko.underscoreTemplateEngine",  "ko.bindingHandlers.mapMarker", "ko.bindingHandlers.peity", "markerwithlabel", "markerclusterer", "infobox", "jquery.geolocation"]
        }
    }
}); 