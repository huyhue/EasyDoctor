package fpt.edu.vn;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.UserRepository;
import fpt.edu.vn.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class EasyDoctorApplicationTests {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetCustomer() throws Exception {
		// Tạo mock data
		// User returned = new User("huyhue", null, null, null, null, null, null, false,
		// null, null);
		//
		// //stub the data Định nghĩa hành vi
//		 Mockito.when(userRepository.getOne(3)).thenReturn(returned);

		// Gọi method
		User result = userService.findById(3);

		// Kiểm tra kết quả
		Assert.assertEquals("huyhue", result.getUserName());
	}
}
