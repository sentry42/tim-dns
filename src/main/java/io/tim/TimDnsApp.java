package io.tim;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tim.TimDns.TimDnsBuilder;
import jdk.internal.jline.internal.Nullable;

public class TimDnsApp {

	private static TimDns instance = null;

	public static final Logger logger = LoggerFactory.getLogger(TimDnsApp.class);

	public static void main(String[] args) throws Exception {
		System.out.println("System stdout");
		System.err.println("System stderr");
		logger.debug("Debug logger");
		logger.info("Info logger");
		logger.warn("Warn logger");
		logger.error("Error logger");

		try {
			instantiateInstance(args);
			instance.start();
		} catch (StartupException se) {
			System.out.println(
					"Start up failure due to incorrect arguements." + System.lineSeparator() + se.getMessage());
			logger.error("Start up failure due to incorrect arguements.", se);
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Start up failure due to exception." + System.lineSeparator() + e.getMessage());
			logger.error("Start up failure due to exception.", e);
			System.exit(1);
		}
	}

	private static void instantiateInstance(String[] args) throws StartupException {
		Map<TimDnsArgs, Object[]> validatedArgs = TimDnsApp.validateArgTypes(TimDnsApp.parseArgs(args));
		if (validatedArgs.containsKey(TimDnsArgs.HELP)) {
			// print usage
			System.exit(0);
		}
		TimDnsBuilder builder = new TimDns.TimDnsBuilder();
		for (TimDnsArgs arg : TimDnsArgs.values()) {
			setArg(builder, arg, validatedArgs.get(arg));
		}
		instance = builder.build();
	}

	private static void setArg(TimDnsBuilder builder, TimDnsArgs arg, @Nullable Object[] parameterArgs) {

		switch (arg) {
		case PORT: {
			if (parameterArgs != null) {
				builder.port((int) parameterArgs[0]);
			}
			break;
		}
		case PRIVATE_KEY: {
			if (parameterArgs != null) {
				builder.privateKey(DatatypeConverter.parseHexBinary((String) parameterArgs[0]));
			}
			break;
		}
		case PUBLIC_KEY: {
			if (parameterArgs != null) {
				builder.addPublicKey((String) parameterArgs[0],
						DatatypeConverter.parseHexBinary((String) parameterArgs[1]));
			}
			break;
		}
		case HELP: {
			break;
		}
		default:
			throw new IllegalStateException("'" + arg.name() + "' is not properly supported.");
		}
	}

	/**
	 * Parses the arguments in the {@code args} parameters and returns a
	 * {@code Map<TimDnsArgs, String[]>} with all the parameter values keyed by the
	 * argument name.
	 * <p>
	 * This method doesn't check that the parameters of arguments match the expected
	 * parameter types i.e. if the value of the 'port' argument is provided as 'foo'
	 * then this method will set 'for' as the value of the 'port' parameter even
	 * though it should be an integer.
	 * 
	 * @param args
	 * @return
	 */
	private static Map<TimDnsArgs, String[]> parseArgs(String[] args) {
		Map<TimDnsArgs, String[]> argsMap = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			String argString = args[i];
			TimDnsArgs arg;

			if (argString.startsWith("---")) {
				throw new IllegalArgumentException("Command-line arguments may not start with '---'.");
			} else if (argString.startsWith("--")) {
				arg = TimDnsArgs.forName(argString);
			} else if (argString.startsWith("-")) {
				arg = TimDnsArgs.forFlag(argString);
			} else {
				throw new IllegalArgumentException("Command line argument '" + argString + "' not recognised.");
			}

			int numParams = arg.paramTypes().length;
			String[] argParams = new String[numParams];
			if (numParams > 0) {
				for (int j = 0; j < numParams; j++) {
					argParams[j] = args[i + j + 1];
				}
				i += numParams;
			}
			argsMap.put(arg, argParams);
		}
		return argsMap;
	}

	/**
	 * Instantiates all of the parameters for the arguments for TimDns as the
	 * correct type.
	 * 
	 * @param TimDnsArgs
	 * @return
	 */
	private static Map<TimDnsArgs, Object[]> validateArgTypes(Map<TimDnsArgs, String[]> TimDnsArgs) {
		Map<TimDnsArgs, Object[]> argsMap = new HashMap<>();
		for (TimDnsArgs TimDnsArg : TimDnsArgs.keySet()) {
			Object[] argParams = new Object[TimDnsArg.paramTypes().length];
			for (int i = 0; i < argParams.length; i++) {
				Class<?> classOfParam = TimDnsArg.paramTypes()[i];
				String paramStr = TimDnsArgs.get(TimDnsArg)[i];
				switch (classOfParam.getSimpleName()) {
				case "String":
					argParams[i] = paramStr;
					break;
				case "Integer":
					argParams[i] = Integer.parseInt(paramStr);
					break;
				case "Boolean":
					argParams[i] = Boolean.parseBoolean(paramStr);
					break;
				default:
					throw new IllegalStateException("Parameter " + i + " of argument " + TimDnsArg.name()
							+ " should be of type " + TimDnsArg.paramTypes()[i] + " which is not supported.");
				}
			}
			argsMap.put(TimDnsArg, argParams);
		}
		return argsMap;
	}

	public static TimDns getInstance() {
		return instance;
	}

	/**
	 * Command-line arguments recognised by the {@link TimDnsApp}.
	 * 
	 * @author Sentry
	 *
	 */
	public enum TimDnsArgs {

		//@formatter:off
		PORT(
				"-p",
				"--port",
				"The port TimDns will listen on (defaults to 80)",
				new Class<?>[] { Integer.class },
				new Object[] { 80 }
			),
		PRIVATE_KEY(
				"-privkey",
				"--private-key",
				"The private key TimDns uses to sign data.",
				new Class<?>[] { String.class },
				new Object[] { null }
			),
		PUBLIC_KEY(
				"-pubkey",
				"--public-key",
				"A public key used to verify a token from a remote system. Format is \"key ID> <key value>\".",
				new Class<?>[] { String.class, String.class },
				new Object[] { null, null }
			),
		HELP(
				"-h",
				"--help",
				"Displays this help text",
				new Class<?>[0],
				new Object[0]
			);
		//@formatter:on

		private String flag;
		private String argName;
		private String description;
		private Class<?>[] paramTypes;
		private Object[] defaultValues;

		private TimDnsArgs(String flag, String argName, String description, Class<?>[] paramTypes,
				Object[] defaultValues) {
			this.flag = flag;
			this.argName = argName;
			this.description = description;
			this.paramTypes = paramTypes;
			this.defaultValues = defaultValues;
		}

		public String flag() {
			return flag;
		}

		public String argName() {
			return argName;
		}

		public String description() {
			return description;
		}

		public Class<?>[] paramTypes() {
			return paramTypes;
		}

		public Object[] defaultValues() {
			return defaultValues;
		}

		public static TimDnsArgs forFlag(String flag) {
			switch (flag) {
			case "-p":
				return PORT;
			case "-h":
				return HELP;
			default:
				throw new IllegalArgumentException("'" + flag + "' is not a valid flag for TimDns arguments.");
			}
		}

		public static TimDnsArgs forName(String name) {
			switch (name) {
			case "--port":
				return PORT;
			case "--help":
				return HELP;
			default:
				throw new IllegalArgumentException("'" + name + "' is not a valid name for TimDns arguments.");
			}
		}

		public static String printUsage() {
			return null;
		}
	}

}
