package simulator.launcher;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.*;

public class Main {

	private enum execMode {
		
		CONSOLE("console"),GUI("gui");
		
		private String mode_tag;
		
		execMode(String mode_tag) {
			// TODO Auto-generated constructor stub
			this.mode_tag = mode_tag;
		}
		
		private String getModeTag() {
			
			return this.mode_tag;
		}
			
	}
	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	//Creamos nosotros
	private static Integer ticks;
	private static execMode _execMode;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			//Creamos nosotros
			parseTicks(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
        //Creamos nosotros
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks Simulators loop").build());
		//Nuevo por el modo
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution mode (possible values: 'batch' \n " + "(batch mode), 'gui' (interface mode).").build());
		
		
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		
		/*if (_execMode != execMode.GUI) {
			_inFile = line.getOptionValue("i");
			if (_inFile == null) {
				throw new ParseException("An events file is missing");
			}
		}*/
		
		if (line.hasOption("i")) {
			_inFile = line.getOptionValue("i");
		}
	}
		
	private static void parseOutFileOption(CommandLine line) throws ParseException {
		
		if (_execMode != execMode.GUI) {
		_outFile = line.getOptionValue("o");
		}
	}
	
	//Parse creado por nosotros
	private static void parseTicks(CommandLine line) throws ParseException {
		
		//_execMode != execMode.GUI
		if (line.hasOption("t")) {
		String pt =line.getOptionValue("t",_timeLimitDefaultValue.toString());
		try {
			ticks=Integer.parseInt(pt);
			assert (ticks>0);
		}catch (Exception e) {
			
			throw new ParseException("Invalid ticks vlaue: " + pt);
		}
			}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		
		String mode = line.getOptionValue("m","gui");
		
		for (execMode m: execMode.values()) {
			if(m.getModeTag().equals(mode)) {
				_execMode = m;
				return;
			}
		}
		
		throw new ParseException("invalid execution mode"+ mode + ",");
	}

	private static void initFactories() {

		// TODO complete this method to initialize _eventsFactory
		List<Builder<LightSwitchingStrategy>> lblss = new ArrayList<>();
		
		lblss.add(new RoundRobinStrategyBuilder());
		lblss.add(new MostCrowdedStrategyBuilder());
		
		Factory<LightSwitchingStrategy> flss = new BuilderBasedFactory<>(lblss);
		List<Builder<DequeuingStrategy>> lbds = new ArrayList<>();
		
		lbds.add(new MoveFirstStrategyBuilder());
		lbds.add(new MoveAllStrategyBuilder());
		
		Factory<DequeuingStrategy> fds = new BuilderBasedFactory<>(lbds);
		List<Builder<Event>> lbe = new ArrayList<>();
		
		lbe.add(new NewJunctionEventBuilder(flss, fds));
		lbe.add(new NewCityRoadEventBuilder());
		lbe.add(new NewInterCityRoadEventBuilder());
		lbe.add(new NewVehicleEventBuilder());
		lbe.add(new SetWeatherEventBuilder());
		lbe.add(new NewSetContClassEventBuilder());
		_eventsFactory = new BuilderBasedFactory<>(lbe);
		
	}

	private static void startBatchMode() throws IOException {
		// TODO complete this method to start the simulation
		OutputStream out;
		InputStream in = new FileInputStream(new File(_inFile));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		if (_outFile == null) {
			out = System.out;
		} else {
			out = new FileOutputStream(new File(_outFile));
		}
		
		ctrl.loadEvents(in);
		ctrl.run(ticks, out);
		
		in.close();
		out.flush();
		out.close();
		
	}
	
	private static void startGUIMode() throws Exception {
		
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		if (_inFile != null) {
			InputStream in = new FileInputStream(new File(_inFile));
			
			try {
				ctrl.loadEvents(in);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		SwingUtilities.invokeAndWait(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainWindow(ctrl);
			}
		});
		
	}

	private static void start(String[] args) throws Exception {
		initFactories();
		parseArgs(args);
		
		if(_execMode==execMode.CONSOLE) {
			startBatchMode();
		}else {
			startGUIMode();
		}
		
		
	}
	
	

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
