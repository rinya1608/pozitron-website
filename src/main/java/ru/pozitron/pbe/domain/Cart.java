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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    ClientInfo clientInfo;


    public Cart() {
    }

    public Cart(LocalDateTime date) {
        this.date = date;
        this.orderProducts = new HashSet<>();
        this.setStatus(CartStatus.IN_PROCESS);
    }

    @Transient
    public BigDecimal getCostAllProducts() {

        return orderProducts
                .stream()
                .map(orderProduct ->
                        (orderProduct.getProduct().getPriceWithDiscount() != null ?
                                orderProduct.getProduct().getPriceWithDiscount().multiply(BigDecimal.valueOf(orderProduct.getQuantity())) :
                                orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity()))))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    @PreRemove
    void removeFromUser() {
        System.out.println("deletefromuser");
        user.getCarts().remove(this);
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

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}

