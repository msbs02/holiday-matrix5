package com.holidaymatrix.holiday_matrix_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.holidaymatrix"})
@EnableJpaRepositories(basePackages = {"com.holidaymatrix.repository"})
@EntityScan(basePackages = {"com.holidaymatrix.model"})
public class HolidayMatrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(HolidayMatrixApplication.class, args);
	}
}
