package fpt.edu.vn.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.InvoiceService;
import fpt.edu.vn.service.ScheduledTasksService;

@Component
public class ScheduledTasksServiceImpl implements ScheduledTasksService {

	private final AppointmentService appointmentService;
	private final InvoiceService invoiceService;

	public ScheduledTasksServiceImpl(AppointmentService appointmentService, InvoiceService invoiceService) {
		this.appointmentService = appointmentService;
		this.invoiceService = invoiceService;
	}

	// Chay sau moi 30 phut
	@Scheduled(fixedDelay = 30 * 60 * 1000)
	@Override
	public void updateAllAppointmentsStatuses() {
		appointmentService.updateAllAppointmentsStatuses();
	}

	// Chay sau 10 giay
//    @Scheduled(cron = "*/10 * * * * *")
	// Chay vao ngay dau tien moi thang
	@Scheduled(cron = "0 0 0 1 * ?")
	@Override
	public void issueInvoicesForCurrentMonth() {
		invoiceService.issueInvoicesForConfirmedAppointments();
	}

}
