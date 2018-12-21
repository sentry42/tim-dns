/*
 * Tims DNS API
 * Tims DNS Api
 *
 * OpenAPI spec version: 1.0.0
 * Contact: sentry42@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.tim.dns.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Represents a failed outcome
 */
@ApiModel(description = "Represents a failed outcome")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class Error {

   public class ErrorCode {
      public static final int NO_RECORD = 1;
   }

   @JsonProperty("code")
   private int code = 0;

   @JsonProperty("message")
   private String message = null;

   public Error code(int code) {
      this.code = code;
      return this;
   }

   @JsonProperty("code")
   @ApiModelProperty(required = true, value = "")
   @NotNull
   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public Error message(String message) {
      this.message = message;
      return this;
   }

   /**
    * @return message
    **/
   @JsonProperty("message")
   @ApiModelProperty(value = "")
   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   @Override
   public boolean equals(java.lang.Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Error error = (Error) o;
      return Objects.equals(this.code, error.code) && Objects.equals(this.message, error.message);
   }

   @Override
   public int hashCode() {
      return Objects.hash(code, message);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("class Error {\n");

      sb.append("    code: ").append(toIndentedString(code)).append("\n");
      sb.append("    message: ").append(toIndentedString(message)).append("\n");
      sb.append("}");
      return sb.toString();
   }

   /**
    * Convert the given object to string with each line indented by 4 spaces (except the first line).
    */
   private String toIndentedString(java.lang.Object o) {
      if (o == null) {
         return "null";
      }
      return o.toString().replace("\n", "\n    ");
   }
}
