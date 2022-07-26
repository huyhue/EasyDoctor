package fpt.edu.vn.unit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.UserRepository;
import fpt.edu.vn.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserUnitTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Test
	public void testGetUserById() throws Exception {
		// Tạo mock data
		User returned = new User("huyhue123", null, null, null, null, null, null, false, null, null);
		String use = "Str";
		System.out.print("tesst returned:" + returned.toString());
		// stub the data Định nghĩa hành vi
		Mockito.when(userRepository.getOne(3)).thenReturn(returned);

		// Gọi method
		User result = userServiceImpl.findById(3);
		System.out.print("tesst:" + result);

		// Kiểm tra kết quả
		Assert.assertEquals("huyhue123", result.getUserName());
	}

	@Test
	public void testGetAllDoctorsByPatient() throws Exception {

		// Gọi method
		List<Doctor> list = userServiceImpl.getAllDoctorsByPatient();

		// Kiểm tra kết quả
		Assert.assertEquals(5, list.size());
	}

}
