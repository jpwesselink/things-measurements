require(["IOT"], function(IOT) {
    IOT.init({
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