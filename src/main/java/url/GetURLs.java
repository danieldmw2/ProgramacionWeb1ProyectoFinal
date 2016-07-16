package url;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.get;

import domain.*;
import services.ImageServices;
import services.UsuarioServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

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

            image.addView();
            ImageServices.getInstance().update(image);

            model.put("image", image);
            return new ModelAndView(model, "postImage.ftl");
        }, freeMarker);

        get("/home", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            int page = request.queryParams("p") != null ? Integer.parseInt(request.queryParams("p")) : 1;
            model.put("page", (page + 1));

            //Change this way to a more efficient way later.
            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<>();

            for (int i = 0; i < 5; i++)
            {
                int index = i + ((page - 1) * 5);

                if (index < aux.size())
                    images.add(aux.get(index));
                else
                    break;
            }

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
            String tags="";
            for(Etiqueta e: new HashSet<>(image.getListaEtiquetas())){
                tags += e.getEtiqueta() +",";
            }
            model.put("image", image);
            model.put("etiquetas", tags.substring(0,tags.length()-1));
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


    }
}
