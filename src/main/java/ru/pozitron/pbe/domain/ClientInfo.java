package ru.pozitron.pbe.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Поле имя обязательно для заполнения")
    @Length(min = 3,max = 20,message = "Длина имени должна быть не менее 2 символов и не более 20")
    private String name;
    @NotBlank(message = "Поле номер телефона обязательно для заполнения")
    @Pattern(regexp = "^$|^(\\+)?[0-9]{11}",message = "поле номер телефона должно содержать 11 цифр")
    private String number;
    @NotBlank(message = "Поле город обязательно для заполнения")
    private String city;
    @NotBlank(message = "Поле улица обязательно для заполнения")
    private String street;
    @NotBlank(message = "Поле дом обязательно для заполнения")
    private String houseNumber;
    @NotBlank(message = "Поле квартира обязательно для заполнения")
    private String ApartmentNumber;
    private String comment;

    public ClientInfo() {
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

    public String getApartmentNumber() {
        return ApartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        ApartmentNumber = apartmentNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
