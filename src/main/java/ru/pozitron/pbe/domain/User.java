package ru.pozitron.pbe.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Поле имя обязательно для заполнения")
    @Length(min = 3,max = 20,message = "Длина имени должна быть не менее 2 символов и не более 20")
    private String name;
    @Length(max = 20,message = "Длина фамилии должна быть не более 20 символов")
    private String surname;
    @NotBlank(message = "Поле логин обязательно для заполнения")
    @Length(min = 3,max = 20,message = "Длина логина должна быть не менее 2 символов и не более 20")
    private String username;
    @Email(message = "Не верный формат почты")
    @NotBlank(message = "Поле адрес эл. почты обязательно для заполнения")
    private String email;
    @NotBlank(message = "Поле пароль обязательно для заполнения")
    private String password;
    @Pattern(regexp = "^$|^(\\+)?[0-9]{11}",message = "поле номер телефона должно содержать 11 цифр")
    private String number;



    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Cart> carts;

    public User() {
    }

    @Transient
    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }
    @Transient
    public Cart getCartInProcess(){
        return getCarts()
                .stream()
                .filter(cart -> cart.getStatus().equals(CartStatus.IN_PROCESS))
                .findFirst()
                .orElse(null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
