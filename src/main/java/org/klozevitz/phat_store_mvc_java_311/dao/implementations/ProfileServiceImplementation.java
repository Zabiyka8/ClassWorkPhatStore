package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ProfileService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Profile;
import org.klozevitz.phat_store_mvc_java_311.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImplementation implements ProfileService {
    private final ProfileRepository repo;

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public Optional<Profile> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Profile save(Profile profile) {
        return repo.save(profile);
    }

    @Override
    public void deleteById(int id) {

    }
}
