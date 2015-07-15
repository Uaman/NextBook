package com.nextbook.utils;


import org.dozer.DozerConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/15/2015
 * Time: 7:09 AM
 */

public class StringAndListConverter extends DozerConverter<String, List> {
    /**
     * Defines two types, which will take part transformation.
     * As Dozer supports bi-directional mapping it is not known which of the classes is source and
     * which is destination. It will be decided in runtime.
     *
     * @param prototypeA type one
     * @param prototypeB type two
     */
    public StringAndListConverter(Class<String> prototypeA, Class<List> prototypeB) {
        super(prototypeA, prototypeB);
    }

    @Override
    public List convertTo(String source, List destination) {
        List lst = null;
        if(source != null && !source.equals("")){
            lst = new ArrayList();
            String elementsOfTheList [] = source.split("[,]");
            for (String s : elementsOfTheList) {
                lst.add(s);
            }
        }
        return lst;
    }

    @Override
    public String convertFrom(List source, String destination) {
        StringBuilder stringBuilder = new StringBuilder();
        if(source != null && source.size() > 0){
            for (Object o : source) {
                stringBuilder.append(o).append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
