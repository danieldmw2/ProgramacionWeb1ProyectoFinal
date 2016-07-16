package services;

import domain.*;

/**
 * Created by Daniel's Laptop on 5/31/2016.
 */
public class ImageServices extends DatabaseServices<Image>
{
    private static ImageServices instance;

    private ImageServices()
    {
        super(Image.class);
    }

    public static ImageServices getInstance()
    {
        if (instance == null)
        {
            instance = new ImageServices();
        }
        return instance;
    }
}
