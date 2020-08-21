package ru.pozitron.pbe.domain;

public class OrderProductCriteria {
    private long id;
    private int quantity;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
