package com.example.ReadingList;

import com.example.ReadingList.resolver.ReaderHandlerMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@SpringBootApplication
public class ReadingListApplication extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active");
		if(profile == null){
			System.setProperty("spring.profiles.active", "development");
		}

		SpringApplication.run(ReadingListApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		argumentResolvers.add(new ReaderHandlerMethodArgumentResolver());
	}
}
