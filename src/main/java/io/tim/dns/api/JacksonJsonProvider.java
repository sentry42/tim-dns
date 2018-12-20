package io.tim.dns.api;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
@Produces({ MediaType.APPLICATION_JSON })
public class JacksonJsonProvider extends JacksonJaxbJsonProvider {

   public JacksonJsonProvider() {

      ObjectMapper objectMapper =
            new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                  .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                  .registerModule(new Jdk8Module()) // enable support for Optional
                  .registerModule(new JavaTimeModule()) // enable support for jsr310 date/time handling
                  .setDateFormat(new RFC3339DateFormat());

      setMapper(objectMapper);
   }
}