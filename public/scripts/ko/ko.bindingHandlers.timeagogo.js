require(['knockout', 'jquery', 'jquery.timeago'], function(ko, $, _) {
	ko.bindingHandlers.timeagogo = {
		delta: 0,
	    update: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
	    	var allBindings = allBindingsAccessor();
	    	var self = ko.bindingHandlers.timeagogo;
	        var value = ko.utils.unwrapObservable(valueAccessor());
	        var $this = $(element);
	
	        // Set the title attribute to the new value = timestamp
	        $this.attr('title', value);
	        var tick = self.tick;
	        var now = new Date();
	        if(allBindings.tick){
	        	now = ko.utils.unwrapObservable(allBindings.tick);
	        }
	        
	        if ($this.data('timeago')) {
	            var datetime = $.timeago.datetime($this);
	            var distance = (now.getTime() - datetime.getTime() + self.delta);
	            var inWords = $.timeago.inWords(distance);
	
	            // Update cache and displayed text..
	            $this.data('timeago', { 'datetime': datetime });
	            $this.text(inWords);
	        } else {
	            // timeago hasn't been applied to this node -> we do that now!
	            $this.timeago();
	        }
	    }
	};
	
	ko.virtualElements.allowedBindings.timeagogo = true;
});