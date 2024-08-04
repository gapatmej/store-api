package com.aperalta.store.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String escapeCharacters(String string, char... chars){
        for (char c: chars) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\\");
            stringBuilder.append(c);
            string = string.replaceAll(stringBuilder.toString(),"\\\\"+c);
        }
        return string;
    }

    public static List stringToList(String string, String separator){
        String [] stringValues = string.split(separator);
        List<Object> values = new ArrayList<>();
        for (String value: stringValues) {
            values.add(value);
        }
        return values;
    }

}
