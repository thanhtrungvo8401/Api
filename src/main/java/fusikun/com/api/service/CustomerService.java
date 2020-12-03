package fusikun.com.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.CustomerDAO;
import fusikun.com.api.model.Customer;

@Service
public class CustomerService {
	@Autowired
	private CustomerDAO customerDAO;

	public List<Customer> findAll() {
		return customerDAO.findAll();
	}

	public Customer findById(final int id) {
		return customerDAO.findById(id);
	}

	public Customer insert(Customer customer) {
		return customerDAO.insert(customer);
	}

	public void update(Customer customer) {
		customerDAO.update(customer);
	}

	public void delete(int id) {
		Customer customer = customerDAO.findById(id);
		if (customer != null) {
			customerDAO.delete(customer);
		}
	}
}
