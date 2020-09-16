package ru.pozitron.pbe.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String value;
    LocalDateTime date;
    String newEmail;

    @NotNull
    CodeType codeType;

    @ManyToOne
    User user;

    public Code() {
    }

    public Code(User user, CodeType codeType) {
        this.user = user;
        this.codeType = codeType;
        this.value = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
