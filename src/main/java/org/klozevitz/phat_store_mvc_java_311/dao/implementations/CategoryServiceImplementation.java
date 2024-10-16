package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.CategoryService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Category;
import org.klozevitz.phat_store_mvc_java_311.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Category save(Category category) {
        return repo.save(category);
    }

    @Override
    public void deleteById(int id) {

    }
}
