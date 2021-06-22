package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result add(CategoryDto categoryDto) {
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName) {
            return new Result("A category with such a name already  exist", false);
        }

        Category category = new Category();
        category.setName(categoryDto.getName());

        if(categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalCategory.isEmpty()){
                return new Result("Such a parent category does not exist",false);
            }
            category.setParentCategory(optionalCategory.get());
        }
        categoryRepository.save(category);
        return new Result("Successfully saved", true);
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category getOne(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new Category();
        }
        return optionalCategory.get();
    }

    public Result edit(Integer id,CategoryDto categoryDto){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new Result("Category not found",false);
        }
        Category category = optionalCategory.get();
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName) {
            return new Result("This category already exists",false);
        }

        if (categoryDto.getParentCategoryId()!=null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalParentCategory.isEmpty()) {
                return new Result("This parent category not found",false);
            }
            category.setParentCategory(optionalParentCategory.get());
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new Result("Category successfully edited",true);
    }

    public Result delete(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new Result("Category not found",false);
        }
        categoryRepository.deleteById(id);
        return new Result("Category deleted",true);
    }
}
