package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
public class UsuarioServices extends DatabaseServices<Usuario>
{
    private static UsuarioServices instance;

    private UsuarioServices()
    {
        super(Usuario.class);
    }

    public static UsuarioServices getInstance()
    {
        if (instance == null)
        {
            instance = new UsuarioServices();
        }
        return instance;
    }
}
