package com.github.kolorobot.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.github.kolorobot" }, excludeFilters = @Filter(type = FilterType.ANNOTATION, value = Configuration.class))
@Import(PersistenceConfig.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final String TILES = "/WEB-INF/tiles/tiles.xml";
	private static final String VIEWS = "/WEB-INF/views/**/views.xml";
	
	private static final String RESOURCES_HANDLER = "/resources/";
	private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";
	
	@Bean
	public TilesViewResolver configureTilesViewResolver() {
		return new TilesViewResolver();
	}
	
	@Bean
	public TilesConfigurer configureTilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] {TILES, VIEWS});
		return configurer;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(new SessionStatusInterceptor());
	}
	
	public static class SessionStatusInterceptor implements WebRequestInterceptor {
		
		private static final Logger LOG = LoggerFactory.getLogger(SessionStatusInterceptor.class);
		
		@Override
		public void preHandle(WebRequest request) throws Exception {
		
		}

		@Override
		public void postHandle(WebRequest request, ModelMap model) throws Exception {
		
		}

		@Override
		public void afterCompletion(WebRequest request, Exception ex) throws Exception {
			LOG.trace("current session attribute names: " + attributeNamesAsString(request));
		}

		private String attributeNamesAsString(WebRequest request) {
			return Arrays.asList(request.getAttributeNames(WebRequest.SCOPE_SESSION)).toString();
		}
	}
}
