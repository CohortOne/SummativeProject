package crg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crg.model.VehStatus;

@Repository
public interface VehStatusRepo extends JpaRepository<VehStatus, Integer> {

}
