package com.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class SpringAppApplication {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homePage() {

		String data = "SPRING MVC APP v1.0";

		ModelAndView mv = new ModelAndView();

		mv.setViewName("index");
		mv.addObject("data", data);

		return mv;

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

}
