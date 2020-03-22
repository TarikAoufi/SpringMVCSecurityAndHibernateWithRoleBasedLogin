package fr.aoufi.springmvcsecurity.configuration;

import fr.aoufi.springmvcsecurity.converter.RoleConverter;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"fr.aoufi.springmvcsecurity"})
public class ApplicationConfiguration implements WebMvcConfigurer {
	
	@Autowired
	RoleConverter roleConverter;
	
	/**
	 * Configurer ViewResolvers pour fournir des vues préférées.
	 * InternalResourceViewResolver: Aide à mapper les noms des vues logiques pour afficher 
	 * directement les fichiers dans un certain répertoire préconfiguré.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}
	
	/**
     * Configurer MessageSource pour rechercher tout message de validation / d'erreur 
     * dans les fichiers de propriétés internationalisés.
     * @return
     */
	@Bean({"messageSource"})
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("/locale/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	/**
	 * Configurer ResourceHandlers pour servir des ressources statiques comme CSS/Javascript etc ...
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/static/")
				.setCacheControl(CacheControl.maxAge(2L, TimeUnit.HOURS).cachePublic());
		registry.addResourceHandler("/static/**").addResourceLocations("/static/")
				.setCacheControl(CacheControl.maxAge(2L, TimeUnit.HOURS).cachePublic());
	}
	
	/**
	 * Configurer le convertisseur à utiliser.
	 * Dans notre exemple, nous avons besoin d'un convertisseur pour convertir les valeurs de chaîne [Roles] en Roles
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(this.roleConverter);
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(Boolean.valueOf(true));
	}

	@Bean
	public ThemeSource themeSource() {
		ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
		themeSource.setBasenamePrefix("theme/");
		return themeSource;
	}

	@Bean
	public ThemeResolver themeResolver() {
		CookieThemeResolver resolver = new CookieThemeResolver();
		resolver.setDefaultThemeName("theme_2");
		return resolver;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return  new CookieLocaleResolver();
	}

	@Bean
	public ThemeChangeInterceptor themeChangeInterceptor() {
		ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
		themeChangeInterceptor.setParamName("theme");
		return themeChangeInterceptor;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.themeChangeInterceptor());
		registry.addInterceptor(this.localeChangeInterceptor());
	}
}
