package io.tim.dns.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class NotFoundException extends ApiException {
   private int code;

   public NotFoundException(int code, String msg) {
      super(code, msg);
      this.code = code;
   }
}
