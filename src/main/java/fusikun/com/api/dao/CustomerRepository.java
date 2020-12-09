package fusikun.com.api.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.Customer;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
