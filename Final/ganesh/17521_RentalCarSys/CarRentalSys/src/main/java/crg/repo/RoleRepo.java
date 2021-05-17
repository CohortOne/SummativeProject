package crg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import crg.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
