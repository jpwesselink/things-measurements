require(['knockout', 'jquery', 'underscore'], function(ko, $, _) {
    ko.bindingHandlers.peity = {
        update: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var $el = $(element);
            var options = valueAccessor();
            var valueMap = {};
            var values = [];
            if(options.valueMap && _.isObject(options.valueMap)){
                valueMap = options.valueMap;
                _.each(valueMap, function (value, key){
                    values.push(value);
                });
            }
            $el.html(values.join(","));
            var config = {};
            if(options.config){
               config = options.config; 
               console.log("config", config);
            }
            $el.peity("pie", config);
        }
    };
});