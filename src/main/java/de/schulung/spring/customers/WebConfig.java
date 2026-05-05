package de.schulung.spring.customers;

import jakarta.annotation.Nonnull;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.servlet.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  // für Controller
  @Bean
  WebMvcConfigurer registerStaticResourceTypes() {
    return new WebMvcConfigurer() {
      @Override
      public void configureContentNegotiation(
        @Nonnull
        ContentNegotiationConfigurer configurer
      ) {
        configurer
          .mediaType("yml", MediaType.APPLICATION_YAML)
          .mediaType("yaml", MediaType.APPLICATION_YAML);
      }
    };
  }

  // für statische Dateien
  @Bean
  public WebServerFactoryCustomizer<?> mimeMappings() {
    return factory -> {
      if (factory instanceof ConfigurableServletWebServerFactory servletFactory) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("yml", MediaType.APPLICATION_YAML_VALUE);
        mappings.add("yaml", MediaType.APPLICATION_YAML_VALUE);
        servletFactory.setMimeMappings(mappings);
      }
    };
  }

}