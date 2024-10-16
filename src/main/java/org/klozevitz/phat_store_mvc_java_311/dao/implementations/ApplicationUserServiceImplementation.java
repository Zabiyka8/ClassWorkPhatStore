package org.klozevitz.phat_store_mvc_java_311.dao.implementations;


import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.klozevitz.phat_store_mvc_java_311.repositories.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImplementation implements ApplicationUserService {
    private final ApplicationUserRepository repo;


    @Override
    public List<ApplicationUser> findAll() {
        return null;
    }

    @Override
    public Optional<ApplicationUser> findById(int id) {
        return Optional.empty();
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> loadedUser = repo.findApplicationUserByProfileEmail(email);
        if (loadedUser.isPresent()) {
            return loadedUser.get();
        } else {
            throw new UsernameNotFoundException("Данный email не зарегистрирован: " + email);
        }
    }
}
