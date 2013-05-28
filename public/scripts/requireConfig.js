requirejs.config({
    baseUrl: "/scripts",
    paths : {
        "IOT" : "@{RequireJS.IOT()}",
        "jquery" : "jquery",
        "jquery-geolocation" : "jquery/jquery.geolocation",
        "ko.observableDictionary" : "ko/ko.observableDictionary",
        "knockout" : "knockout-2.2.1",
        "markerwithlabel" : "google/markerwithlabel",
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
        "knockout" : {
            deps: ["jquery"]
        },
        "jquery-geolocation" : {
            deps: ["jquery"],
            exports : "jQuery.geolocation"
        },
        "google-maps-v3" : {
            deps : ["async!https://maps.googleapis.com/maps/api/js?key=${play.configuration.getProperty('google.maps.api.key', '')}&sensor=false"]
        },
        "markerwithlabel" : { 
            deps: ["google-maps-v3"],
            exports: "MarkerLabel"
        },
        "infobox" : ["google-maps-v3"],
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
            deps: ["jquery"],
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
        "IOT" : {
            deps : ["jquery", "knockout", "ko.observableDictionary", "markerwithlabel", "infobox", "jquery-geolocation"]
        }
    }
}); 