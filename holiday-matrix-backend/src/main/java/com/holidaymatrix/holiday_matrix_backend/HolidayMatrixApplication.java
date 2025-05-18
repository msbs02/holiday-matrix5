package com.holidaymatrix.holiday_matrix_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.holidaymatrix"})
@EnableJpaRepositories(basePackages = {"com.holidaymatrix.repository"})
@EntityScan(basePackages = {"com.holidaymatrix.model"})
@EnableScheduling
public class HolidayMatrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(HolidayMatrixApplication.class, args);
	}
}