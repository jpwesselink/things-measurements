Internet of things measurements 
========

## Get started ##

### Prerequisites ###
1.	[Play-1.2.5](http://download.playframework.org/releases/play-1.2.5.zip)
2. 	[MySQL-5.5.25](http://dev.mysql.com/downloads/mysql/)
3. 	[Maven-3.0.4](http://maven.apache.org/download.html)

### Get it to work ###

1. 	Clone this repo.
2.	Add a mysql user.

		# local
		CREATE USER 'four'@'localhost' IDENTIFIED BY 'NZg9TSMM';
		CREATE DATABASE four;
		GRANT ALL ON four.* TO 'four'@'localhost';
		
	
3.	Get the Play! wagon on the road.

		$ play secret
		$ play deps
		$ play mvn:init
		$ mv pom.xml.bak pom.xml
		$ play mvn:update
		$ play mvn:sources
		$ play run
	
4.	Go to [http://localhost:9000/admin](http://localhost:9000/admin).

5.	Endpoints.
		GET		/								Seek life elsewhere
		GET		/locations.xls							Excel export of results
		GET		/locations.json							Lists all locations
		GET		/locations/{slug}.json					Shows a single location

		GET		/locations/{slug}/measurements.json		Shows all measurements for a location
		POST	/locations/{slug}/measurements.json		Creates and returns a new measurement for a 
														location

		GET		/admin									Admin area
		
6. Eclipse and Play.
	
	From the play directory copy /support/eclipse/org.playframework.playclipse_0.7.0.jar to your Eclipse's /plugin directory. The plugin will give you some content assist, highlighting, the ability to quickly create models, controllers and views and easier debugging.

7. Eclipse and this project.
	
	In order to import this project as 'existing project' in Eclipse just run the following command at the project's path:
	
		$ play eclipsify
		
