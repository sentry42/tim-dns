package io.tim.factories;

import io.tim.dns.api.DefaultApiService;
import io.tim.impl.DefaultApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DefaultApiServiceFactory {
   private final static DefaultApiService service = new DefaultApiServiceImpl();

   public static DefaultApiService getDefaultApi() {
      return service;
   }
}
