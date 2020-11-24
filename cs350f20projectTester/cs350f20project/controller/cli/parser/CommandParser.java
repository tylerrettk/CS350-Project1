package cs350f20project.controller.cli.parser;

import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoExit;

public class CommandParser {
	
	private MyParserHelper parserHelper;
	private String commandText;

	public CommandParser(MyParserHelper parserHelper, String commandText)
	{
		this.parserHelper = parserHelper;
		this.commandText = commandText;
	}
	
	public void parse()
	{
		String nospacescommandtext = this.commandText.replaceAll(" ","");
		String tempchecker = "";
		
		if (this.commandText.equalsIgnoreCase("@exit")) // 51
		{
			A_Command command = new CommandMetaDoExit();
			this.parserHelper.getActionProcessor().schedule(command);
		} 
		
		if (this.commandText.equalsIgnoreCase("( ( Rule#66 | Rule#67 ) ( ';' )? )*\r\n")) // 1 || Accepts rules 2 - 67 
		{

		} 
		
		if (nospacescommandtext.substring(0, 7).equalsIgnoreCase("DOBRAKE")) // 2 
		{
			System.out.println("Command 2");
		} 
		
		if (nospacescommandtext.substring(0, 8).equalsIgnoreCase("DOSELECT")) // 6 - 8
		{
			System.out.println("Command 6 - 8");
		}
 
		if (nospacescommandtext.substring(0, 5).equalsIgnoreCase("DOSET")) // 11 - 15
		{
			System.out.println("Command 11 - 15");
		} 
		
		if (nospacescommandtext.substring(0, 11).equalsIgnoreCase("CREATEPOWER")) // 22 - 25
		{
			System.out.println("Command 22 - 25");
		} 
		
		if (nospacescommandtext.substring(0, 11).equalsIgnoreCase("CREATESTOCK")) // 28 - 34 
		{
			System.out.println("Command 28 - 34");
		} 
		
		if (nospacescommandtext.substring(0, 11).equalsIgnoreCase("CREATETRACK")) // 39 - 49
		{
			System.out.println("Command 39 - 49");
		} 
		
		if (nospacescommandtext.substring(0, 10).equalsIgnoreCase("@RUNSTRING")) // 52
		{
			System.out.println("Command 52");
		}
		
		if (nospacescommandtext.substring(5, 9).equalsIgnoreCase("VIEW") || nospacescommandtext.substring(4, 8).equalsIgnoreCase("VIEW")) // 55
		{
			System.out.println("Command 55 - 56");
		}
		
		if (this.commandText.equalsIgnoreCase("COMMIT")) // 60
		{
			System.out.println("Command 60");
		}
		
		if (nospacescommandtext.substring(6, 11).equalsIgnoreCase("STOCK") || nospacescommandtext.substring(8, 13).equalsIgnoreCase("STOCK")) // 61
		{
			System.out.println("Command 61 - 65");
		}
		
		if (nospacescommandtext.substring(0, 3).equalsIgnoreCase("USE")) // 66
		{
			System.out.println("Command 66");
		}
		
		if (this.commandText.equalsIgnoreCase("Rule2 - Rule65")) // 67 || rule2 - rule65
		{
			System.out.println("Command 67");
		}
	}
}

