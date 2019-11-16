package ru.levelp.junior.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 32, unique = true, nullable = false)
    @Size(min = 4)
    private String login;

    @Column(length = 32, nullable = false)
    @Size(min = 4)
    private String password;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
