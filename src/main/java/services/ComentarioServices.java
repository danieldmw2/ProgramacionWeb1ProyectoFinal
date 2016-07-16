package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class ComentarioServices extends DatabaseServices<Comentario>
{

    private static ComentarioServices instance;

    private ComentarioServices()
    {
        super(Comentario.class);
    }

    public static ComentarioServices getInstance()
    {
        if (instance == null)
        {
            instance = new ComentarioServices();
        }
        return instance;
    }
}
