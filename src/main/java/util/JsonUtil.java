package util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Ariel on 7/15/2016.
 */
public class JsonUtil
{
    public static String toJson(Object o)
    {
        return new Gson().toJson(o);
    }

    public static ResponseTransformer json()
    {
        return JsonUtil::toJson;
    }
}
