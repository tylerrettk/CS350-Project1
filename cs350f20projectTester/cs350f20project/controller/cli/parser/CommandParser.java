package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoExit;
import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.Latitude;
import cs350f20project.datatype.Longitude;

public class CommandParser {
	
	protected MyParserHelper parserHelper;
	protected A_Command command;
	private String commandText;

	public CommandParser(MyParserHelper parserHelper, String commandText)
	{
		this.parserHelper = parserHelper;
		this.commandText = commandText;
	}
	
	public void parse()
	{
	
	String[] tempArr = commandText.split(";");
	
	for(int x = 0; x < tempArr.length; x++) // handles multiple commands separated by ";" for rule #1
	{
		String CurrentCommand = tempArr[x];
		
		if (CurrentCommand.equalsIgnoreCase("@exit"))
		{
			A_Command command = new CommandMetaDoExit();
			this.parserHelper.getActionProcessor().schedule(command);
		} 
		
		else if(CurrentCommand.substring(0, 12).equalsIgnoreCase("CREATE POWER"))
		{
			CreationalPower power = new CreationalPower(parserHelper, commandText.substring(13));
			power.parse();
		}
		
		else if(CurrentCommand.substring(0, 12).equalsIgnoreCase("CREATE STOCK"))
		{
			int k = this.commandText.indexOf("K")+2;
			CreationalStock stock = new CreationalStock(parserHelper, commandText.substring(k));
			stock.parse();
		}
		else if(CurrentCommand.substring(0, 12).equalsIgnoreCase("CREATE TRACK"))
		{
			CreationalTrack track = new CreationalTrack(parserHelper, commandText.substring(13));
			track.parse();
		}
		else if(CurrentCommand.substring(0, 9).equalsIgnoreCase("DO SELECT"))
		{
			BehavioralSelect behsel = new BehavioralSelect(parserHelper, commandText.substring(10));
			behsel.parse();
		}
		else if(CurrentCommand.substring(0, 6).equalsIgnoreCase("DO SET"))
		{
			BehavioralSet behset = new BehavioralSet(parserHelper, commandText.substring(7));
			behset.parse();
		}
		else
		{
			MetaMiscCommands misc = new MetaMiscCommands(parserHelper, commandText);
			misc.parse(); 
		}
	}
	}
	
	public void commandSchedule()
	{
		this.parserHelper.getActionProcessor().schedule(this.command);
	}
	
	public String getCommand()
	{
		return this.commandText;
	}
	
	public void setCommandType(A_Command command)
	{
		this.command = command;
	}
	
	public MyParserHelper getHelper()
	{
		return this.parserHelper;
	}
	
	public CoordinatesWorld CalculateWorldCoordinates(String latitude, String longitude) 
	{
		// Latitude
        int laDegrees = Integer.parseInt(latitude.split("\\*")[0]);
        int laMinutes = Integer.parseInt(latitude.split("\'")[0].split("\\*")[1]);
        double laSecond =  Double.parseDouble(latitude.split("\"")[0].split("\'")[1]);
        Latitude finallatitude = new Latitude(laDegrees, laMinutes, laSecond);

        // Longitude
        int loDegrees = Integer.parseInt(longitude.split("\\*")[0]);
        int loMinutes = Integer.parseInt(longitude.split("\'")[0].split("\\*")[1]);
        double loSeconds =  Double.parseDouble(longitude.split("\"")[0].split("\'")[1]);
        Longitude finallongitude = new Longitude(loDegrees, loMinutes, loSeconds);

        return new CoordinatesWorld(finallatitude, finallongitude);
    }

}

