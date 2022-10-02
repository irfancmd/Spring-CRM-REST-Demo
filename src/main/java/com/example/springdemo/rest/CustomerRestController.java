package com.example.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdemo.entity.Customer;
import com.example.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerService.getCustomers();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);
		
		if(customer == null) {
			throw new CustomerNotFoundException("Customer with ID: " + customerId + " does not exist.");
		}
		
		return customer;
	}
	
	// The sent JSON will be automatically converted into a Java object
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		// We are "Posting" so we want to add new data. So, we'll set the id to '0' which will make hibernate
		// generate an id automatically and thus creating a new entry in the database
		customer.setId(0);
		
		customerService.saveCustomer(customer);
		
		return customer;
	}

	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		
		return customer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);
		
		if(customer == null) {
			throw new CustomerNotFoundException("Customer with ID: " + customerId + " does not exist.");
		}
		
		customerService.deleteCustomer(customerId);
		
		return "Deleted customer with ID: " + customerId + ".";
	}
}
