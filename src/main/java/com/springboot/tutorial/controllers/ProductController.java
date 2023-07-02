package com.springboot.tutorial.controllers;

import com.springboot.tutorial.models.Product;
import com.springboot.tutorial.models.ResponseObject;
import com.springboot.tutorial.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController
{
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
//    This request will be handled by the method getAllProducts()
    List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        return product.isPresent()?
            ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Product found",product))
        :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Cannot find product with id = "+id, ""));
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct)
    {
        // 2 products must not have the same name
        Optional<Product> foundProduct=productRepository.findByProductName(newProduct.getProductName().trim());
        if(foundProduct.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("failed","Product with name = "+newProduct.getProductName()+" already exists", ""));
        }
        else {
            productRepository.save(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("success", "Product with name = " + newProduct.getProductName() + " has been created", newProduct));
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id)
    {
        Product updateProduct = productRepository.findById(id)
                .map(product -> {
                product.setProductName(newProduct.getProductName());
                product.setYear(newProduct.getYear());
                product.setPrice(newProduct.getPrice());
                product.setUrl(newProduct.getUrl());
                return productRepository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Product with id = "+id+" has been updated",updateProduct));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
        {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Product with id = "+id+" has been deleted", ""));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Cannot find product with id = "+id, ""));
        }
    }

}
