package com.wpfinki.airlinesproject.service.serviceImpl;

import com.wpfinki.airlinesproject.model.User;
import com.wpfinki.airlinesproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional= userRepository.findByEmail(username);

        if(!userOptional.isPresent()){
            throw new UsernameNotFoundException("User not found for email" + username);
        }
        User user=userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.getRoles());

    }

}
