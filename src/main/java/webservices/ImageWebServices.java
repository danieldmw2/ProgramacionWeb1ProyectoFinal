

package webservices;
import static spark.Spark.*;

import domain.Image;
import domain.Usuario;
import services.ImageServices;
import services.UsuarioServices;

import java.util.ArrayList;
import java.util.List;

import static util.JsonUtil.json;

/**
 * Created by Ariel on 7/16/2016.
 */
public class ImageWebServices
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
            return images;
        }, json());

    }
}

