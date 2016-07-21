package url;


import static main.Main.db;
import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

import domain.*;
import services.EtiquetaServices;
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
public class GetURLs {
    public static void create(FreeMarkerEngine freeMarker) {
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
            q.setFirstResult((page-1) * 12);
            q.setMaxResults(12);
            images = q.getResultList();

            model.put("redirect","home?p="+ (page+1));
            model.put("images", images);
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/home/:etiqueta", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            EntityManager entityManager = db.getEntityManager();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<>();
            String etiqueta = request.params("etiqueta");
            for(Image a: aux)
            {
                for(Etiqueta e: new HashSet<>(a.getListaEtiquetas()))
                {
                    if(e.getEtiqueta().toLowerCase().equals(request.params("etiqueta").toLowerCase()))
                    {
                        images.add(a);
                        break;
                    }
                }
            };
            model.put("redirect", "home/" + etiqueta+ "?p="+ (page+1));
            model.put("images", images);
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

        get("/upload", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "createImage.ftl");
        }, freeMarker);

        get("/edit", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            String tags = "";
            for (Etiqueta e : new HashSet<>(image.getListaEtiquetas())) {
                tags += e.getEtiqueta() + ",";
            }
            model.put("image", image);
            model.put("etiquetas", tags.substring(0, tags.length() - 1));
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "editImage.ftl"); // TODO
        }, freeMarker);

        get("/sign-up", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "registration.ftl");
        }, freeMarker);

        get("/sign-in", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            loggedInUser = null;
            request.session().attribute("usuario", loggedInUser);
            login = "Iniciar Sesión";
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        get("/zonaAdmin", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("users", UsuarioServices.getInstance().select());
            model.put("iniciarSesion", login);
            return new ModelAndView(model, "listUsers.ftl");
        }, freeMarker);

        get("/MisFotos", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            String param = request.queryParams("user");

            //Change this way to a more efficient way later.
            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<>();

            Usuario user = param != null ? UsuarioServices.getInstance().selectByID(param) : loggedInUser;

            if (user == null) {
                response.redirect("/home");
                return null;
            }

           for (int j = 0; j < aux.size(); j++)
            {
                Image i = aux.get(j);
                if (!i.getUsuario().getUsername().equals(user.getUsername()))
                {
                    aux.remove(j);
                    j--;
                }
            }

            for (int i = 0; i < 12; i++)
            {
                int index = i + ((page - 1) * 12);

                if (index < aux.size())
                    images.add(aux.get(index));
                else
                    break;
            }
            model.put("redirect", "MisFotos?p=" + (page + 1) + "&user=" + user.getUsername());
            model.put("images", images);
            model.put("iniciarSesion", login);
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
