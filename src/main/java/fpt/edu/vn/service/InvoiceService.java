package fpt.edu.vn.service;

import java.io.File;
import java.util.List;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.security.CustomUserDetails;

public interface InvoiceService {

    Invoice getInvoiceByAppointmentId(int appointmentId);

    Invoice getInvoiceById(int invoiceId);

    List<Invoice> getAllInvoices();

    CommonMsg changeInvoiceStatusToPaid(int invoiceId);

    void issueInvoicesForConfirmedAppointments();

    String generateInvoiceNumber();

    File generatePdfForInvoice(int invoiceId);

    boolean isUserAllowedToDownloadInvoice(CustomUserDetails user, Invoice invoice);

	void createNewInvoice(Appointment appointment);

	CommonMsg createNewInvoiceByAdmin(int appointmentId, String status);
}

