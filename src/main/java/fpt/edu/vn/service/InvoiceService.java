package fpt.edu.vn.service;

import java.io.File;
import java.util.List;

import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.security.CustomUserDetails;

public interface InvoiceService {
    void createNewInvoice(Invoice invoice);

    Invoice getInvoiceByAppointmentId(int appointmentId);

    Invoice getInvoiceById(int invoiceId);

    List<Invoice> getAllInvoices();

    void changeInvoiceStatusToPaid(int invoiceId);

    void issueInvoicesForConfirmedAppointments();

    String generateInvoiceNumber();

    File generatePdfForInvoice(int invoiceId);

    boolean isUserAllowedToDownloadInvoice(CustomUserDetails user, Invoice invoice);
}

