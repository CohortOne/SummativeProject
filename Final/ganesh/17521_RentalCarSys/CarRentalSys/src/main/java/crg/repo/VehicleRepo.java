package crg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import crg.model.Vehicle;

public interface VehicleRepo extends JpaRepository <Vehicle, Long>{

}
