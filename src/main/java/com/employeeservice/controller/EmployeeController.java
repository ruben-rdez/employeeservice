package com.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employeeservice.model.EmployeeDto;
import com.employeeservice.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Value("${page.size}")
    private int pageSize;

    @Value("${page.sortfield}")
    private String sortField;

    @Value("${page.sortdir}")
    private String sortDir;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public String findAllEmployees(Model model){
        return findEmployeesPaginated(1, this.sortField, this.sortDir, model);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		EmployeeDto employee = new EmployeeDto();
		model.addAttribute("employee", employee);
		return "new_employee";
	}

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") EmployeeDto employeeDto){
        employeeService.addEmployee(employeeDto);
        return "redirect:/employee/all";
    }

    @GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		EmployeeDto employee = employeeService.findEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
    
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDto employeeDto){
        employeeService.updateEmployee(employeeDto);
        return "redirect:/employee/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/employee/all";
    }

    @GetMapping("/page/{pageNo}")
	public String findEmployeesPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		
        List<Object> objEmployees = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", objEmployees.get(1));
		model.addAttribute("totalItems", objEmployees.get(2));
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listEmployees", objEmployees.get(0));
		return "index";
	}
}
