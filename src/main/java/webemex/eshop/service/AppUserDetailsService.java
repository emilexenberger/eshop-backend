package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webemex.eshop.repository.UsersRepository;

/**
 * Service class that implements the {@link UserDetailsService} interface to handle user authentication
 * and retrieval of user details by username. This class is used by Spring Security for user authentication.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Loads the user details by username.
     *
     * @param username the username to look up in the repository.
     * @return the {@link UserDetails} of the user with the specified username.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
