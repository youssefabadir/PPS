package com.pps.controller.domain.category;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> getAllCategories();

    CategoryEntity getCategoryByName(String name);
}
