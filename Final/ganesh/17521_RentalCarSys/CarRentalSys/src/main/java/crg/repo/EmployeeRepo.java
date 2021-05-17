package crg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import crg.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
