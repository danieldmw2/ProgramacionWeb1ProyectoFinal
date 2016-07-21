package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */

@Entity
public class Etiqueta implements Serializable
{
    @Id @GeneratedValue private Long id;
    private String etiqueta;
    @Column(nullable = false) private Long imageID;

    public Etiqueta()
    {

    }

    public Etiqueta(String etiqueta, Long imageID)
    {
        this.etiqueta = etiqueta;
        this.imageID = imageID;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getEtiqueta()
    {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }
}

