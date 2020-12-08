package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.behavioral.CommandBehavioralSelectBridge;
import cs350f20project.controller.command.behavioral.CommandBehavioralSelectRoundhouse;
import cs350f20project.controller.command.behavioral.CommandBehavioralSelectSwitch;
import cs350f20project.datatype.Angle;

public class BehavioralSelect extends CommandParser{
	
	private String[] commandSplit;

	public BehavioralSelect(MyParserHelper parserHelper, String commandText, String temptext) {
		super(parserHelper, temptext);
		// TODO Auto-generated constructor stub
		commandSplit = temptext.split(" ");
	}

	@Override
	public void parse() {
		// split into methods

		
		if(this.commandSplit[0].equalsIgnoreCase("drawbridge"))
		{
			drawbridge();
		}
		
		else if(this.commandSplit[0].equalsIgnoreCase("roundhouse"))
		{
			roundhouse();
		}
		
		else if(this.commandSplit[0].equalsIgnoreCase("switch"))
		{
			switchPath();
		}
		
		else
			throw new IllegalArgumentException("Incorrect format for BehavioralSelect class");
	}
	
	/*
	 * DO SELECT SWITCH id PATH ( PRIMARY | SECONDARY ) 
	 * DO SELECT ROUNDHOUSE id POSITION angle ( CLOCKWISE | COUNTERCLOCKWISE ) 
	 * DO SELECT DRAWBRIDGE id POSITION ( UP | DOWN )
	 */
	
	public void drawbridge()
	{
		String keyWord = "POSITION UP";
		String keyWord2 = "POSITION DOWN";
		
		String command = this.commandSplit[2] + " "+ this.commandSplit[3];
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			boolean position = false;
			
			if(this.commandSplit[3].equalsIgnoreCase("up"))
				position = true;
			
			this.setCommandType(new CommandBehavioralSelectBridge(id, position));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for selecting the position of the drawbridge");
	}
	
	public void roundhouse()
	{
		//public CommandBehavioralSelectRoundhouse(java.lang.String id, Angle angle, boolean isClockwise)
		
		String keyWord = "POSITION CLOCKWISE";
		String keyWord2 = "POSITION COUNTERCLOCKWISE";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[4];
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
		
			Angle angle;
			
			try
			{
				angle = new Angle(Double.parseDouble(this.commandSplit[3]));
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("incorrect command format: " + this.getCommand());
			}
			
			
			boolean changePosition = false;
			if(this.commandSplit[4].equalsIgnoreCase("clockwise"))
			{
				changePosition = true;
			}
			
			this.setCommandType(new CommandBehavioralSelectRoundhouse(id, angle, changePosition));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for setting the position of the roundhouse");
	
	}
	
	public void switchPath()
	{
		//public CommandBehavioralSelectSwitch(java.lang.String id, boolean isPrimaryElseSecondary)
		
		String keyWord = "PATH PRIMARY";
		String keyWord2 = "PATH SECONDARY";
		
		String command = this.commandSplit[2] + " " + this.commandSplit[3];
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[1];
			boolean primaryOrSecondary = false;
			
			if(this.commandSplit[3].equalsIgnoreCase("primary"))
				primaryOrSecondary = true;
			
			this.setCommandType(new CommandBehavioralSelectSwitch(id, primaryOrSecondary));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for setting path position Class");
	}

}