package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.behavioral.*;

public class BehavioralSet extends CommandParser{

	private String[] commandSplit;
	
	public BehavioralSet(MyParserHelper parserHelper, String commandText, String temptext) {
		super(parserHelper, temptext);
		// TODO Auto-generated constructor stub
		this.commandSplit = temptext.split(" ");
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
		
		String keyWord = "DIRECTION FORWARD";
		String keyWord2 = "DIRECTION BACKWARD";
		String command = this.commandSplit[1] +" "+this.commandSplit[2];
		
		if(keyWord.equalsIgnoreCase(command) || keyWord2.equalsIgnoreCase(command))
		{
			String id = this.commandSplit[0];
			boolean isForwardElseBackward = false;
			
			if(this.commandSplit[2].equalsIgnoreCase("forward"))
				isForwardElseBackward = true;
			
			
			this.setCommandType(new CommandBehavioralSetDirection(id, isForwardElseBackward));
			this.commandSchedule();
		}
		else
			throw new IllegalArgumentException("Incorrect command construction for setting direction");
	}
	
	public void reference()
	{
		//public CommandBehavioralSetReference(java.lang.String id)
		
		if(!this.getCommand().contains("ENGINE"))
		{
			throw new IllegalArgumentException("Incorrect format for command - BehavioralSet Class");
		}
		
		String id = this.commandSplit[2];
		
		this.setCommandType(new CommandBehavioralSetReference(id));
		this.commandSchedule();
	}
	
	public void speed()
	{
		//public CommandBehavioralSetSpeed(java.lang.String id, double speed)
		String id;
		double speed;
		
		try
		{
			id = this.commandSplit[0];
			speed = Double.parseDouble(this.commandSplit[2]);
		}
		
		catch(Exception e)
		{
			throw new IllegalArgumentException("Incorrect format for command - BehavioralSet Class");
		}
		
		
		this.setCommandType(new CommandBehavioralSetSpeed(id, speed));
		this.commandSchedule();
	}

}