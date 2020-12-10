package fusikun.com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.model.Customer;
import fusikun.com.api.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping(value = {"/customers" })
	public List<Customer> listCustomer() {
		return customerService.findAll();
	}

	@GetMapping("/customers/{id}")
	public Customer viewCustomer(@PathVariable int id) {
		Customer customer = customerService.findById(id);
		return customer;
	}

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerService.insert(customer);
	}

}
