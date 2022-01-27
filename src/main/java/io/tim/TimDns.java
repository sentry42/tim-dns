package io.tim;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tim.dns.api.DnsApi;
import io.tim.dns.api.InvalidSignatureException;
import io.tim.dns.api.JacksonJsonProvider;
import io.tim.dns.api.TeapotApi;
import io.tim.dns.api.TeapotException;
import io.tim.dns.api.ThrowableExceptionMapper;

public class TimDns {

	public static final Logger logger = LoggerFactory.getLogger(TimDns.class);
	public static final String APP_NAME = "TimDns";

	private static Server server;
	private static HandlerCollection handlerCollection;

	private int port = 80;
	private byte[] privateKey = null;
	private Map<String, byte[]> publicKeys = new HashMap<>();

	public TimDns port(int port) {
		this.port = port;
		return this;
	}

	public int getPort() {
		return port;
	}

	public TimDns privateKey(byte[] privateKey) {
		this.privateKey = privateKey;
		return this;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public TimDns addPublicKey(String kid, byte[] key) {
		publicKeys.put(kid, key);
		return this;
	}

	public byte[] getPublicKey(String kid) {
		return publicKeys.get(kid);
	}

	public void start() throws Exception {
		if (server == null) {
			server = new Server();
			handlerCollection = new HandlerCollection();
			server.setHandler(handlerCollection);
		}

		Connector connector = new ServerConnector(server);
		((ServerConnector) connector).setName(APP_NAME);
		((ServerConnector) connector).setPort(port);
		((ServerConnector) connector).setHost("0.0.0.0");
		((ServerConnector) connector).setIdleTimeout(5000);

		server.addConnector(connector);

		ServletContainer servletContainer = new ServletContainer(getResourceConfig());
		ServletHolder servletHolder = new ServletHolder(servletContainer);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setDisplayName("TimDns Servlet Context Handler");
		String path = "/";
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (!"/".equals(path)) {
			context.setContextPath(path);
		}
		context.addServlet(servletHolder, "/*");

		// context.addServlet(Bootstrap.class, "/dns");

		handlerCollection.addHandler(context);

		logger.info("Starting server.");
		System.out.println("Starting server.");
		server.start();
		for (Handler handler : server.getHandlers()) {
			handler.start();
		}
		for (Connector serverConnector : server.getConnectors()) {
			serverConnector.start();
		}
		logger.info("Server started.");
		System.out.println("Server started.");
	}

	private ResourceConfig getResourceConfig() {
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.register(DnsApi.class);
		resourceConfig.register(TeapotApi.class);
		resourceConfig.register(JacksonJsonProvider.class);
		resourceConfig.register(ThrowableExceptionMapper.class);
		resourceConfig.register(InvalidSignatureException.class);
		resourceConfig.register(TeapotException.class);
		return resourceConfig;
	}

	public static class TimDnsBuilder {

		private int port = -1;
		private byte[] privateKey = null;
		private Map<String, byte[]> publicKeys = new HashMap<>();

		public TimDnsBuilder() {

		}

		public TimDnsBuilder port(int port) {
			this.port = port;
			return this;
		}

		public TimDnsBuilder privateKey(byte[] privateKey) {
			this.privateKey = privateKey;
			return this;
		}

		public TimDnsBuilder addPublicKey(String kid, byte[] key) {
			publicKeys.put(kid, key);
			return this;
		}

		public TimDns build() throws StartupException {
			validateBuildParameters();
			return new TimDns().port(port);
		}

		private void validateBuildParameters() throws StartupException {
			boolean parametersValid = true;
			StringBuilder sb = new StringBuilder();
			if (port <= 0) {
				parametersValid = false;
				sb.append("No valid port set.").append(System.lineSeparator());
			}
			if (privateKey != null && privateKey.length != 32) {
				parametersValid = false;
				sb.append("The private key must be exactly 32 bytes (256 bits).").append(System.lineSeparator());
			}
			if (!parametersValid) {
				throw new StartupException(sb.toString());
			}
		}
	}
}
