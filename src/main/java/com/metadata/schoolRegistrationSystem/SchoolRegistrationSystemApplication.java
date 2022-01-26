package com.metadata.schoolRegistrationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolRegistrationSystemApplication {
	public static final int COURSES_LIMIT = 5;
	public static final int STUDENTS_LIMIT = 50;
	public static void main(String[] args) {
		SpringApplication.run(SchoolRegistrationSystemApplication.class, args);
	}

}
