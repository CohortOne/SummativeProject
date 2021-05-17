package crg.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import crg.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

	//	@Query (value = "SELECT ")
}
