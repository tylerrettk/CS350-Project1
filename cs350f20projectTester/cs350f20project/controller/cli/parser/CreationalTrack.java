package cs350f20project.controller.cli.parser;

import java.util.ArrayList;
import java.util.List;

import cs350f20project.controller.command.PointLocator;
import cs350f20project.controller.command.creational.*;
import cs350f20project.datatype.*;

public class CreationalTrack extends CommandParser{

	private String[] commandSplit;
	
	public CreationalTrack(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		
		this.commandSplit = commandText.split(" ");
	}
	
	public void parse()
	{
		String type = this.commandSplit[0];
		if(type.equalsIgnoreCase("bridge"))
		{
			createBridge();
		}
		
		else if(type.equalsIgnoreCase("crossing"))
		{
			createCrossing();
		}
		
		else if(type.equalsIgnoreCase("crossover")) 
		{
			createCrossover();
		}
		
		else if(type.equalsIgnoreCase("curve"))
		{
			createCurve();
		}
		
		else if(type.equalsIgnoreCase("end"))
		{
			createEnd();
		}
		
		else if(type.equalsIgnoreCase("layout"))
		{
			createLayout();
		}
		
		else
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
	}
	
	/*
	 * CREATE TRACK BRIDGE DRAW id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * ANGLE angle
	 * CREATE TRACK BRIDGE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 */
	private void createBridge()
	{
		//CommandCreateTrackBridgeFixed(java.lang.String id, PointLocator locator)
		//CommandCreateTrackBridgeDraw(java.lang.String id, PointLocator locator, Angle angle)
		//PointLocator(CoordinatesWorld reference, CoordinatesDelta deltaStart, CoordinatesDelta deltaEnd)
		if(!this.getCommand().contains("REFERENCE") && !this.getCommand().contains("DELTA START") && !this.getCommand().contains("END"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = "";
		boolean draw = false, angleCheck = this.getCommand().contains("ANGLE");
		
		if(this.commandSplit[1].equalsIgnoreCase("draw"))
		{
			id = this.commandSplit[2];
			draw = true;
		}
		
		else
			id = this.commandSplit[1];
		
		
		
		String[] delta = this.commandSplit[7].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta deltaCoor = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[9].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		 
		CoordinatesDelta deltaCoor2 = new CoordinatesDelta(x,y);		
		
		
		String idCoordinate = this.commandSplit[4];
		
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);

		
		PointLocator locator = new PointLocator(refCoordinate,deltaCoor, deltaCoor2);
		
		if(draw)
		{
			if(!angleCheck)
				throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
			
			
			int angleInt = Integer.parseInt(this.commandSplit[11]);
			Angle angle = new Angle(angleInt);
			
			this.setCommandType(new CommandCreateTrackBridgeDraw(id, locator, angle));
		}
		
		else
			this.setCommandType(new CommandCreateTrackBridgeFixed(id,locator));
		
		this.commandSchedule();
	}
	
	/*
	 * CREATE TRACK CROSSING id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * 
	 */
	
	private void createCrossing()
	{
		if(!this.getCommand().contains("REFERENCE") && !this.getCommand().contains("CROSSING") && !this.getCommand().contains("END") && this.getCommand().contains("DELTA START"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = this.commandSplit[1];
		
		//creating the Delta Coordinates
		String[] delta = this.commandSplit[6].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta deltaCoor = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[8].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		 
		CoordinatesDelta deltaCoor2 = new CoordinatesDelta(x,y);
		
		
		//creating the World Coordinate reference
		String idCoordinate = this.commandSplit[3];
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);

		
		//creating the PointLocator
		PointLocator locator = new PointLocator(refCoordinate, deltaCoor, deltaCoor2);
		
		this.setCommandType(new CommandCreateTrackCrossing(id, locator));
		this.commandSchedule();
	}
	
	/*
	 * CREATE TRACK CROSSOVER id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * START coordinates_delta3 END coordinates_delta4
	 */
	
	private void createCrossover()
	{
		if(!this.getCommand().contains("REFERENCE") && !this.getCommand().contains("CROSSOVER") && 
				!this.getCommand().contains("END") && this.getCommand().contains("DELTA START") && !this.getCommand().contains("START"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = this.commandSplit[1];
		
		String idCoordinate = this.commandSplit[3];
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);

		
		String[] delta = this.commandSplit[6].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta1 = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[8].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta2 = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[10].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta3 = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[12].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta4 = new CoordinatesDelta(x,y);
		
		this.setCommandType(new CommandCreateTrackCrossover(id, refCoordinate, coordinates_delta1,coordinates_delta2,coordinates_delta3,coordinates_delta4));
		this.commandSchedule();
		
	}
	
	/*
	 * CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
		( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )
	 */
	
	private void createCurve()
	{
		if(!this.getCommand().contains("REFERENCE") && !this.getCommand().contains("ORIGIN") && !this.getCommand().contains("END") && this.getCommand().contains("DELTA START"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = this.commandSplit[1];
		
		
		String idCoordinate = this.commandSplit[3];
		
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);

		
		String[] delta = this.commandSplit[6].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta1 = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[8].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta2 = new CoordinatesDelta(x,y);
		
		if(this.getCommand().contains("DISTANCE ORIGIN"))
		{
			int distance = Integer.parseInt(this.commandSplit[11]);
			
			this.setCommandType(new CommandCreateTrackCurve(id, refCoordinate, coordinates_delta1, coordinates_delta2, distance));
		}
		
		else
		{
			delta = this.commandSplit[10].split(":");
			x = Double.parseDouble(delta[0]);
			y = Double.parseDouble(delta[1]);
			
			CoordinatesDelta coordinates_delta3 = new CoordinatesDelta(x,y);

			this.setCommandType(new CommandCreateTrackCurve(id, refCoordinate, coordinates_delta1, coordinates_delta2, coordinates_delta3));
		}
		
		this.commandSchedule();
		
	}
	
	/*
	 * CREATE TRACK END id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 */
	
	private void createEnd()
	{
		if(!this.getCommand().contains("REFERENCE") && !this.getCommand().contains("END") && this.getCommand().contains("DELTA START"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = this.commandSplit[1];
		
		String idCoordinate = this.commandSplit[3];
		
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
		
		String[] delta = this.commandSplit[6].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta1 = new CoordinatesDelta(x,y);
		
		delta = this.commandSplit[8].split(":");
		x = Double.parseDouble(delta[0]);
		y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta coordinates_delta2 = new CoordinatesDelta(x,y);
		
		PointLocator locator = new PointLocator(refCoordinate, coordinates_delta1, coordinates_delta2);
		
		this.setCommandType(new CommandCreateTrackEnd(id, locator));
	}
	
	/*
	 * CREATE TRACK LAYOUT id1 WITH TRACKS idn+
	 */
	
	private void createLayout()
	{
		if(!this.getCommand().contains("LAYOUT") && !this.getCommand().contains("WITH TRACKS"))
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
		
		String id = this.commandSplit[1];
		
		List<String> tracks = new ArrayList<String>();
		
		for(int x = 4; x < this.commandSplit.length; x++)
		{
			tracks.add(this.commandSplit[x]);
		}
		
		this.setCommandType(new CommandCreateTrackLayout(id, tracks));
		this.commandSchedule();
	}
	
	private CoordinatesWorld createCoordinate(String idCoordinate)
	{
		CoordinatesWorld refCoordinate;

		if(idCoordinate.contains("$"))
		{
			idCoordinate = idCoordinate.substring(idCoordinate.indexOf("$") +1);
			refCoordinate = this.getHelper().getReference(idCoordinate);
		}
		
		else
		{
			//123*45'29"/123*45'29"
			
			String[] coordinates = idCoordinate.split("/");
			String lat = coordinates[0];
			
			int hours = Integer.parseInt(lat.substring(0,lat.indexOf("*")));
			int mintues = Integer.parseInt(lat.substring(lat.indexOf("*") +1,lat.indexOf("'")));
			int seconds = Integer.parseInt(lat.substring(lat.indexOf("'") +1, lat.indexOf('"')));
			
			Latitude lat1 = new Latitude(hours,mintues,seconds);
			
			String longitude = coordinates[1];
			hours = Integer.parseInt(longitude.substring(0,longitude.indexOf("*")));
			mintues = Integer.parseInt(longitude.substring(longitude.indexOf("*") +1,longitude.indexOf("'")));
			seconds = Integer.parseInt(longitude.substring(longitude.indexOf("*") +1,longitude.indexOf("'")));
			
			Longitude longi = new Longitude(hours, mintues, seconds);
			
			refCoordinate = new CoordinatesWorld(lat1, longi);
		}
		
		return refCoordinate;
	}

}
