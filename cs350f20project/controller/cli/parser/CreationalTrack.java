package cs350f20project.controller.cli.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		else if(type.equalsIgnoreCase("roundhouse"))
		{
			createRoundhouse();
		}
		
		else if(type.equalsIgnoreCase("switch"))
		{
			createSwitch();
		}
		
		else if(type.equalsIgnoreCase("straight"))
		{
			createStraight();
		}
		
		else
			throw new IllegalArgumentException("Incorrect command for CreationalTrack Class");
	}
	
	/*
	 * CREATE TRACK STRAIGHT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 */
	private void createStraight() {
		
		String keyWord = "REFERENCE DELTA START END";
		
		String command = this.commandSplit[2] + " "+ this.commandSplit[4] +" "+ this.commandSplit[5] + " " + this.commandSplit[7];
		
		if(keyWord.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			
			String idCoordinate = this.commandSplit[3];
			
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
			CoordinatesDelta deltaCoor, deltaCoor2;
			
			try
			{
				String[] delta = this.commandSplit[6].split(":");
				double x = Double.parseDouble(delta[0]);
				double y = Double.parseDouble(delta[1]);
				
				deltaCoor = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[8].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				deltaCoor2 = new CoordinatesDelta(x,y);
				
			}
			
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			 
			
			PointLocator locator = new PointLocator(refCoordinate, deltaCoor, deltaCoor2);
			
			this.setCommandType(new CommandCreateTrackStraight(id, locator));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for creation of straight track");
	}

	/*
	 * CREATE TRACK SWITCH TURNOUT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) STRAIGHT DELTA START coordinates_delta1 END
	 * coordinates_delta2 CURVE DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number
	 * 
	 * CREATE TRACK SWITCH WYE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * DISTANCE ORIGIN number1 DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number2
	 */
	private void createSwitch() {
		
	
		String keyWord = "TURNOUT REFERENCE STRAIGHT DELTA START END CURVE DELTA START END DISTANCE ORIGIN";
		String keyWord2 = "WYE REFERENCE DELTA START END DISTANCE ORIGIN DELTA START END DISTANCE ORIGIN";
		
		String command = this.commandSplit[1] + " " + this.commandSplit[3] + " " + this.commandSplit[5] + " " + this.commandSplit[6] + " " + this.commandSplit[7] + " " + this.commandSplit[9] + " " +
				this.commandSplit[11] + " " + this.commandSplit[12] + " " + this.commandSplit[13] + " " + this.commandSplit[15] + " " + this.commandSplit[17] + " " + this.commandSplit[18];
		
		String command2 = this.commandSplit[1] + " " + this.commandSplit[3] + " " + this.commandSplit[5] + " " + this.commandSplit[6] + " " + this.commandSplit[8] + " " + this.commandSplit[10] + " " +
				this.commandSplit[11] + " " + this.commandSplit[13] + " " + this.commandSplit[14] + " " + this.commandSplit[16] + " " + this.commandSplit[18] + " " + this.commandSplit[19];
		
		
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command2))
		{
			String id = this.commandSplit[2];
			
			
			String idCoordinates = this.commandSplit[4];
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinates);
			
			CoordinatesDelta deltaCoor, deltaCoor2, deltaCoor3, deltaCoor4;
			
			String[] delta;
			int distance, distance2;
			double x,y;
			
			if(this.getCommand().contains("TURNOUT"))
			{
				try
				{
					delta = this.commandSplit[8].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor = new CoordinatesDelta(x,y);
					
					delta = this.commandSplit[10].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor2 = new CoordinatesDelta(x,y);
					
					delta = this.commandSplit[14].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor3 = new CoordinatesDelta(x,y);
					
					delta = this.commandSplit[16].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor4 = new CoordinatesDelta(x,y);
					
					distance = Integer.parseInt(this.commandSplit[19]);
				}
				
				catch(Exception e)
				{
					throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
				}
				
				CoordinatesDelta origin = ShapeArc.calculateDeltaOrigin(refCoordinate, deltaCoor3, deltaCoor4, distance);
				
				this.setCommandType(new CommandCreateTrackSwitchTurnout(id, refCoordinate, deltaCoor, deltaCoor2, deltaCoor3, deltaCoor4, origin));
		}

		
		//CREATE TRACK SWITCH WYE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
		// DISTANCE ORIGIN number1 DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number2
		
		else
		{
			try
			{
				delta = this.commandSplit[7].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				deltaCoor = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[9].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				deltaCoor2 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[15].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				deltaCoor3 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[17].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				deltaCoor4 = new CoordinatesDelta(x,y);
				
				distance = Integer.parseInt(this.commandSplit[12]);
				distance2 = Integer.parseInt(this.commandSplit[20]);
			}
			
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			
			CoordinatesDelta origin = ShapeArc.calculateDeltaOrigin(refCoordinate, deltaCoor, deltaCoor2, distance);
			CoordinatesDelta origin2 = ShapeArc.calculateDeltaOrigin(refCoordinate, deltaCoor3, deltaCoor4, distance2);
			
			this.setCommandType(new CommandCreateTrackSwitchWye(id, refCoordinate, deltaCoor, deltaCoor2, origin, deltaCoor3, deltaCoor4, origin2));
		}
		
		this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for creation of Switch track");
	}

	/*
	 * CREATE TRACK ROUNDHOUSE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA ORIGIN coordinates_delta1 ANGLE ENTRY angle1
	 * START angle2 END angle3 WITH integer SPURS LENGTH number1 TURNTABLE LENGTH number2
	 */
	private void createRoundhouse() {
		
		String keyWord = "REFERENCE DELTA ORIGIN ANGLE ENTRY START END WITH SPURS LENGTH TURNTABLE LENGTH";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7] + " " + this.commandSplit[8] + " " + this.commandSplit[10] + " " +
				this.commandSplit[12] + " " + this.commandSplit[14] + " " + this.commandSplit[16] + " " + this.commandSplit[17] + " " + this.commandSplit[19] + " " + this.commandSplit[20];
		
		if(keyWord.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			
			String idCoordinate = this.commandSplit[3];
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
			CoordinatesDelta deltaCoor;
			
			double x, y, spurLength, roundHouse;
			int spurs;
			Angle angle, angle2, angle3;
			
			
			try
			{
				String[] delta = this.commandSplit[6].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				deltaCoor = new CoordinatesDelta(x,y);
				
				angle = new Angle(Double.parseDouble(this.commandSplit[9]));
				angle2 = new Angle(Double.parseDouble(this.commandSplit[11]));
				angle3 = new Angle(Double.parseDouble(this.commandSplit[13]));
				
				spurs = Integer.parseInt(this.commandSplit[15]);
				spurLength = Double.parseDouble(this.commandSplit[18]);
				roundHouse = Double.parseDouble(this.commandSplit[21]);
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			this.setCommandType(new CommandCreateTrackRoundhouse(id, refCoordinate, deltaCoor, angle, angle2, angle3, spurs, spurLength, roundHouse));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for create roundhouse track");
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
		String keyWord = "DRAW REFERENCE DELTA START END ANGLE";
		String keyWord2 = "REFERENCE DELTA START END";
		
		String command;
		
		if(this.commandSplit.length < 11)
			command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7];
		
		else
			command = this.commandSplit[1] + " " + this.commandSplit[3] + " " + this.commandSplit[5] + " " + this.commandSplit[6] + " " + this.commandSplit[8] + " " + this.commandSplit[10];
		
		if(keyWord.equalsIgnoreCase(command)|| keyWord2.equalsIgnoreCase(command))
		{
			String id = "";
			boolean angleCheck = this.getCommand().contains("ANGLE");
			double x, y;
			
			CoordinatesDelta deltaCoor2, deltaCoor;
			Angle angle;
			
			if(this.getCommand().contains("DRAW"))
			{
				id = this.commandSplit[2];
	
				try
				{
					String[] delta = this.commandSplit[7].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor = new CoordinatesDelta(x,y);
					
					delta = this.commandSplit[9].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					 
					deltaCoor2 = new CoordinatesDelta(x,y);	
					
					if(!angleCheck)
						throw new IllegalArgumentException("Incorrect command for CreationalTrack Class");
					
					
					int angleInt = Integer.parseInt(this.commandSplit[11]);
					angle = new Angle(angleInt);
				}
				
				catch(Exception e)
				{
					throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
				}
				
				String idCoordinate = this.commandSplit[4];
				
				CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
	
				
				PointLocator locator = new PointLocator(refCoordinate,deltaCoor, deltaCoor2);
				
				this.setCommandType(new CommandCreateTrackBridgeDraw(id, locator, angle));
			}
			
			else
			{
				id = this.commandSplit[1];
	
				try
				{
					String[] delta = this.commandSplit[6].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					
					deltaCoor = new CoordinatesDelta(x,y);
					
					delta = this.commandSplit[8].split(":");
					x = Double.parseDouble(delta[0]);
					y = Double.parseDouble(delta[1]);
					 
					deltaCoor2 = new CoordinatesDelta(x,y);	
				}
				
				catch(Exception e)
				{
					throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
				}
				
				
				String idCoordinate = this.commandSplit[3];
				
				CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
	
				
				PointLocator locator = new PointLocator(refCoordinate,deltaCoor, deltaCoor2);
				
				this.setCommandType(new CommandCreateTrackBridgeFixed(id,locator));
			}
			
			
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for creating the bridge track");
	}
	
	/*
	 * CREATE TRACK CROSSING id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * 
	 */
	
	private void createCrossing()
	{
		String keyWord = "REFERENCE DELTA START END";
		String command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7];
		
		if(keyWord.equalsIgnoreCase(command))
		{
			CoordinatesDelta deltaCoor, deltaCoor2;
			String id = this.commandSplit[1];
			
			try
			{
				//creating the Delta Coordinates
				String[] delta = this.commandSplit[6].split(":");
				double x = Double.parseDouble(delta[0]);
				double y = Double.parseDouble(delta[1]);
				
				deltaCoor = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[8].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				 
				deltaCoor2 = new CoordinatesDelta(x,y);
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			//creating the World Coordinate reference
			String idCoordinate = this.commandSplit[3];
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
	
			
			//creating the PointLocator
			PointLocator locator = new PointLocator(refCoordinate, deltaCoor, deltaCoor2);
			
			this.setCommandType(new CommandCreateTrackCrossing(id, locator));
			this.commandSchedule();
		}
	}
	
	/*
	 * CREATE TRACK CROSSOVER id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 * START coordinates_delta3 END coordinates_delta4
	 */
	
	private void createCrossover()
	{
		
		String keyWord = "REFERENCE DELTA START END START END";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7] + " " + this.commandSplit[9] + " " + this.commandSplit[11];
		
		if(keyWord.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			
			String idCoordinate = this.commandSplit[3];
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
			CoordinatesDelta coordinates_delta1, coordinates_delta2, coordinates_delta3, coordinates_delta4;
	
			try
			{
				String[] delta = this.commandSplit[6].split(":");
				double x = Double.parseDouble(delta[0]);
				double y = Double.parseDouble(delta[1]);
				
				coordinates_delta1 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[8].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				coordinates_delta2 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[10].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				coordinates_delta3 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[12].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				coordinates_delta4 = new CoordinatesDelta(x,y);
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			this.setCommandType(new CommandCreateTrackCrossover(id, refCoordinate, coordinates_delta1,coordinates_delta2,coordinates_delta3,coordinates_delta4));
			this.commandSchedule();
		}
		
	}
	
	/*
	 * CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
		( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )
	 */
	
	private void createCurve()
	{
		String keyWord = "REFERENCE DELTA START END DISTANCE ORIGIN";
		String keyWord2 = "REFERENCE DELTA START END ORIGIN";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7] +" "+ this.commandSplit[9] + " "+ this.commandSplit[10];
		String command2 = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7] +" "+ this.commandSplit[9];
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command2))
		{
		String id = this.commandSplit[1];
		
		
		String idCoordinate = this.commandSplit[3];
		
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);

		try
		{
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
		}
		
		catch(Exception e)
		{
			throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
		}
		
		this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for creating a curved track Class");
		
	}
	
	/*
	 * CREATE TRACK END id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2
	 */
	
	private void createEnd()
	{
		String keyWord = "REFERENCE DELTA START END";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7];
		
		if(keyWord.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			
			String idCoordinate = this.commandSplit[3];
			
			CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
			CoordinatesDelta coordinates_delta1, coordinates_delta2;
			
			try
			{
				String[] delta = this.commandSplit[6].split(":");
				double x = Double.parseDouble(delta[0]);
				double y = Double.parseDouble(delta[1]);
				
				coordinates_delta1 = new CoordinatesDelta(x,y);
				
				delta = this.commandSplit[8].split(":");
				x = Double.parseDouble(delta[0]);
				y = Double.parseDouble(delta[1]);
				
				coordinates_delta2 = new CoordinatesDelta(x,y);
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			PointLocator locator = new PointLocator(refCoordinate, coordinates_delta1, coordinates_delta2);
			
			this.setCommandType(new CommandCreateTrackEnd(id, locator));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for creating a end track Class");
	}
	
	/*
	 * CREATE TRACK LAYOUT id1 WITH TRACKS idn+
	 */
	
	private void createLayout()
	{
		if(this.getCommand().contains("LAYOUT") && this.getCommand().contains("WITH TRACKS"))
		{
		
			String id = this.commandSplit[1];
			
			List<String> tracks = new ArrayList<String>();
			
			for(int x = 4; x < this.commandSplit.length; x++)
			{
				tracks.add(this.commandSplit[x]);
			}
			
			this.setCommandType(new CommandCreateTrackLayout(id, tracks));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command for CreationalTrack Class");
	}
	
	private CoordinatesWorld createCoordinate(String idCoordinate)
	{
		
		Pattern pattern = Pattern.compile("\\d+" + "\\*" + "\\d+" + "'" + "\\d+" + "\"", Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(idCoordinate);
		
		boolean correct = matcher.find();
		
		if(!correct && !this.getCommand().contains("$"))
		{
			throw new IllegalArgumentException("Incorrect format for coordinate");
		}
		
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
			seconds = Integer.parseInt(longitude.substring(longitude.indexOf("'") +1,longitude.indexOf('"')));
			
			Longitude longi = new Longitude(hours, mintues, seconds);
			
			refCoordinate = new CoordinatesWorld(lat1, longi);
		}
		
		return refCoordinate;
	}

}
