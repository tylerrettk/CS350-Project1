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
		if (this.commandText.equalsIgnoreCase("@exit")) // 51
		{
			A_Command command = new CommandMetaDoExit();
			this.parserHelper.getActionProcessor().schedule(command);
		} 
		
		if (this.commandText.equalsIgnoreCase("( ( Rule#66 | Rule#67 ) ( ';' )? )*\r\n")) // 1
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO BRAKE id")) // 2 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SELECT DRAWBRIDGE id POSITION UP") || this.commandText.equalsIgnoreCase("DO SELECT DRAWBRIDGE id POSITION DOWN")) // 6
		{

		}
		
		if (this.commandText.equalsIgnoreCase("DO SELECT ROUNDHOUSE id POSITION angle CLOCKWISE") || this.commandText.equalsIgnoreCase("DO SELECT ROUNDHOUSE id POSITION angle COUNTERCLOCKWISE")) // 7 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SELECT SWITCH id PATH PRIMARY") || this.commandText.equalsIgnoreCase("DO SELECT SWITCH id PATH SECONDARY")) // 8 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET id DIRECTION FORWARD") || this.commandText.equalsIgnoreCase("DO SET id DIRECTION BACKWARD")) // 11
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET REFERENCE ENGINE id ")) // 12 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET id SPEED number")) // 15 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET REFERENCE ENGINE id")) // 22 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET id SPEED number")) // 23
		{

		}
		
		if (this.commandText.equalsIgnoreCase("DO SET REFERENCE ENGINE id ")) // 24 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("DO SET id SPEED number")) // 25 
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS BOX")) // 28 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS CABOOSE")) // 29
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS FLATBED")) // 30 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS PASSENGER")) // 31
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS TANK")) // 32 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK CAR id AS TENDER")) // 33
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )")) // 34 || come back to this one
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK BRIDGE DRAW id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 ANGLE angle")) // 39 || come back to this one
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("* CREATE TRACK BRIDGE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2")) // 40 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK CROSSING id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2")) // 41
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK CROSSOVER id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2\r\n" + "START coordinates_delta3 END coordinates_delta4\r\n")) // 42 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2\r\n" + "( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )\r\n")) // 43
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK END id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2")) // 44
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK LAYOUT id1 WITH TRACKS idn+")) // 45 
		{

		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK ROUNDHOUSE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA ORIGIN coordinates_delta1 ANGLE ENTRY angle1 START angle2 END angle3 WITH integer SPURS LENGTH number1 TURNTABLE LENGTH number2")) // 46
		{

		}
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK STRAIGHT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2")) // 47 
		{
			
		} 
		
		if (this.commandText.equalsIgnoreCase("CREATE TRACK SWITCH TURNOUT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) STRAIGHT DELTA START coordinates_delta1 END coordinates_delta2 CURVE DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number")) // 48
		{

		}

		if (this.commandText.equalsIgnoreCase("CREATE TRACK SWITCH WYE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 DISTANCE ORIGIN number1 DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number2")) // 49
		{

		}
		
		if (this.commandText.equalsIgnoreCase("@RUN string")) // 52
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("CLOSE VIEW id")) // 55
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("OPEN VIEW id1 ORIGIN ( coordinates_world | ( '$' id2 ) ) WORLD WIDTH integer1 SCREEN WIDTH integer2 HEIGHT integer3")) // 56
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("COMMIT")) // 60
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("COUPLE STOCK id1 AND id2")) // 61
		{
			
		}
		if (this.commandText.equalsIgnoreCase("LOCATE STOCK id1 ON TRACK id2 DISTANCE number FROM ( START | END )")) // 62
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("UNCOUPLE STOCK id1 AND id2")) // 65
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("USE id AS REFERENCE coordinates_world ")) // 66
		{
			
		}
		
		if (this.commandText.equalsIgnoreCase("Rule#2 through Rule#65")) // 67
		{
			
		}
		
	}
	
}

