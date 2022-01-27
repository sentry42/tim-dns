package io.tim.dns.api;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
@Produces({ MediaType.APPLICATION_JSON })
public class JacksonJsonProvider extends JacksonJaxbJsonProvider {

	public JacksonJsonProvider() {
		setMapper(JsonUtil.getBaseMapper());
	}
}