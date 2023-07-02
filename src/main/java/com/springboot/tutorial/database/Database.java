package com.springboot.tutorial.database;

import com.springboot.tutorial.models.Product;
import com.springboot.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
       return new CommandLineRunner() {
           @Override
           public void run(String... args) throws Exception {
//               Product productA = new Product("iPhone 12", 2020, 799.99, "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-12-pro-family-hero?wid=940&hei=1112&fmt=png-alpha&.v=1604021660000");
//               Product productB = new Product("iPhone 12 Pro", 2020, 999.99, "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-12-pro-family-hero?wid=940&hei=1112&fmt=png-alpha&.v=1604021660000");
//               Product productC = new Product("iPhone 12 Pro Max", 2020, 1099.99, "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-12-pro-family-hero?wid=940&hei=1112&fmt=png-alpha&.v=1604021660000");
//               logger.info("Inserting data into database" + productRepository.save(productA));
//               logger.info("Inserting data into database" + productRepository.save(productB));
//               logger.info("Inserting data into database" + productRepository.save(productC));
           }
       };
    }
}
