package crg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crg.Service.CustomerService;
import crg.model.Customer;
import crg.repo.CustomerRepo;

@Controller
public class CustomerController {
			
		@Autowired
		private CustomerRepo custRepo;
		
		@Autowired
		private CustomerService custService;
				
		@RequestMapping("/customer")
		public String ShowCustList(Model model) {
			
			return listByPage(model, 1, "custId" , "ASC" );
			
		}
		
		
//		@RequestMapping("/customer")
//		public String ShowCustList(Model model) {
//			
//			return listByPage(model, 1, "custId" , "ASC" );
//		}
		

		
		@GetMapping("/page/{pageNumber}")
		public String listByPage(Model model,
				                 @PathVariable("pageNumber") int currentPage,
				                 @Param("sortField") String sortField,
				                 @Param("sortDir") String sortDir) {		
			Page<Customer> page = custService.listAll(currentPage, sortField,sortDir); 
			long totalItems = page.getTotalElements();
			int totalPages = page.getTotalPages();
			List<Customer> listcust = page.getContent();
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalItems", totalItems);
			model.addAttribute("totalPages", totalPages);		
			model.addAttribute("listcust", listcust);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDirection", sortDir.equalsIgnoreCase("ASC") ? "DESC" : "ASC");
			return "customer";
			
		}
			
						
			@GetMapping("/newCustomerReg")
			public String newCustomerReg(Model model) {
				List<Customer> customers =  custRepo.findAll();
				model.addAttribute("customers",customers);
				model.addAttribute("customer" , new Customer());
				
				return "newCustomerReg";
			}
			@PostMapping("/customer/save")
			public String  saveCustomer(Customer customer) {
				custRepo.save(customer);
				return "redirect:/customer";
			}

			@GetMapping("/UpdateCust/{custId}")
			public String ShowEditForm(@PathVariable("custId") Long custId, Model model) {
				Customer customer =   custRepo.findById(custId).get();
				model.addAttribute("Customer", customer);
				
//				List<Role> roles = custRepo.findAll();
//				model.addAttribute("roles", roles);
				
				return "UpdateCust";
			}
			
			@GetMapping("/deleteCust/{custId}")
			public String deleteCustomer(@PathVariable("custId") Long custId, Model model) {
				custRepo.deleteById(custId);
				
				return "redirect:/customer";
			
		}
}



		
		
