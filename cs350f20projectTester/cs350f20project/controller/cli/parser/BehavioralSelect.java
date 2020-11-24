package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.behavioral.CommandBehavioralSelectBridge;
import cs350f20project.controller.command.behavioral.CommandBehavioralSelectRoundhouse;
import cs350f20project.controller.command.behavioral.CommandBehavioralSelectSwitch;
import cs350f20project.datatype.Angle;

public class BehavioralSelect extends CommandParser{
	
	private String[] commandSplit;

	public BehavioralSelect(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		// TODO Auto-generated constructor stub
		commandSplit = commandText.split(" ");
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
		
		else
		{
			switchPath();
		}
	}
	
	/*
	 * DO SELECT SWITCH id PATH ( PRIMARY | SECONDARY ) 
	 * DO SELECT ROUNDHOUSE id POSITION angle ( CLOCKWISE | COUNTERCLOCKWISE ) 
	 * DO SELECT DRAWBRIDGE id POSITION ( UP | DOWN )
	 */
	
	public void drawbridge()
	{
		String id = this.commandSplit[1];
		boolean position = false;
		
		if(this.commandSplit[3].equalsIgnoreCase("up"))
			position = true;
		
		this.setCommandType(new CommandBehavioralSelectBridge(id, position));
		this.commandSchedule();
	}
	
	public void roundhouse()
	{
		//public CommandBehavioralSelectRoundhouse(java.lang.String id, Angle angle, boolean isClockwise)
		String id = this.commandSplit[1];
		Angle angle = new Angle(Double.parseDouble(this.commandSplit[3]));
		boolean changePosition = false;
		
		if(this.commandSplit[4].equalsIgnoreCase("clockwise"))
		{
			changePosition = true;
		}
		
		this.setCommandType(new CommandBehavioralSelectRoundhouse(id, angle, changePosition));
		this.commandSchedule();
	
	}
	
	public void switchPath()
	{
		//public CommandBehavioralSelectSwitch(java.lang.String id, boolean isPrimaryElseSecondary)
		
		String id = this.commandSplit[1];
		boolean primaryOrSecondary = false;
		
		if(this.commandSplit[3].equalsIgnoreCase("primary"))
			primaryOrSecondary = true;
		
		this.setCommandType(new CommandBehavioralSelectSwitch(id, primaryOrSecondary));
	}

}
