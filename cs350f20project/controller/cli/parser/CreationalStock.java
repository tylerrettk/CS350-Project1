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
		String id = this.commandSplit[1], type;
		
		if(!this.commandSplit[2].equalsIgnoreCase("as"))
		{
			throw new IllegalArgumentException("Incorrect command construction for CreationalStock Class");
		}
		
		try
		{
			type = this.commandSplit[3];
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("parser error for CreationalStock Class");
		}
		
	
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
			throw new IllegalArgumentException("Incorrect command construction for creating a Stock Car Class");
		}
		
		this.commandSchedule();
			
	}
	
	/*
	 * public TrackLocator(java.lang.String trackID,double distance,boolean isFromAElseB)
	 */
	
	private void createEngine()
	{
		//	 * CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )
		
		String keyWord = "AS DIESEL ON TRACK DISTANCE FROM FACING";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[3] + " " + this.commandSplit[4] + " " + this.commandSplit[5] + " " + this.commandSplit[7] + " " 
				+ this.commandSplit[9] + " "+ this.commandSplit[11];
		
		boolean startOrEnd = this.commandSplit[10].equalsIgnoreCase("start") || this.commandSplit[10].equalsIgnoreCase("end");
		boolean endOrStart = this.commandSplit[12].equalsIgnoreCase("end") || this.commandSplit[12].equalsIgnoreCase("start");
		
		if(keyWord.equalsIgnoreCase(command)&& (startOrEnd||endOrStart))
		{
			String id = this.commandSplit[1];
			
			boolean isFromAElseB = false, isFacingStartElseEnd = false;
			TrackLocator locator;
			
			
			try
			{
				String id2 = this.commandSplit[6];
				
				int distance = Integer.parseInt(this.commandSplit[8]);
				
				if(this.commandSplit[10].equalsIgnoreCase("start"))
					isFromAElseB = true;
				
				locator = new TrackLocator(id2, distance, isFromAElseB);
				
				if(this.commandSplit[11].equalsIgnoreCase("start"))
					isFacingStartElseEnd = true;
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format for parser: " + this.getCommand());
			}
			
			this.setCommandType(new CommandCreateStockEngineDiesel(id, locator, isFacingStartElseEnd));
	
			this.commandSchedule();
		}
		else 
			throw new IllegalArgumentException("Incorrect command construction for creating an Engine");
	}
}
