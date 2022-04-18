package fpt.edu.vn.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.RoleRepository;
import fpt.edu.vn.repository.UserRepository;
import fpt.edu.vn.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User findByConfirmationToken(String token) {
		return userRepository.findByConfirmationToken(token);
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}
	
	@Override
	@PreAuthorize("#userId == principal.id")
	public User getUserById(int userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
    
    @Override
    public void savePasswordByUser(User user) {
    	User userRE = userRepository.findByUserName(user.getUserName())
    			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	userRE.setConfirmationToken(UUID.randomUUID().toString());
    	userRE.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRE.setEnabled(true);
        userRepository.save(userRE);
    }
    
    @Override
    public void saveRegister(User userRE) {
    	User user = new User( userRE.getUserName(), userRE.getEmail(), userRE.getConfirmationToken(), getRolesForPatient());
    	userRepository.save(user);
    }
    
    @Override
    public Collection<Role> getRolesForDoctor() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_DOCTOR"));
        return roles;
    }
    
    @Override
    public Collection<Role> getRolesForPatient() {
    	HashSet<Role> roles = new HashSet();
    	roles.add(roleRepository.findByName("ROLE_PATIENT"));
    	return roles;
    }

}
