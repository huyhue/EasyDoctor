package fpt.edu.vn.unit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;


import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.repository.AppointmentRepository;
import fpt.edu.vn.service.impl.AppointmentServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppointmentUnitTest {
	@InjectMocks
	AppointmentServiceImpl appointmentServiceImpl;

	@Mock
	private AppointmentRepository appointmentRepository;

	@Test
	public void testGetAppointmentById() throws Exception {
		// Tạo mock data
		Appointment returned = new Appointment(null, null, null, null, null);
		//stub the data Định nghĩa hành vi
		Mockito.when(appointmentRepository.getOne(3)).thenReturn(returned);

		// Gọi method
		Appointment result = appointmentServiceImpl.getAppointmentById(3);
		System.out.print("tesst:" + result);

		// Kiểm tra kết quả
		Assert.assertEquals("huyhue123", result.getPackages().getName());
	}
	
}
