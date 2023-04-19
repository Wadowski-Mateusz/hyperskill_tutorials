package application.dto;

import java.util.Objects;
import java.util.UUID;

public class EmployeeDTO {

    private UUID id;
    private String login;
    private String password;

    public EmployeeDTO(UUID id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public EmployeeDTO() {
    }

    public UUID getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
