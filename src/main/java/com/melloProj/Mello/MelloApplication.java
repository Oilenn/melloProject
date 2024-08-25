package com.melloProj.Mello;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Project;
import com.melloProj.Mello.repositories.ListRepository;
import com.melloProj.Mello.repositories.ProjectRepository;
import com.melloProj.Mello.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@EnableJpaRepositories
@ComponentScan(basePackages = {"com.melloProj.Mello"})
@SpringBootApplication
public class MelloApplication implements CommandLineRunner {
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DatabaseCleanupService databaseCleanupService;
	public static void main(String[] args) {
		SpringApplication.run(MelloApplication.class, args);
	}

	public void run(String... args) throws Exception {

	}
}
