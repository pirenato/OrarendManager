/**
 * Egyszeru POJO osztaly a Users entitasokhoz
 */

package usermanagement;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Users {

    private String full_name;
    private String email;
    private String username;
    private String password;
    private String role;

    public Users(){

    }

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
