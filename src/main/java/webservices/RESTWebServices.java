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
        get("/listImages/*", (request, response) -> {

            List<Image> aux = ImageServices.getInstance().select();
            List<Image> images = new ArrayList<Image>();
            String[] url = request.url().split("/");
            Usuario usuario = UsuarioServices.getInstance().selectByID(url[4]);

            for(Image image: aux)
            {
                if(image.getUsuario() != null)
                    if (usuario.getUsername().equals(image.getUsuario().getUsername()))
                        images.add(image);
            }

            ArrayList<Image> lol = new ArrayList<>();
            lol.add(images.get(0));
            return lol;
        }, json());

        post("/postImage", (request, response) ->
        {
            boolean success = false;
            if(!request.queryParams("description").isEmpty() && !request.queryParams("title").isEmpty())
            {
                String base64 = getFile("image.txt");
                Image image = new Image(Base64.decode(base64.substring(base64.indexOf("base64,") + "base64,".length())), request.queryParams("description"), request.queryParams("title"), loggedInUser);

                for (String tag : request.queryParams("tags").split(","))
                    image.getListaEtiquetas().add(new Etiqueta(tag));

                ImageServices.getInstance().insert(image);
                success = true;
            }

            return success;
        }, json());

        get("/listUsers", (request, response) -> {
            List<Usuario> users = UsuarioServices.getInstance().select();
            ArrayList<String> usernames = new ArrayList<>();
            for(Usuario u : users)
                usernames.add(u.getUsername());

            return usernames;
        }, json());

    }
}
