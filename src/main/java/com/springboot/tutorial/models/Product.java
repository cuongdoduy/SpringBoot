package com.springboot.tutorial.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tblProduct")
public class Product {
//    This is primary key
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    You can also use sequence
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    @Column(length = 300, nullable = false, unique = true)
    private String productName;
    private int productYear;
    private Double price;
    private String url;
    public Product() {
    }
    public Product(String productName, int productYear, Double price, String url) {
        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.url = url;
    }
    public Long getId() {
        return id;
    }
    public String getProductName() {
        return productName;
    }
    public int getYear() {
        return productYear;
    }
    public Double getPrice() {
        return price;
    }
    public String getUrl() {
        return url;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setYear(int year) {
        this.productYear = year;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName='" + productName + '\'' + ", year=" + productYear + ", price=" + price + ", url='" + url + '\'' + '}';
    }

}
