package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.behavioral.*;

public class BehavioralSet extends CommandParser{

	private String[] commandSplit;
	
	public BehavioralSet(MyParserHelper parserHelper, String commandText) {
		super(parserHelper, commandText);
		// TODO Auto-generated constructor stub
		this.commandSplit = commandText.split(" ");
	}

	@Override
	public void parse() {
		
		if(this.commandSplit[0].equalsIgnoreCase("reference"))
		{
			reference();
		}
		
		else if(this.commandSplit[1].equalsIgnoreCase("direction"))
		{
			direction();
		}
		
		else if(this.commandSplit[1].equalsIgnoreCase("speed"))
		{
			speed();
		}
		
		else
			throw new IllegalArgumentException("This Command is invalid");
		
	}
	
	/*
	 * DO SET id DIRECTION ( FORWARD | BACKWARD ) 
	 * DO SET REFERENCE ENGINE id
	 * DO SET id SPEED number
	 */
	
	public void direction()
	{
		//public CommandBehavioralSetDirection(java.lang.String id, boolean isForwardElseBackward)
		String id = this.commandSplit[0];
		boolean isForwardElseBackward = false;
		
		if(this.commandSplit[2].equalsIgnoreCase("forward"))
			isForwardElseBackward = true;
		
		
		this.setCommandType(new CommandBehavioralSetDirection(id, isForwardElseBackward));
		this.commandSchedule();
	}
	
	public void reference()
	{
		//public CommandBehavioralSetReference(java.lang.String id)
		String id = this.commandSplit[2];
		
		this.setCommandType(new CommandBehavioralSetReference(id));
		this.commandSchedule();
	}
	
	public void speed()
	{
		//public CommandBehavioralSetSpeed(java.lang.String id, double speed)
		String id = this.commandSplit[0];
		double speed = Double.parseDouble(this.commandSplit[2]);
		
		this.setCommandType(new CommandBehavioralSetSpeed(id, speed));
		this.commandSchedule();
	}

}
