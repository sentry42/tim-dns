package io.tim.factories;

import io.tim.dns.api.TeapotApiService;
import io.tim.impl.TeapotApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class TeapotApiServiceFactory {
   private final static TeapotApiService service = new TeapotApiServiceImpl();

   public static TeapotApiService getTeapotApi() {
      return service;
   }
}
