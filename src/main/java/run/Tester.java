package run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import commands.*;
import lib.Launchpad;

public class Tester {

	private static Launchpad LP;
	private static List<Command> commands = new ArrayList<>();
	private static String lastCommand = "";

	public static void main(String[] args) {

		if (args.length > 0) {
			LP = new Launchpad(args[0]).init();
		} else {
			LP = new Launchpad().init();
		}
		if (LP == null)
			System.exit(1);
		LP.markGridClear(true);
		
		
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		addCommand(new HelpCommand(commands));
		addCommand(new ClearCommand(LP));
		addCommand(new UpCommand(LP));
		addCommand(new LeftCommand(LP));
		addCommand(new RandRedrawCommand(LP));
		addCommand(new RandCommand(LP, 20));
		addCommand(new CycleCommand(LP, 5));
		addCommand(new PaletteCommand(LP));
		addCommand(new ColorCommand(LP,5,5,2,5));
		addCommand(new AlphaCommand(LP));
		addCommand(new QuitCommand());

		execute(HelpCommand.KEY);

		while (acceptCommand(cin)){};

		LP.close(false);
		System.exit(0);

	}

	private static boolean execute(String commandStr) {
		for (Command command : commands) {
			if (commandStr.trim().startsWith(command.getKey())) {
				return command.launch(commandStr);
			}
		}
		return true;
	}

	private static void addCommand(Command command) {
		commands.add(command);
		commands.sort(Comparator.comparing(Command::getKey));
	}

	public static boolean acceptCommand(BufferedReader cin) {
		boolean success = true;
		try {
			String comm = cin.readLine().toLowerCase();
			if (comm.isEmpty()) {
				if (!lastCommand.isEmpty()) {
					comm = lastCommand;
				} else {
					return success;
				}
			}
			success = execute(comm);
			if (success) {
				lastCommand = comm;
			}
			return success;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}
}
