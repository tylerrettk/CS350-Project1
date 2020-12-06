package cs350f20project.controller.cli.parser;

import java.util.ArrayList;
import java.util.List;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.creational.CommandCreatePowerCatenary;
import cs350f20project.controller.command.creational.CommandCreatePowerPole;
import cs350f20project.controller.command.creational.CommandCreatePowerStation;
import cs350f20project.controller.command.creational.CommandCreatePowerSubstation;
import cs350f20project.datatype.CoordinatesDelta;
import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.Latitude;
import cs350f20project.datatype.Longitude;

public class CreationalPower extends CommandParser{

	private String[] commandSplit;
	
	public CreationalPower(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		// TODO Auto-generated constructor stub
		this.commandSplit = commandText.split(" ");
	}
	
	public void parse()
	{
		if(this.commandSplit[0].equalsIgnoreCase("catenary"))
			catenary();
		
		else if(this.commandSplit[0].equalsIgnoreCase("pole"))
			pole();
		
		else if(this.commandSplit[0].equalsIgnoreCase("station"))
 			station();
		
		else
			substation();
	}
	
	/*
	 * CREATE POWER CATENARY id1 WITH POLES idn+
	 * CREATE POWER POLE id1 ON TRACK id2 DISTANCE number FROM ( START | END )
	 * CREATE POWER STATION id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH ( SUBSTATION | SUBSTATIONS ) idn+
	 * CREATE POWER SUBSTATION id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH CATENARIES idn+
	 */
	
	private void catenary()
	{
		//public CommandCreatePowerCatenary(java.lang.String id, java.util.List<java.lang.String> idPoles)
		String id = this.commandSplit[1];
		List<String> poles = new ArrayList<String>();
		
		for(int x = 4; x < commandSplit.length; x++)
		{
			poles.add(commandSplit[x]);
		}
		
		this.setCommandType(new CommandCreatePowerCatenary(id, poles));
		this.commandSchedule();
	}
	
	private void pole()
	{
		//public CommandCreatePowerPole(java.lang.String id, TrackLocator locator)
		String id = this.commandSplit[1];
		String id2 = this.commandSplit[4];
		Double distance = Double.parseDouble(this.commandSplit[6]);
		boolean isAElseB = false;
		
		if(this.commandSplit[8].equalsIgnoreCase("Start"))
			isAElseB = true;
		
		TrackLocator locator = new TrackLocator(id2, distance, isAElseB);
		
		this.setCommandType(new CommandCreatePowerPole(id, locator));
		this.commandSchedule();
		
	}
	//	 * CREATE POWER STATION id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH ( SUBSTATION | SUBSTATIONS ) idn+

	private void station()
	{
		//public CommandCreatePowerStation(java.lang.String id,CoordinatesWorld reference,
		//CoordinatesDelta delta, java.util.List<java.lang.String> idSubstations)
		List<String> idSubstations = new ArrayList<String>();
		String id = this.commandSplit[1];
		String idCoordinate = this.commandSplit[3];
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
		
		String[] delta = this.commandSplit[5].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta deltaCoor = new CoordinatesDelta(x,y);
		
		for(int z = 8; z < this.commandSplit.length; z++)
		{
			idSubstations.add(this.commandSplit[z]);
		}
		
		
		this.setCommandType(new CommandCreatePowerStation(id, refCoordinate, deltaCoor, idSubstations));
		this.commandSchedule();
	}
	
	private void substation()
	{
		//public CommandCreatePowerSubstation(java.lang.String id, CoordinatesWorld reference,
        //CoordinatesDelta delta,java.util.List<java.lang.String> idCatenaries4)
		List<String> idSubstations = new ArrayList<String>();
		String id = this.commandSplit[1];
		String idCoordinate = this.commandSplit[3];
		CoordinatesWorld refCoordinate = createCoordinate(idCoordinate);
		
		String[] delta = this.commandSplit[5].split(":");
		double x = Double.parseDouble(delta[0]);
		double y = Double.parseDouble(delta[1]);
		
		CoordinatesDelta deltaCoor = new CoordinatesDelta(x,y);
		
		for(int z = 8; x < this.commandSplit.length; z++)
		{
			idSubstations.add(this.commandSplit[z]);
		}
		
		
		
		this.setCommandType(new CommandCreatePowerSubstation(id, refCoordinate, deltaCoor, idSubstations));
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