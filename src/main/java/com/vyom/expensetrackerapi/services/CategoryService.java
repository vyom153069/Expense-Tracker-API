package com.vyom.expensetrackerapi.services;

import java.util.List;

import com.vyom.expensetrackerapi.domain.Category;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface CategoryService {

    List<Category> fetchAllCategories(Integer userId);

    Category fatchCategoryById(Integer userId,Integer categoryId) throws EtResourceNotFoundException;

    Category addCategory(Integer userId,String title,String description) throws EtBadRequestException;

    void updateCategory(Integer userId,Integer categoryId,Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransections(Integer userId,Integer categoryId) throws EtResourceNotFoundException;
    
    
}
