package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MeasurementRepository measurementRepository;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOne(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new Product();
        }
        return optionalProduct.get();
    }

    public Result add(ProductDto productDto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId) {
            return new Result("Such a product whit category id does not exist", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new Result("Such a category doesn't exist", false);
        }


        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (optionalAttachment.isEmpty()) {
            return new Result("Such a photo doesn't exist", false);

        }


        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (optionalMeasurement.isEmpty()) {
            return new Result("Such a unit of  measurement doesn't exist", false);

        }

        Product product = new Product();
        product.setPhoto(optionalAttachment.get());
        product.setName(productDto.getName());
        List<Product> productList = getAll();
        product.setCode(generateCode(productList.size())); //todo generate code automatically
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());

        productRepository.save(product);
        return new Result("Product  saved", true);
    }

    public Result edit(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new Result("Product not found", false);
        }

        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId) {
            return new Result("Such a product whit category id does not exist", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new Result("Such a category doesn't exist", false);
        }


        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (optionalAttachment.isEmpty()) {
            return new Result("Such a photo doesn't exist", false);

        }

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (optionalMeasurement.isEmpty()) {
            return new Result("Such a unit of  measurement doesn't exist", false);

        }

        Product product = optionalProduct.get();
        product.setPhoto(optionalAttachment.get());
        product.setName(productDto.getName());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());

        productRepository.save(product);
        return new Result("Product edited", true);
    }

    public Result delete(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new Result("Product not found", false);
        }
        productRepository.deleteById(id);
        return new Result("Product deleted", true);
    }

    private String generateCode(Integer n) {
        return String.valueOf((n + 1));
    }

}
