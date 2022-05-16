package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Invoice;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i from Invoice i where i.issued >= :beginingOfCurrentMonth")
    List<Invoice> findAllIssuedInCurrentMonth(@Param("beginingOfCurrentMonth") LocalDateTime beginingOfCurrentMonth);

    @Query("select i from Invoice i inner join i.appointments a where a.id in :appointmentId")
    Invoice findByAppointmentId(@Param("appointmentId") int appointmentId);
}
