package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import domain.*;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import services.*;
import url.Filtros;
import url.GetURLs;
import url.PostURLs;
import webservices.RESTWebServices;
import java.io.*;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
public class Main
{
    public static Usuario loggedInUser;
    public static String login = "Iniciar Sesión";
    public static DatabaseServices db;


    public static void main(String[] args)
    {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        enableDebugScreen();
        db = new DatabaseServices(DatabaseServices.class);

        if (UsuarioServices.getInstance().selectByID("user") == null)
            UsuarioServices.getInstance().insert(new Usuario("user", "user@admin.com", "admin", true));

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);


        GetURLs.create(freeMarker);
        PostURLs.create(freeMarker);
        Filtros.aplicarFiltros();
        RESTWebServices.aplicarServiciosRESTful();
    }

    static int getHerokuAssignedPort()
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null)
        {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    public static DbxClientV1 getClient()
    {
        return new DbxClientV1(new DbxRequestConfig(""), "IIFa6SoYfrUAAAAAAADKNTUwd_ir6fs2bg25BB2wYU48NxbzmZG_xDPLV1wnkJvl");
    }

    public static String getFile(String file) throws Exception
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        getClient().getFile("/" + file, null, outputStream);
        return outputStream.toString();
    }
}
