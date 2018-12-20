package io.tim.dns.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.tim.factories.TeapotApiServiceFactory;

@Path("/")
@io.swagger.annotations.Api(description = "the teapot API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class TeapotApi {
   private final TeapotApiService delegate;

   public TeapotApi(@Context ServletConfig servletContext) {
      TeapotApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("TepotApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (TeapotApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }

      if (delegate == null) {
         delegate = TeapotApiServiceFactory.getTeapotApi();
      }

      this.delegate = delegate;
   }

   @GET
   @Path("/teapot")
   @Consumes({ "application/json" })
   @io.swagger.annotations.ApiOperation(value = "Makes tea.", notes = "Makes tea.", response = Void.class, tags = {})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 408, message = "Making tea", response = Void.class) })
   public Response teapot(@Context SecurityContext securityContext) throws NotFoundException {
      System.out.println("DNS operation");
      return delegate.teapot(securityContext);
   }
}
