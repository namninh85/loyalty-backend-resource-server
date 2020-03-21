package com.nin.xloyalty.service;

import com.nin.xloyalty.model.Category;
import com.nin.xloyalty.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findByIsActive(true);
    }


}
