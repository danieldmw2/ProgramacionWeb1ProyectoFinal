package domain;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import services.ComentarioServices;
import services.ImageServices;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Image implements Serializable
{
    @Id @GeneratedValue private Long id;
    @Column(nullable = false) private String filename;
    @OneToOne private Usuario usuario;
    @Column(nullable = false, columnDefinition = "VARCHAR(1000)") private String descripcion;
    @Column(nullable = false) private String titulo;
    @Basic(fetch = FetchType.EAGER)
    @Lob @Column(nullable=false, columnDefinition="BLOB") private byte[] image;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}) private List<Etiqueta> listaEtiquetas;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) private List<Usuario> interaction;
    @Column(nullable = false) private Integer likes;
    @Column(nullable = false) private Integer dislikes;
    @Column(nullable = false) private Date date;
    private Long views;


    @Transient private String base;
    @Transient private Double bandwidthTotal;
    @Transient private float bandwidth;
    @Transient private List<Comentario> listaComentarios;

    public Image()
    {
        likes = 0;
        dislikes = 0;
        views = 0l;
        date = new Date();
        interaction = new ArrayList<>();
        listaEtiquetas = new ArrayList<>();
    }

    public Image(String filename, String descripcion, String titulo,Usuario usuario) throws IOException
    {
        this.filename = filename.substring(filename.lastIndexOf("\\") + 1);
        File imgPath = new File(filename);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        this.image = baos.toByteArray();
        this.base = Base64.encode(image);

        this.usuario = usuario;
        this.descripcion = descripcion;
        this.titulo = titulo;

        this.likes = 0;
        this.dislikes = 0;
        this.views = 0l;
        this.bandwidthTotal = ((this.views.doubleValue() * this.image.length)/1024)/1024;
        this.bandwidth = this.image.length/1024;
        this.date = new Date();
        this.interaction = new ArrayList<>();
        this.listaEtiquetas = new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
        this.base = Base64.encode(image);
    }

    public String getBase()
    {
        base = Base64.encode(image);
        return base;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<Comentario> getListaComentarios()
    {
        listaComentarios = new ArrayList<>();
        for (Comentario c : ComentarioServices.getInstance().select())
            if(c.getImage().getId().equals(this.id))
                listaComentarios.add(c);

        return listaComentarios;
    }

    public List<Etiqueta> getListaEtiquetas()
    {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<Etiqueta> etiquetas)
    {
        this.listaEtiquetas = etiquetas;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public void addLike(Usuario usuario)
    {
        if(this.interaction.contains(usuario))
            return;

        this.likes++;
        this.interaction.add(usuario);
        ImageServices.getInstance().update(this);
    }

    public Long getViews()
    {
        return views;
    }



    public void addDislike(Usuario usuario)
    {
        if (this.interaction.contains(usuario))
            return;

        this.dislikes++;
        this.interaction.add(usuario);
        ImageServices.getInstance().update(this);
    }

    public void addView()
    {
        views++;
        bandwidthTotal = ((views.doubleValue()* image.length)/1024)/1024;
        bandwidth = image.length/1024;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public Double getBandwidthTotal() {
        return bandwidthTotal;
    }
}
