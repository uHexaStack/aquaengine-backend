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

    /**
     * Constructs a new {@code UserDetailsServiceImpl} with the specified user repository.
     *
     * @param userRepository the repository used to access user data
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username.
     * <p>
     * Searches for a user by username in the database. If found, returns a {@link UserDetailsImpl}
     * instance representing the user. If not found, throws a {@link UsernameNotFoundException}.
     * </p>
     *
     * @param username the username identifying the user whose data is required
     * @return a fully populated {@link UserDetails} instance (never {@code null})
     * @throws UsernameNotFoundException if the user could not be found or has no granted authorities
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}