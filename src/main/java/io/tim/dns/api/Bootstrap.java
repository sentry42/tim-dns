package io.tim.dns.api;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Swagger;

@SuppressWarnings("serial")
public class Bootstrap extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		Info info = new Info().title("Swagger Server").description("Tims DNS Api").termsOfService("")
				.contact(new Contact().email("sentry42@gmail.com"))
				.license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"));

		Swagger swagger = new Swagger().info(info);

		new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
	}
}
