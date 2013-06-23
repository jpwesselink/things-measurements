require(['knockout', 'jquery', 'underscore'], function(ko, $, _) {
    ko.bindingHandlers.highlight = {
        update: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var $el = $(element);
            $el.html(hljs.highlightBlock(element));
        }
    };
});