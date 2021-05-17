package crg.controller;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import crg.model.Booking;
import crg.repo.CustomerRepo;
import crg.repo.EmployeeRepo;
import crg.repo.VehicleRepo;

  
public class BookingPDFExporter {
    private Booking booking;
    
    @Autowired
    private CustomerRepo custRepo;
    @Autowired
    private EmployeeRepo empRepo;
    @Autowired
    private VehicleRepo vehRepo;
     
        
//    public List<Booking> getListBook() {
//		return listBook;
//	}
	
public BookingPDFExporter(Booking booking) {
       this.booking = booking;
       System.out.println("Booking " + booking.getBookId());
    } 
   
	private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Book ID", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getBookId()));
        
        cell.setPhrase(new Phrase("Customer Details", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getCustomer().getCustName()));
        
        cell.setPhrase(new Phrase("Vehicle Details", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getVehicle().getVehRegNo()) + " ; " + String.valueOf(booking.getVehicle().getVehBrand()));
                
        cell.setPhrase(new Phrase("Start Date", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getStartDate()));
                
        cell.setPhrase(new Phrase("End Date", font));
        table.addCell(cell);   
        table.addCell(String.valueOf(booking.getEndDate()));
                
        cell.setPhrase(new Phrase("Total Cost", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getTotalCost()));
        
        cell.setPhrase(new Phrase("Booked By (Employee)", font));
        table.addCell(cell);
        table.addCell(String.valueOf(booking.getEmployee().getEmpName()));
    }

	private void writeTableData(PdfPTable table) {

		table.addCell(String.valueOf(booking.getCustomer()));
	}

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Car Booking Invoice", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {8f, 8f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
    
    /** return boolean false if end date is before start date 
	 * @param
	 * @return boolean	 **/
	public static boolean checkValidDates(LocalDate start, LocalDate end) {
		if (end.isBefore(start)) {return false;}
		
		return true;
	}
	
	public static boolean chkCrashdDates(LocalDate crash, LocalDate start, LocalDate end) {
		if (crash.isAfter(start.minusDays(1)) && crash.isBefore(end.plusDays(1))) {return true;}
		return false;
	}

}