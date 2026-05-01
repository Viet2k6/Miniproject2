package org.example.miniproject2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.miniproject2.model.Employee;
import org.example.miniproject2.repository.DepartmentRepository;
import org.example.miniproject2.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employee-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Employee employee, BindingResult result, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employee-form";
        }

        if (!file.isEmpty()) {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + extension;
            Path path = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            employee.setAvatar(fileName);
        }

        employeeRepository.save(employee);
        return "redirect:/";
    }
}