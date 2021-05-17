package crg.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.awt.Dialog;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import crg.model.Booking;
import crg.model.Customer;
import crg.model.Employee;
import crg.model.Vehicle;
import crg.repo.BookingRepo;
import crg.repo.CustomerRepo;
import crg.repo.EmployeeRepo;
import crg.repo.VehStatusRepo;
import crg.repo.VehicleRepo;

@Controller
public class BookingController {
	@Autowired
	private BookingRepo bookRepo;
	@Autowired
	private CustomerRepo custRepo;
	@Autowired
	private EmployeeRepo empRepo;
	@Autowired
	private VehicleRepo vehRepo;
	@Autowired
	private VehStatusRepo vehSttsRepo;

	@GetMapping("/booking")
	public String ShowBookingList(Model model) {
		List<Booking> listBook = bookRepo.findAll();
		model.addAttribute("listBook", listBook);
		return "booking";
	}

	@GetMapping("/newBookingReg")
	public String newBookingReg(Model model) {
		List<Customer> listCust = custRepo.findAll();
		List<Vehicle> listVeh = vehRepo.findAll();
		List<Employee> listEmp = empRepo.findAll();
		model.addAttribute("booking", new Booking());
		model.addAttribute("listCust", listCust);
		model.addAttribute("listVeh", listVeh);
		model.addAttribute("listEmp", listEmp);
		return "newBookingReg";
	}

	@PostMapping("/booking/save")
	public String saveBooking(Booking booking, Model model) {
		Boolean CheckValid = BookingPDFExporter.checkValidDates(booking.getStartDate(), booking.getEndDate());

		if (!CheckValid) {
			//JOptionPane.showMessageDialog(null, "End Date should be after Start Date", "Problem!", JOptionPane.INFORMATION_MESSAGE);
			//model.addAttribute("optMsg", "Please select the correct End Date to Book");
			System.out.println("End Date should be after " + booking.getStartDate());
		//log.info("======> " + CheckValid + " is wrong");
			return "newBookingReg";
		}
		else {
		bookRepo.save(booking);
		
	}
		return "redirect:/booking";
	}

@GetMapping("/booking/edit/{bookId}")
public String ShowBookingEditForm(@PathVariable("bookId") Long bookId, Model model) {
	List<Customer> listCust = custRepo.findAll();
	List<Vehicle> listVeh = vehRepo.findAll();
	List<Employee> listEmp = empRepo.findAll();
	model.addAttribute("listCust", listCust);
	model.addAttribute("listVeh", listVeh);
	model.addAttribute("listEmp", listEmp);
	Booking booking = bookRepo.findById(bookId).get();
	model.addAttribute("booking", booking);
	List<Booking> listBook = bookRepo.findAll();
	model.addAttribute("listBook", listBook);
	return "newBookingReg";
}

	@GetMapping("/ReturnBooking/{bookId}")
	public String CarReturn(@PathVariable(value = "bookId") long bookId, Model model) {

		// Get hire from the Service
		Booking listBooking = new Booking();

		if (bookId == 0) {
			// this method is not expected to be invoked with hireId=0
		} else {
			if (bookId > 0) { // bring details of existing hire into input area for hire return
				listBooking = bookRepo.getBookingDetails(bookId);
				if (listBooking.getActualEndDate() != null)// .getEmpReturn()!=null) {
				{
					model.addAttribute("optMsg", "Booking (" + bookId + ") is already returned.");					
				} else {				
					model.addAttribute("optMsg1",
							"Enter the actual return date time, click <Save> to confirm return of vehicle.");
				}
				
			}
		}
		model.addAttribute("listBooking", listBooking);
		return "ReturnBooking";
	}

//**
		@PostMapping("/booking/saveReturn/")
		public String saveReturn(Booking listBooking, HttpServletRequest request, Model model) {
			System.out.println("in save return");

			Booking booking = bookRepo.getOne(listBooking.getBookId());
			long noofdays = ChronoUnit.DAYS.between(listBooking.getStartDate(), listBooking.getActualEndDate());
			System.out.println(noofdays);
			System.out.println(listBooking + " check the vehicle with respect to BookID");
			double totalCost = listBooking.getBookingFee() + (noofdays * (listBooking.getVehicle().getDailyRate()));
			listBooking.setTotalCost(totalCost);
			booking.setTotalCost(totalCost);
			booking.setActualEndDate(listBooking.getActualEndDate());
			bookRepo.save(booking);
			System.out.println(totalCost + " is added & saved ");
			model.addAttribute("optMsg", "Vehicle Returned successfully..Thank You...");
			return "redirect:/booking/";
		}		

	@GetMapping("/booking/delete/{bookId}")
	public String ShowBookingDeleteForm(@PathVariable("bookId") Long bookId, Model model) {
		bookRepo.deleteById(bookId);
		return "redirect:/booking";
	}

	@GetMapping("/InvoicePdf/{bookId}")
	public void hirePdf(@PathVariable(value = "bookId") Long bookId, HttpServletResponse response)
			throws DocumentException, IOException {

		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Booking_" + bookId + ".pdf";
		response.setHeader(headerKey, headerValue);
		Booking booking = bookRepo.getBookingDetails(bookId);
		System.out.println("Check Bookid ");
		BookingPDFExporter exporter = new BookingPDFExporter(booking);
		exporter.export(response);
	}
}