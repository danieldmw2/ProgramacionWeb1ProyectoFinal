package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */

@Entity
public class Usuario implements Serializable
{
    @Id private String username;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private Boolean administrator;

    public Usuario()
    {

    }

    public Usuario(String username, String email, String password, Boolean administrator)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.administrator = administrator;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean isAdministrator()
    {
        return administrator;
    }

    public void setAdministrator(boolean administrator)
    {
        this.administrator = administrator;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getAdministrator()
    {
        return administrator;
    }

    public void setAdministrator(Boolean administrator)
    {
        this.administrator = administrator;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s - %s", username, email, password, administrator);
    }
}
