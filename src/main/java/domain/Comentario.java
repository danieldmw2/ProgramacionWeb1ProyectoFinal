package domain;

import services.ComentarioServices;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
@Entity
public class Comentario implements Serializable
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false, columnDefinition = "VARCHAR(1000)") private String comentario;
    @OneToOne private Usuario autor;
    @OneToOne private Image image;
    @Column(nullable = false) private Date date;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) private List<Usuario> interaction;
    @Column(nullable = false) private Integer likes;
    @Column(nullable = false) private Integer dislikes;

    public Comentario()
    {
        this.date = new Date();
        this.interaction = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }

    public Comentario(String comentario, Usuario autor, Image image)
    {
        this.comentario = comentario;
        this.autor = autor;
        this.image = image;
        this.date = new Date();
        this.interaction = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getComentario()
    {
        return comentario;
    }

    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }

    public Usuario getAutor()
    {
        return autor;
    }

    public void setAutor(Usuario autor)
    {
        this.autor = autor;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<Usuario> getInteraction()
    {
        return interaction;
    }

    public void setInteraction(List<Usuario> interaction)
    {
        this.interaction = interaction;
    }

    public Integer getLikes()
    {
        return likes;
    }

    public void setLikes(Integer likes)
    {
        this.likes = likes;
    }

    public Integer getDislikes()
    {
        return dislikes;
    }

    public void setDislikes(Integer dislikes)
    {
        this.dislikes = dislikes;
    }

    public void addLike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.likes++;
        this.interaction.add(usuario);
        ComentarioServices.getInstance().update(this);
    }

    public void addDislike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.dislikes++;
        this.interaction.add(usuario);
        ComentarioServices.getInstance().update(this);
    }
}
