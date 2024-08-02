package com.aperalta.store.utils;

import com.google.gson.Gson;

public class GsonUtils {

    private GsonUtils(){}

    private static final Gson gson = new Gson();

    public static String entityToJson (Object entity){
        return gson.toJson(entity);
    }

}
