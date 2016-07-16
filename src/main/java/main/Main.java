package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import domain.*;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import services.*;
import url.Filtros;
import url.GetURLs;
import url.PostURLs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */
public class Main
{
    public static Usuario loggedInUser;
    public static String login = "Iniciar Sesión";


    public static void main(String[] args)
    {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        enableDebugScreen();

        if (UsuarioServices.getInstance().selectByID("user") == null)
            UsuarioServices.getInstance().insert(new Usuario("user", "user@admin.com", "admin", true));

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/ftl");
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        freeMarker.setConfiguration(configuration);

        GetURLs.create(freeMarker);
        PostURLs.create(freeMarker);
        Filtros.aplicarFiltros();
    }
	
	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
