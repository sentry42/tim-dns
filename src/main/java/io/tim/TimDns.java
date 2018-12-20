package io.tim;

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
import io.tim.dns.api.TeapotApi;

public class TimDns {

   public static final Logger logger = LoggerFactory.getLogger(TimDns.class);

   private static Server server;
   private static HandlerCollection handlerCollection;

   public TimDns(int port) throws Exception {
      if (server == null) {
         server = new Server();
         handlerCollection = new HandlerCollection();
         server.setHandler(handlerCollection);
      }

      Connector connector = new ServerConnector(server);
      ((ServerConnector) connector).setName("TimDns");
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
      return resourceConfig;
   }

   public static void main(String[] args) throws Exception {
      System.out.println("System stdout");
      System.err.println("System stderr");
      logger.debug("Debug logger");
      logger.info("Info logger");
      logger.warn("Warn logger");
      logger.error("Error logger");
      int port = 80;
      try {
         if (args == null || args.length == 0) {
            logger.error(
                  "The first argument should be an integer for the server's listening port, Stupid! Defaulting to port 80.");
            System.out.println(
                  "The first argument should be an integer for the server's listening port, Stupid! Defaulting to port 80.");
         } else {
            port = Integer.parseInt(args[0]);
         }
      } catch (NumberFormatException nfe) {
         logger.error(
               "The first argument should be an integer for the server's listening port, Stupid! Defaulting to port 80.");
         System.out.println(
               "The first argument should be an integer for the server's listening port, Stupid! Defaulting to port 80.");
      }
      logger.info("TimDns configured to start on port " + port);
      System.out.println("TimDns configured to start on port " + port);
      new TimDns(port);
   }

}
