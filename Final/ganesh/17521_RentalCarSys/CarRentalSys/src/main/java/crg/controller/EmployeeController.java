package crg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import crg.model.Employee;
import crg.model.Role;
import crg.repo.EmployeeRepo;
import crg.repo.RoleRepo;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepo empRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
//	@GetMapping("/")
//	public String  ViewHomePage() {
//	return "index";
//	}
	
	@RequestMapping("/employee")
	public String ShowEmpList(Model model) {
		List<Employee> listEmp = empRepo.findAll(); 
		model.addAttribute("listEmp", listEmp);
		return "employee";
	}
	
	@GetMapping("/newEmployeeReg")
	public String newEmployeeReg(Model model) {
		List<Role> roles = roleRepo.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("employee" , new Employee());
		
		return "newEmployeeReg";
	}

	@PostMapping("/employee/save")
	public String saveEmployee(Employee employee) {
		empRepo.save(employee);
		return "redirect:/employee";
	}

	@GetMapping("/UpdateEmp/{empId}")
	public String ShowEditForm(@PathVariable("empId") Long empId, Model model) {
		Employee employee =   empRepo.findById(empId).get();
		model.addAttribute("Employee", employee);
		
		List<Role> roles = roleRepo.findAll();
		model.addAttribute("roles", roles);
		
		return "UpdateEmp";
	}
	
	@GetMapping("/delete/{empId}")
	public String deleteUser(@PathVariable("empId") Long empId, Model model) {
		empRepo.deleteById(empId);
		
		return "redirect:/employee";
		
	}

}
