package org.example.miniproject2.config;

import lombok.RequiredArgsConstructor;
import org.example.miniproject2.model.Department;
import org.example.miniproject2.model.Employee;
import org.example.miniproject2.repository.DepartmentRepository;
import org.example.miniproject2.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            if (departmentRepository.count() == 0) {
                Department d1 = new Department(null, "IT", "Hà Nội", null);
                Department d2 = new Department(null, "Marketing", "Hà Nội", null);
                departmentRepository.saveAll(Arrays.asList(d1, d2));

                Employee e1 = new Employee(null, "Việt", 20, null, "ACTIVE", d1);
                Employee e2 = new Employee(null, "Thành", 22, null, "ACTIVE", d1);
                Employee e3 = new Employee(null, "Tuyến", 28, null, "INACTIVE", d2);
                employeeRepository.saveAll(Arrays.asList(e1, e2, e3));
            }
        };
    }
}