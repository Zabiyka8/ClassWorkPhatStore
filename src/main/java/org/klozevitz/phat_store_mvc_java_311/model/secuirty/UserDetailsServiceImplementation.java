package org.klozevitz.phat_store_mvc_java_311.model.secuirty;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.repositories.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {
    private final ApplicationUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> loadedUser = repo.findApplicationUserByProfileEmail(email);
        if (loadedUser.isPresent()) {
            return loadedUser.get().securityUserFromEntity();
        } else {
            throw new UsernameNotFoundException("Данный email не зарегистрирован: " + email);
        }
    }
}
