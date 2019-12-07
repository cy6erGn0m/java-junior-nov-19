package ru.levelp.junior.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 32, unique = true, nullable = false)
    @Size(min = 3)
    private String login;

    @Column(length = 64, nullable = false)
    @Size(min = 3)
    @JsonIgnore
    private String encryptedPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "origin")
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.encryptedPassword = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
