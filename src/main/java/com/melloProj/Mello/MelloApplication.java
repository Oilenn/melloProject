package com.melloProj.Mello;

import com.melloProj.Mello.repositories.project.ListRepository;
import com.melloProj.Mello.repositories.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@ComponentScan(basePackages = {"com.melloProj.Mello"})
@SpringBootApplication
public class MelloApplication implements CommandLineRunner {
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public static void main(String[] args) {
		SpringApplication.run(MelloApplication.class, args);
	}

	public void run(String... args) throws Exception {

	}
}
