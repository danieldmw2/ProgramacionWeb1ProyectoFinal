package url;

import domain.*;
import main.Main;
import services.ImageServices;
import services.UsuarioServices;

import static spark.Spark.before;
import static spark.Spark.halt;

/**
 * Created by Daniel's Laptop on 6/25/2016.
 */
public class Filtros
{
    public static void aplicarFiltros()
    {
        before("/*", (request, response) -> {
            if (Main.loggedInUser == null)
            {
                if (request.cookie("user") != null && !request.cookie("user").equals(""))
                {
                    Usuario user = UsuarioServices.getInstance().selectByID(request.cookie("user"));
                    request.session(true).attribute("usuario", user);
                    Main.loggedInUser = user;
                    Main.login = "Cerrar Sesión";
                }
            }
        });

        before("/sign-in", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario != null)
                response.redirect("/home");
        });

        before("/login", (request, response) -> {
            Usuario usuario = UsuarioServices.getInstance().selectByID(request.queryParams("username"));
            if (usuario == null)
                response.redirect("/home");
        });

        before("/upload", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null)
                request.session(true).attribute("usuario", createAnon());
        });

        before("/edit", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario != null)
            {
                Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
                if(!usuario.isAdministrator() && !image.getUsuario().getUsername().equals(usuario.getUsername()))
                    halt(401, "Tiene que ser administrador del sistema o autor del post para hacer esta accion");
            }
            else
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
        });

        before("/zonaAdmin/*", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null)
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
            else if (!usuario.isAdministrator())
                halt(401, "Tiene que ser administrador del sistema para poder entrar a esta area");
        });

        before("/zonaAdmin/deleteUser", (request, response) -> {
            String user = request.queryParams("username");

            if(user.equalsIgnoreCase("user"))
                halt(401, "El usuario predeterminado no puede ser borrado");
        });

        before("/deleteImage", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario != null)
            {
                Image image = ImageServices.getInstance().selectByID(Long.parseLong(request.queryParams("id")));
                if(!usuario.isAdministrator() && !image.getUsuario().getUsername().equals(usuario.getUsername()))
                    halt(401, "Tiene que ser administrador del sistema o autor del album para hacer esta accion");
            }
            else
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
        });

        before("/insertComment", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null)
                request.session(true).attribute("usuario", createAnon());

        });

        before("/deleteComment", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario != null)
            {
                if(!usuario.isAdministrator())
                    halt(401, "Tiene que ser administrador del sistema o autor del album para hacer esta accion");
            }
            else
                halt(401, "Tiene que tener algun usuario en session para hacer esta accion");
        });

    }

    public static Usuario createAnon()
    {
        Usuario user = new Usuario("Anon", "Anon@Anon.com", "", false);

        if(UsuarioServices.getInstance().selectByID(user.getUsername()) == null)
            UsuarioServices.getInstance().insert(user);

        Main.loggedInUser = user;
        Main.login = "Cerrar Sesión";

        return  user;
    }
}