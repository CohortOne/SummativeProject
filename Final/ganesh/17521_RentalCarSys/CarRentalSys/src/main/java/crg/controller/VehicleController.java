package crg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crg.model.VehStatus;
import crg.model.Vehicle;
import crg.repo.VehStatusRepo;
import crg.repo.VehicleRepo;

@Controller
public class VehicleController {

	@Autowired
	private VehicleRepo vehRepo;
	
	@Autowired
	private VehStatusRepo vehSttsRepo;
	
	@RequestMapping("/vehicle")
	public String ShowVehList(Model model) {
		List<Vehicle> listVeh = vehRepo.findAll(); 
		model.addAttribute("listVeh", listVeh);
		return "vehicle";
	}
	
	@GetMapping("/newVehicleReg")
	public String newVehicleReg(Model model) {
		List<VehStatus> vehStatus  = vehSttsRepo.findAll();
		model.addAttribute("vehStatus", vehStatus);
		model.addAttribute("vehicle" , new Vehicle());
		return "newVehicleReg";
	}
	
	@PostMapping("/vehicle/save")
	public String  saveVehicle(Vehicle vehicle) {
		vehRepo.save(vehicle);
		return "redirect:/vehicle";
	}
		
	@GetMapping("/UpdateVehicle/{vehId}")
	public String ShowEditForm(@PathVariable("vehId") Long vehId, Model model) {
		Vehicle vehicle =   vehRepo.findById(vehId).get();
		model.addAttribute("Vehicle", vehicle);
		List<VehStatus> vehStatus =  vehSttsRepo.findAll();
		model.addAttribute("vehStatus",vehStatus);
		return "UpdateVehicle";
	}
	
	@GetMapping("/deleteRecord/{vehId}")
	public String deleteVehicle(@PathVariable(value = "vehId") long vehId,
			Model model){
		vehRepo.deleteById(vehId);
		return "redirect:/vehicle";
		
	}

}
