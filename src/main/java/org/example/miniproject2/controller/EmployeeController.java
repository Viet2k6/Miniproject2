package org.example.miniproject2.controller;

import lombok.RequiredArgsConstructor;
import org.example.miniproject2.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-list";
    }
}