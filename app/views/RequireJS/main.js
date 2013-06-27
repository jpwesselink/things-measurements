require(["IOT"], function(IOT) {
    IOT.init({
    	defaults : {
    		COOKIE_PREFIX : "${play.configuration.get('application.session.cookie')}",
    		timeDelta : new Date().getTime() - ${System.currentTimeMillis()},
    		votesGap : ${play.configuration.get('votes.gap.seconds', 28800)},
    		lastMeasurement: ${models.Measurement.find("order by id desc").first().serializeWith('measurementSerializer').raw()}
    	},
    	viewModelDefaults : {
    		lastVote : ${lastVote?:0}
    	},
        actions : {
            Application : {
                vote : #{jsAction @Application.vote(':value', ':lng', ':lat') /}
            },
            Measurements : {
                percentages : #{jsAction @Measurements.percentages()/}
            },
            MeasurementsFeed: {
                FeedSocket : { 
                    join : #{jsAction @@MeasurementsFeed.FeedSocket.join()/}
                }
            }
        },
        data : {
            locations : ${models.Location.all().fetch().serializeWith('locationSerializer').raw()}
        }
    });
    window.IOT = IOT;
});