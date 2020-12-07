package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.creational.*;

public class CreationalStock extends CommandParser{

	private String[] commandSplit;
	
	public CreationalStock(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		// TODO Auto-generated constructor stub
		this.commandSplit = commandText.split(" ");

	}
	/*
	 * CREATE STOCK CAR id AS BOX
	 * CREATE STOCK CAR id AS CABOOSE 
	 * CREATE STOCK CAR id AS FLATBED 
	 * CREATE STOCK CAR id AS PASSENGER 
	 * CREATE STOCK CAR id AS TANK
	 * CREATE STOCK CAR id AS TENDER
	 * CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )
	 */
	public void parse()
	{
		if(this.commandSplit[0].equalsIgnoreCase("car"))
		{
			createCar();
		}
		
		else if(this.commandSplit[0].equalsIgnoreCase("engine"))
		{
			createEngine();
		}
		
		else
		{
			throw new IllegalArgumentException("Incorrect command for CreationalStock Class");
		}
	}
	
	private void createCar()
	{		
		String id = this.commandSplit[1];
		
		if(!this.commandSplit[2].equalsIgnoreCase("as"))
		{
			throw new IllegalArgumentException("Incorrect command construction for CreationalStock Class");
		}
		
		String type = this.commandSplit[3];
		
		if(type.equalsIgnoreCase("box"))
		{

			this.setCommandType(new CommandCreateStockCarBox(id));
		}
		
		else if(type.equalsIgnoreCase("caboose"))
		{

			this.setCommandType(new CommandCreateStockCarCaboose(id));
		}
		
		else if(type.equalsIgnoreCase("flatbed"))
		{

			this.setCommandType(new CommandCreateStockCarFlatbed(id));
		}
		
		else if(type.equalsIgnoreCase("passenger"))
		{

			this.setCommandType(new CommandCreateStockCarPassenger(id));
		}
		
		else if(type.equalsIgnoreCase("tank"))
		{

			this.setCommandType(new CommandCreateStockCarTank(id));
		}
		
		else if(type.equalsIgnoreCase("tender"))
		{

			this.setCommandType(new CommandCreateStockCarTender(id));
		}
		
		else
		{
			throw new IllegalArgumentException("Incorrect command construction for CreationalStock Class");
		}
		
		this.commandSchedule();
			
	}
	
	/*
	 * public TrackLocator(java.lang.String trackID,double distance,boolean isFromAElseB)
	 */
	
	private void createEngine()
	{
		//	 * CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )
		String id = this.commandSplit[1];
		
		boolean isFromAElseB = false, isFacingStartElseEnd = false;
		
		if(!this.getCommand().contains("AS DIESEL ON TRACK") && !this.getCommand().contains("DISTANCE") && !this.getCommand().contains("FACING") && (!this.getCommand().contains("START") || !this.getCommand().contains("END")))
		{
			throw new IllegalArgumentException("Incorrect command construction for CreationalStock Class");
		}
		
		String id2 = this.commandSplit[6];
		
		int distance = Integer.parseInt(this.commandSplit[8]);
		
		if(this.commandSplit[10].equalsIgnoreCase("start"))
			isFromAElseB = true;
		
		TrackLocator locator = new TrackLocator(id2, distance, isFromAElseB);
		
		if(this.commandSplit[11].equalsIgnoreCase("start"))
			isFacingStartElseEnd = true;
		
		this.setCommandType(new CommandCreateStockEngineDiesel(id, locator, isFacingStartElseEnd));

		this.commandSchedule();
	}
}