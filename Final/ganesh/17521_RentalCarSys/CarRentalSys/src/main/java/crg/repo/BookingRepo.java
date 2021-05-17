package crg.repo;

import org.springframework.data.jpa.repository.Query;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



import crg.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long>{
	@Query(value="select bookings.* from bookings  \r\n"
			+ "where bookings.bookid = :bookId \r\n"
			, nativeQuery = true)
	public Booking getBookingDetails(@Param("bookId") Long bookId);

	

}
	

