package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.BrandService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Brand;
import org.klozevitz.phat_store_mvc_java_311.repositories.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImplementation implements BrandService {
    private final BrandRepository repo;

    @Override
    public List<Brand> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Brand> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    @Override
    public void deleteById(int id) {

    }
}
