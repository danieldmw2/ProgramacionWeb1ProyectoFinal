package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class EtiquetaServices extends DatabaseServices<Etiqueta>
{

    private static EtiquetaServices instance;

    private EtiquetaServices()
    {
        super(Etiqueta.class);
    }

    public static EtiquetaServices getInstance()
    {
        if (instance == null)
        {
            instance = new EtiquetaServices();
        }
        return instance;
    }
}
