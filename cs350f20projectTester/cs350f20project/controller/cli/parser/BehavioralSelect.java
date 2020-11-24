package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.behavioral.CommandBehavioralSelectBridge;

public class BehavioralSelect extends CommandParser{

	public BehavioralSelect(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse() {
		// split into methods
		
		String[] whatever = getCommand().split(" ");

		
		if(whatever[0].equalsIgnoreCase("drawbridge"))
		{
			drawbridge();
		}
	}
	
	public void drawbridge()
	{
		String[] whatever = getCommand().split(" ");
		
		String id = whatever[1];
		boolean position = false;
		
		if(whatever[3].equalsIgnoreCase("up"))
			position = true;
		
		this.setCommandType(new CommandBehavioralSelectBridge(id, position));
		this.commandSchedule();
	}
	
	public void roundhouse()
	{
		
	}
	
	public void switchPath()
	{
	
	}

}
