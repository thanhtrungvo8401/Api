package fusikun.com.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.Customer;

@Repository(value = "customerDAO")
@Transactional(rollbackOn = Exception.class)
public class CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Customer insert(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(customer);
		return customer;
	}

	public void update(final Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(customer);
	}

	public Customer findById(final int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Customer.class, id);
	}

	public void delete(final Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		session.remove(customer);
	}

	public List<Customer> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("FROM Customer", Customer.class).getResultList();
	}
}
