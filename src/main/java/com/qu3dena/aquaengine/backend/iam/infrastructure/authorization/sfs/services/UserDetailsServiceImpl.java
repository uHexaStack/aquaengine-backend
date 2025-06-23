package com.qu3dena.aquaengine.backend.iam.infrastructure.authorization.sfs.services;

import com.qu3dena.aquaengine.backend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService} for loading user-specific data.
 * <p>
 * Retrieves user information from the database using {@link UserRepository}
 * and adapts it to Spring Security's {@link UserDetails} contract.
 * Used by Spring Security for authentication and authorization processes.
 * </p>
 */
@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String source) throws UsernameNotFoundException {
        if (source.matches("\\d+")) {
            long id = Long.parseLong(source);
            return userRepository.findById(id)
                    .map(UserDetailsImpl::build)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with id: " + id));
        }

        return userRepository.findByUsername(source)
                .map(UserDetailsImpl::build)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + source));
    }
}