package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.AppointmentStatus;
import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.repository.InvoiceRepository;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.InvoiceService;
import fpt.edu.vn.service.NotificationService;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.util.PdfGeneratorUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, PdfGeneratorUtil pdfGeneratorUtil, UserService userService, AppointmentService appointmentService, NotificationService notificationService) {
        this.invoiceRepository = invoiceRepository;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
    }

    @Override
    public String generateInvoiceNumber() {
        List<Invoice> invoices = invoiceRepository.findAllIssuedInCurrentMonth(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
        int nextInvoiceNumber = invoices.size() + 1;
        LocalDateTime today = LocalDateTime.now();
        return "HD: "+ today.getYear() + "/" + today.getMonthValue() + "-ID: " + nextInvoiceNumber;
    }

    @Override
    public void createNewInvoice(Appointment appointment) {
    	appointment.setStatus(AppointmentStatus.INVOICED);
    	appointmentService.updateAppointment(appointment);
    	List<Appointment> appointments = new ArrayList<>();
    	appointments.add(appointment);
    	
    	Invoice invoice = new Invoice(generateInvoiceNumber(), "paid", LocalDateTime.now(), appointments);
        invoiceRepository.save(invoice);
        notificationService.newInvoice(invoice, true);
    }
    
    @Override
    public CommonMsg createNewInvoiceByAdmin(int appointmentId, String status) {
    	Appointment appointment = appointmentService.getAppointmentById(appointmentId);
    	Invoice invoice = null;
    	CommonMsg commonMsg = new CommonMsg();
    	
    	appointment.setStatus(AppointmentStatus.INVOICED);
    	appointmentService.updateAppointment(appointment);
    	List<Appointment> appointments = new ArrayList<>();
    	appointments.add(appointment);
    	
    	if (status.equals("paid")) {
    		invoice = new Invoice(generateInvoiceNumber(), "paid", LocalDateTime.now(), appointments);
		}else{
			invoice = new Invoice(generateInvoiceNumber(), "issued", LocalDateTime.now(), appointments);
		}
    	invoiceRepository.save(invoice);
    	notificationService.newInvoice(invoice, true);
    	commonMsg.setMsgCode("200");
    	return commonMsg;
    }

    @Override
    public Invoice getInvoiceByAppointmentId(int appointmentId) {
        return invoiceRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public File generatePdfForInvoice(int invoiceId) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        if (!isUserAllowedToDownloadInvoice(currentUser, invoice)) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        return pdfGeneratorUtil.generatePdfFromInvoice(invoice);
    }

    @Override
    public boolean isUserAllowedToDownloadInvoice(CustomUserDetails user, Invoice invoice) {
        int userId = user.getId();
        if (user.hasRole("ROLE_ADMIN")) {
            return true;
        }
        for (Appointment a : invoice.getAppointments()) {
            if (a.getDoctor().getId() == userId || a.getPatient().getId() == userId) {
                return true;
            }
        }
        return false;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CommonMsg changeInvoiceStatusToPaid(int invoiceId) {
    	CommonMsg commonMsg = new CommonMsg();
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        invoice.setStatus("paid");
        invoice.setIssued(LocalDateTime.now());
        invoiceRepository.save(invoice);
        //chua co gui email
        commonMsg.setMsgCode("200");
		return commonMsg;
    }

    @Transactional
    @Override
    public void issueInvoicesForConfirmedAppointments() {
        List<Patient> listPatient = userService.getAllPatients();
        for (Patient patient : listPatient) {
            List<Appointment> appointmentsToIssueInvoice = appointmentService.getConfirmedAppointmentsByPatientId(patient.getId());
            if (!appointmentsToIssueInvoice.isEmpty()) {
                for (Appointment a : appointmentsToIssueInvoice) {
                    a.setStatus(AppointmentStatus.INVOICED);
                    appointmentService.updateAppointment(a);
                }
                Invoice invoice = new Invoice(generateInvoiceNumber(), "issued", LocalDateTime.now(), appointmentsToIssueInvoice);
                invoiceRepository.save(invoice);
                notificationService.newInvoice(invoice, true);
            }

        }
    }
}
