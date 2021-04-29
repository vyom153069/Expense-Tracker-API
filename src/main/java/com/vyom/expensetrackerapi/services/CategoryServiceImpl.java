package com.vyom.expensetrackerapi.services;

import java.util.List;

import com.vyom.expensetrackerapi.domain.Category;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.vyom.expensetrackerapi.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        // TODO Auto-generated method stub
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fatchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws EtBadRequestException {
        // TODO Auto-generated method stub
        int categoryId=categoryRepository.create(userId, title, description);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        // TODO Auto-generated method stub

        categoryRepository.update(userId, categoryId, category);
        
    }

    @Override
    public void removeCategoryWithAllTransections(Integer userId, Integer categoryId)
            throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        this.fatchCategoryById(userId, categoryId);
        categoryRepository.removeById(userId, categoryId);
    }
    
}
