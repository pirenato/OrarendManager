/**
 * Egyszeru POJO osztaly a Users entitasokhoz
 */

package usermanagement;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "felhasznalok")
public class Users {

    private String fullName;
    private String email;
    private String username;
    private String password;
    private String role;

    public Users(){

    }

    @Id
    @Column(name = "username")
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

    @Column(name = "teljes_nev")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "szerepkor")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
