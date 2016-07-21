package url;

import static main.Main.db;
import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

import domain.*;
import services.ImageServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class GetURLs
{
    public static void create(FreeMarkerEngine freeMarker)
    {
        // localhost;4567/album/"AlbumID" or localhost;4567/album/"AlbumID"/"Image Filename"
        get("/image/*", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();

            String[] url = request.url().split("/");

            Image image = ImageServices.getInstance().selectByID(Long.parseLong(url[4]));
            model.put("titulo", image.getTitulo());
            model.put("descripcion", image.getDescripcion());
            model.put("comentarios", image.getListaComentarios());
            model.put("etiquetas", new HashSet<>(image.getListaEtiquetas()));
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());

            image.addView();
            ImageServices.getInstance().update(image);

            model.put("image", image);
            return new ModelAndView(model, "postImage.ftl");
        }, freeMarker);

        get("/", (request, response) -> {
            response.redirect("/home");
            return null;
        });

        get("/home", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            EntityManager entityManager = db.getEntityManager();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;

            //Change this way to a more efficient way later.
            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<>();
            String query = "select i from Image i order by i.date desc";
            Query q = entityManager.createQuery(query, Image.class);
            q.setFirstResult((page - 1) * 12);
            q.setMaxResults(12);
            images = q.getResultList();

            model.put("redirect", "home?p=" + (page + 1));
            model.put("images", images);
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/home/:etiqueta", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            EntityManager entityManager = db.getEntityManager();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            List<Image> images = new ArrayList<>();
            String etiqueta = request.params("etiqueta");
            String query = "select i from Image i, Etiqueta e WHERE i.id= e.imageID AND e.etiqueta='" + etiqueta + "' order by i.date desc";
            Query q = entityManager.createQuery(query, Image.class);
            q.setFirstResult((page - 1) * 12);
            q.setMaxResults(12);
            images = q.getResultList();
            model.put("redirect", "home/" + etiqueta + "?p=" + (page + 1));
            model.put("images", images);
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/upload", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "createImage.ftl");
        }, freeMarker);

        get("/edit", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            String tags = "";
            for (Etiqueta e : new HashSet<>(image.getListaEtiquetas()))
            {
                tags += e.getEtiqueta() + ",";
            }
            model.put("image", image);
            model.put("etiquetas", tags.substring(0, tags.length() - 1));
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "editImage.ftl"); // TODO
        }, freeMarker);

        get("/sign-up", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "registration.ftl");
        }, freeMarker);

        get("/sign-in", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            loggedInUser = null;
            request.session().attribute("usuario", loggedInUser);
            login = "Iniciar Sesión";
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        get("/zonaAdmin", (request, response) -> {
            response.redirect("/zonaAdmin/listUsers");
            return null;
        });

        get("/zonaAdmin/listUsers", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("users", UsuarioServices.getInstance().select());
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "listUsers.ftl");
        }, freeMarker);

        get("/MisFotos", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            EntityManager entityManager = db.getEntityManager();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            String param = request.queryParams("user");
            List<Image> images = new ArrayList<>();

            Usuario user = param != null ? UsuarioServices.getInstance().selectByID(param) : loggedInUser;

            if (user == null)
            {
                response.redirect("/home");
                return null;
            }

            String query = "select i from Image i WHERE i.usuario.username='" + user.getUsername() + "' order by i.date desc";
            Query q = entityManager.createQuery(query, Image.class);
            q.setFirstResult((page - 1) * 12);
            q.setMaxResults(12);
            images = q.getResultList();


            model.put("redirect", "MisFotos?p=" + (page + 1) + "&user=" + user.getUsername());
            model.put("images", images);
            model.put("iniciarSesion", login);
            model.put("user", loggedInUser == null ? null : loggedInUser.getUsername());
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/plainImage/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.params("id")));
            model.put("image", image);
            return new ModelAndView(model, "plainImage.ftl");
        }, freeMarker);


    }
}
