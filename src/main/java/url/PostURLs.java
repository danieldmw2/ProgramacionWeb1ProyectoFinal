package url;

import domain.*;
import services.ComentarioServices;
import services.ImageServices;
import services.UsuarioServices;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static main.Main.loggedInUser;
import static main.Main.login;
import static spark.Spark.post;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class PostURLs
{
    public static boolean invalid = false;

    public static boolean isValidEmail(String email)
    {
        if(email == null)
            return false;
        else
            return Pattern.compile("^.+@.+\\..+$").matcher(email).matches();
    }


    public static void create(FreeMarkerEngine freeMarker)
    {
        post("/insertUser", (request, response) -> {

            if(!request.queryParams("username").isEmpty() && !request.queryParams("email").isEmpty()
                    && !request.queryParams("password").isEmpty())
            {
                if(isValidEmail(request.queryParams("email")))
                {
                    Usuario newUser = new Usuario();

                    newUser.setUsername(request.queryParams("username"));
                    newUser.setEmail(request.queryParams("email"));
                    newUser.setPassword(request.queryParams("password"));
                    newUser.setAdministrator(false);

                    UsuarioServices.getInstance().insert(newUser);

                    response.redirect("/home");
                    return null;
                }
            }

            return "El formulario recibido por el servidor fue invalido.";
        });

        post("/insertImage", (request, response) -> {
            if(!request.queryParams("image").isEmpty() && !request.queryParams("description").isEmpty() &&
                    !request.queryParams("title").isEmpty())
            {
                Image image = new Image("C:\\" + request.queryParams("image"), request.queryParams("description"), request.queryParams("title"), loggedInUser);

                for (String tag : request.queryParams("tags").split(","))
                    image.getListaEtiquetas().add(new Etiqueta(tag));

                ImageServices.getInstance().insert(image);
                response.redirect("/home");
                return null;
            }
            return "El formulario recibido por el servidor fue invalido.";
        });

        post("/editImage", (request, response) -> {
            if(!request.queryParams("description").isEmpty() && !request.queryParams("title").isEmpty())
            {


                Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
                image.setDescripcion(request.queryParams("description"));
                image.setTitulo(request.queryParams("title"));
                image.setListaEtiquetas(new ArrayList<>());

                for (String tag : request.queryParams("tags").split(","))
                    image.getListaEtiquetas().add(new Etiqueta(tag));

                ImageServices.getInstance().update(image);

                response.redirect("/home");
                return null;
            }
            return "El formulario recibido por el servidor fue invalido.";
        });

        post("/deleteImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));

            for(Comentario c : image.getListaComentarios())
                ComentarioServices.getInstance().delete(c);

            ImageServices.getInstance().delete(image);

            response.redirect("/home");
            return null;
        });

        post("/zonaAdmin/deleteUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            UsuarioServices.getInstance().delete(user);

            response.redirect("/zonaAdmin");
            return null;
        });

        post("/zonaAdmin/editUser", (request, response) -> {
            Usuario user = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            user.setAdministrator(true);

            UsuarioServices.getInstance().update(user);

            response.redirect("/zonaAdmin");
            return null;
        });

        post("/login", (request, response) -> {
            if(!request.queryParams("username").isEmpty() && !request.queryParams("password").isEmpty())
            {
                loggedInUser = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
                if (loggedInUser.getPassword().equals(request.queryParams("password")))
                {
                    request.session(true).attribute("usuario", loggedInUser);
                    response.cookie("user", loggedInUser.getUsername());
                    login = "Cerrar Sesión";
                } else
                    loggedInUser = null;

                response.redirect("/home");
                return null;
            }
            return "El formulario recibido por el servidor fue invalido.";
        });

        post("/logout", (request, response) -> {
            login = "Iniciar Sesión";

            request.session(true).attribute("usuario", null);
            response.cookie("user", "");
            loggedInUser = null;
            response.redirect("/home");
            return null;
        });

        post("/insertComment", (request, response) -> {

            if(!request.queryParams("comentario").isEmpty())
            {
                Image album = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("idImage")));

                Comentario c = new Comentario();
                c.setImage(album);
                c.setAutor(loggedInUser);
                c.setComentario(request.queryParams("comentario"));

                ComentarioServices.getInstance().insert(c);

                response.redirect("/image/" + request.queryParams("idImage"));
                return null;
            }
            return "El formulario enviado al servidor fue invalido.";
        });

        post("/editComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.setComentario(request.queryParams("comentario"));

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/deleteComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            ComentarioServices.getInstance().delete(c);

            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/likeComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.addLike(loggedInUser);

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/dislikeComment", (request, response) -> {
            Comentario c = ComentarioServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            c.addDislike(loggedInUser);

            ComentarioServices.getInstance().update(c);
            response.redirect("/image/" + request.queryParams("idImage"));
            return null;
        });

        post("/likeImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            image.addLike(loggedInUser);

            ImageServices.getInstance().update(image);
            response.redirect("/image/" + request.queryParams("id"));
            return null;
        });

        post("/dislikeImage", (request, response) -> {
            Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
            image.addDislike(loggedInUser);

            ImageServices.getInstance().update(image);
            response.redirect("/image/" + request.queryParams("id"));
            return null;
        });

    }
}