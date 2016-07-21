package webservices;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import domain.Etiqueta;
import domain.Image;
import domain.Usuario;
import services.ImageServices;
import services.UsuarioServices;

import java.util.ArrayList;
import java.util.List;

import static main.Main.getFile;
import static main.Main.loggedInUser;
import static spark.Spark.get;
import static spark.Spark.post;
import static util.JsonUtil.json;

/**
 * Created by Daniel's Laptop on 7/18/2016.
 */
public class RESTWebServices
{
    public static void aplicarServiciosRESTful()
    {
        get("/REST/listImages", (request, response) -> {
            List<Image> aux = ImageServices.getInstance().select();
            String url = request.queryParams("user");
            Usuario usuario = UsuarioServices.getInstance().selectByID(url);
            ArrayList<String> actualImages = new ArrayList<>();

            if (usuario == null)
                return null;

            for (Image image : aux)
                if (image.getUsuario() != null)
                    if (usuario.getUsername().equals(image.getUsuario().getUsername()))
                        actualImages.add(image.getBase());

            return actualImages;
        }, json());

        post("/REST/postImage", (request, response) ->
        {
            if (UsuarioServices.getInstance().selectByID("REST Service") == null)
                UsuarioServices.getInstance().insert(new Usuario("REST Service", "REST@Service.com", "admin", true));

            boolean success = false;
            if (!request.queryParams("description").isEmpty() && !request.queryParams("title").isEmpty())
            {
                String base64 = getFile("image.txt");
                Image image = new Image(Base64.decode(base64), request.queryParams("description"), request.queryParams("title"), UsuarioServices.getInstance().selectByID("REST Service"));
                ImageServices.getInstance().insert(image);
                for (String tag : request.queryParams("tags").split(","))
                    image.getListaEtiquetas().add(new Etiqueta(tag, image.getId()));

                ImageServices.getInstance().update(image);
                success = true;
            }

            return success;
        }, json());

        get("/REST/listUsers", (request, response) -> {
            List<Usuario> users = UsuarioServices.getInstance().select();
            ArrayList<String> usernames = new ArrayList<>();
            for (Usuario u : users)
                usernames.add(u.getUsername());

            return usernames;
        }, json());

    }
}
