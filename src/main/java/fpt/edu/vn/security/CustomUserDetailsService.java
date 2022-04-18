package fpt.edu.vn.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fpt.edu.vn.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String userName) {
        return userRepository.findByUserName(userName)
                .map(CustomUserDetails::create)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password!"));
    }
}
