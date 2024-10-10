package com.afr.fms;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.afr.fms.Common.Service.FilesStorageService;
@SpringBootApplication
@EnableScheduling
public class AFRFMSApplication extends SpringBootServletInitializer implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;
public static void main(String[] args) {
		SpringApplication.run(AFRFMSApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AFRFMSApplication.class);
	}
	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
}
