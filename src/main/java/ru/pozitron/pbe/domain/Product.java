package ru.pozitron.pbe.domain;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer discount;
    private Double count;
    private String unit;
    private String fotoname;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public Product() {
    }



    public Product(String name, String description, Double price, Double count, String unit, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.unit = unit;
        this.category = category;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer sale) {
        this.discount = sale;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFotoname() {
        return fotoname;
    }

    public void setFotoname(String fotoname) {
        this.fotoname = fotoname;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
