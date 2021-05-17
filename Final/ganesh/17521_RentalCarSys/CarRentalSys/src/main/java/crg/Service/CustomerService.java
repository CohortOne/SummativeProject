package crg.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import crg.model.Customer;
import crg.repo.CustomerRepo;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepo custRepo;
	
		
	public Page<Customer> listAll(int pageNumber, String sortfield, String sortDir) {
		
		Sort sort = Sort.by("custId");
		sort = sortDir.equals("ASC")? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 20, sort);
		return custRepo.findAll(pageable);
				
	}
}

	

