package fusikun.com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.CustomerRepository;
import fusikun.com.api.model.Customer;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer findById(final long id) {
		Optional<Customer> optCustomer = customerRepository.findById(id);
		if (optCustomer.isPresent())
			return optCustomer.get();
		else
			return null;
	}

	public Customer insert(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer update(Customer customer) {
		return customerRepository.save(customer);
	}

	public void delete(long id) {
		boolean isIdExist = customerRepository.existsById(id);
		if (isIdExist) {
			customerRepository.deleteById(id);
		}
	}
}
