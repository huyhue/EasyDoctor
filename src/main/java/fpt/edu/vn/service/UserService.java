package fpt.edu.vn.service;

import java.util.Collection;
import java.util.Optional;

import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.User;

public interface UserService {
	User findByEmail(String email);
	Optional<User> findByUserName(String username);
	User findByConfirmationToken(String token);
	
	User getUserById(int userId);
	void savePasswordByUser(User user);
	void saveRegister(User userRE);
	
	Collection<Role> getRolesForDoctor();
	Collection<Role> getRolesForPatient();
}
