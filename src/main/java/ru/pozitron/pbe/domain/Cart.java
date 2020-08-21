package ru.pozitron.pbe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;
    private CartStatus status;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderProduct> orderProducts;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String number;
    private String city;
    private String street;
    private String houseNumber;
    private String comment;

    public Cart() {
    }
    public Cart(LocalDateTime date) {
        this.date = date;
        this.orderProducts = new HashSet<>();
    }
    public Cart(String name,String Number,String city,String street,String houseNumber,String comment){

    }

    public BigDecimal getCostAllProducts(){

        return orderProducts
                .stream()
                .map(orderProduct ->
                        (orderProduct.getProduct().getPriceWithDiscount() != null ?
                                orderProduct.getProduct().getPriceWithDiscount().multiply(BigDecimal.valueOf(orderProduct.getQuantity())):
                                orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity()))))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> products) {
        this.orderProducts = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
