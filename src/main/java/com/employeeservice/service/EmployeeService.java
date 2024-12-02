package com.employeeservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employeeservice.entity.Employee;
import com.employeeservice.exception.UserNotFoundException;
import com.employeeservice.model.EmployeeDto;
import com.employeeservice.repository.EmployeeRepository;
import com.employeeservice.util.EmployeeMapper;

@Service
public class EmployeeService {

    @Autowired
    private ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> findAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                        .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                        .collect(Collectors.toList());
    }

    public void addEmployee(EmployeeDto employeeDto){
        employeeDto.setEmployeeCode(UUID.randomUUID().toString());
        EmployeeMapper.employeeToEmployeeDto(
            employeeRepository.save(modelMapper.map(employeeDto, Employee.class)));
    }

    public EmployeeDto findEmployeeById(Long id){
        Employee employee = employeeRepository.findEmployeeById(id)
            .orElseThrow(() -> new UserNotFoundException("Employee by id " + id + " was not found"));
        return modelMapper.map(employee, EmployeeDto.class);   
    }

    public void updateEmployee(EmployeeDto employeeDto){
        EmployeeMapper.employeeToEmployeeDto(
            employeeRepository.save(modelMapper.map(employeeDto, Employee.class)));
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public List<Object> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		Page<Employee> page = employeeRepository.findAll(pageable);
        List<Employee> employees = page.getContent();
		List<EmployeeDto> listEmployees = employees.stream()
                        .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                        .collect(Collectors.toList());
        return Arrays.asList(listEmployees, page.getTotalPages(), page.getTotalElements());                 
	}
}
