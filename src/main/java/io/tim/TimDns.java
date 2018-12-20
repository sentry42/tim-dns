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

import io.tim.dns.api.DefaultApi;
import io.tim.dns.api.TeapotApi;

public class TimDns {

   private static Server server;
   private static HandlerCollection handlerCollection;

   public TimDns() throws Exception {
      if (server == null) {
         server = new Server();
         handlerCollection = new HandlerCollection();
         server.setHandler(handlerCollection);
      }

      Connector connector = new ServerConnector(server);
      ((ServerConnector) connector).setName("TimDns");
      ((ServerConnector) connector).setPort(80);
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
      } else {
         System.out.println("Not setting ServletContextHandler path");
      }
      context.addServlet(servletHolder, "/*");

      // context.addServlet(Bootstrap.class, "/dns");

      handlerCollection.addHandler(context);

      server.start();
      for (Handler handler : server.getHandlers()) {
         handler.start();
      }
      for (Connector serverConnector : server.getConnectors()) {
         serverConnector.start();
      }

   }

   private ResourceConfig getResourceConfig() {
      ResourceConfig resourceConfig = new ResourceConfig();
      resourceConfig.register(DefaultApi.class);
      resourceConfig.register(TeapotApi.class);
      return resourceConfig;
   }

   public static void main(String[] args) throws Exception {
      new TimDns();
   }

}