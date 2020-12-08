package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.structural.CommandStructuralCommit;
import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoRun;
import cs350f20project.controller.command.meta.CommandMetaViewDestroy;
import cs350f20project.controller.command.meta.CommandMetaViewGenerate;
import cs350f20project.controller.command.structural.CommandStructuralCouple;
import cs350f20project.controller.command.structural.CommandStructuralLocate;
import cs350f20project.controller.command.structural.CommandStructuralUncouple;
import cs350f20project.datatype.CoordinatesScreen;
import cs350f20project.datatype.CoordinatesWorld;

public class MetaMiscCommands extends CommandParser {

	private String[] commandSplit;
	private A_Command command;
	private final String commandUse = "USE AS REFERENCE", commandOpen = "OPEN VIEW ORIGIN WORLD WIDTH SCREEN WIDTH HEIGHT", commandClose = "CLOSE VIEW", commandCouple = "COUPLE STOCK AND", commandUnCouple = "UNCOUPLE STOCK AND", commandLocate = "LOCATE STOCK ON TRACK DISTANCE FROM";          

	public MetaMiscCommands(MyParserHelper parserHelper, String commandText, String temptext) {
		super(parserHelper, commandText);
		this.commandSplit = temptext.split(" "); 
	}
	
	public void parse()
	{
		if(this.commandSplit[0].equalsIgnoreCase("@RUN"))
			RunString();
		else if(this.commandSplit[1].equalsIgnoreCase("VIEW"))
			CloseOrOpenview();
		else if(this.commandSplit[0].equalsIgnoreCase("COUPLE") || this.commandSplit[0].equalsIgnoreCase("UNCOUPLE"))
			Coupler();
		else if(this.commandSplit[0].equalsIgnoreCase("LOCATE"))
			Locater();
		else if(this.commandSplit[0].equalsIgnoreCase("USE"))
			UseAsRef();
	}
	
	// @RUN string
	private void RunString()
	{
			String temp = commandSplit[1].replace("'", "");
			A_Command command = new CommandMetaDoRun(temp);
			parserHelper.getActionProcessor().schedule(command);
	}
	
	// CLOSE VIEW id 
	// OPEN VIEW id1 ORIGIN ( coordinates_world | ( '$' id2 ) ) WORLD WIDTH integer1 SCREEN WIDTH integer2 HEIGHT integer3
	private void CloseOrOpenview()
	{
		if(commandClose.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[1]))
		{
			String viewId = commandSplit[2];
			this.command = new CommandMetaViewDestroy(viewId);
			this.parserHelper.getActionProcessor().schedule(this.command);
			return;
		}
		
		if(commandOpen.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[1] + " " + commandSplit[3] + " " + commandSplit[5] + " " + commandSplit[6] + " " + commandSplit[8] + " " + commandSplit[9] + " " + commandSplit[11])) 
		{
			 String id = commandSplit[2];
			 CoordinatesWorld origin;
			 if(commandSplit[4].charAt(0) == '$') {
				 origin = parserHelper.getReference(commandSplit[4].substring(1));
			 }
			 else {
				 String[] latAndLon = commandSplit[4].split("/");
				 origin = CalculateWorldCoordinates(latAndLon[0], latAndLon[1]);
			 }
			 
		     int screenX, screenY, worldWidth;
		     try 
		     {
		    	 worldWidth = Integer.parseInt(commandSplit[7]);
		    	 screenX = Integer.parseInt(commandSplit[10]);
		    	 screenY = Integer.parseInt(commandSplit[12]);
		     } 
		     catch (Exception e) 
		     {
		    	 throw new RuntimeException("Command Number Format Not Valid!");
		     }
			 CoordinatesScreen screenSize = new CoordinatesScreen(screenX, screenY);
			 this.command = new CommandMetaViewGenerate(id, origin, worldWidth, screenSize);
			 this.parserHelper.getActionProcessor().schedule(this.command);
		}
	}
	
	// COUPLE STOCK id1 AND id2 
	// UNCOUPLE STOCK id1 AND id2 
	private void Coupler()
	{
		 if(commandCouple.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[1] + " " + commandSplit[3])) {
			 String idStock1 = commandSplit[2], idStock2 = commandSplit[4];
			 this.command = new CommandStructuralCouple(idStock1, idStock2);
			 this.parserHelper.getActionProcessor().schedule(this.command);
		 }
		 if(commandUnCouple.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[1] + " " + commandSplit[3])) {
			 String idStock1 = commandSplit[2], idStock2 = commandSplit[4];
			 this.command = new CommandStructuralUncouple(idStock1, idStock2);
			 this.parserHelper.getActionProcessor().schedule(this.command);
		 }
		 
	}
	
	// LOCATE STOCK id1 ON TRACK id2 DISTANCE number FROM ( START | END )
	private void Locater()
	{
		 if(commandLocate.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[1] + " " + commandSplit[3] + " " + commandSplit[4] + " " + commandSplit[6] + " " + commandSplit[8])) 
		 {
			  String stockId = commandSplit[2], trackId = commandSplit[5], startOrEnd = commandSplit[9];
			  double distance = 0;
			  try
			  {
				  distance = Double.parseDouble(commandSplit[7]);
			  } 
			  catch (Exception e) 
			  {
				  throw new RuntimeException("Number not valid!");
			  }
			  boolean isStartOrEnd;
			  if(startOrEnd.equalsIgnoreCase("START")) {
				  isStartOrEnd = true;
			  }
			  else if(startOrEnd.equalsIgnoreCase("END")){
				  isStartOrEnd = false;
			  }   
			  else
				  throw new RuntimeException("START or END not Valid!");
			  
			  TrackLocator locator = new TrackLocator(trackId, distance, isStartOrEnd);
			  this.command = new CommandStructuralLocate(stockId, locator);
			  this.parserHelper.getActionProcessor().schedule(this.command);
		 }
		 else 
			 throw new RuntimeException("Command Not Valid!");
		 	
	}
	
	// USE id AS REFERENCE coordinates_world 
	private void UseAsRef()
	{
		if(commandUse.equalsIgnoreCase(commandSplit[0] + " " + commandSplit[2] + " " + commandSplit[3])) 
		{
		String [] latLon = commandSplit[4].split("/");
        CoordinatesWorld cw = CalculateWorldCoordinates(latLon[0], latLon[1]);
        parserHelper.addReference(commandSplit[1], cw);
		}
		else
			 throw new RuntimeException("Command Not Valid!");
	}
	
	
}
