package org.example.miniproject2.service;

import lombok.RequiredArgsConstructor;
import org.example.miniproject2.model.Employee;
import org.example.miniproject2.repository.DepartmentRepository;
import org.example.miniproject2.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void deleteDepartment(Long id) {
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        for (Employee e : employees) {
            e.setDepartment(null);
        }

        employeeRepository.saveAll(employees);
        departmentRepository.deleteById(id);
    }
}