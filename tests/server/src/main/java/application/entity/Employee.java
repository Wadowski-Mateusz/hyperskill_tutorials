package application.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private UUID id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public Employee() {
    }

    public Employee(UUID id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
