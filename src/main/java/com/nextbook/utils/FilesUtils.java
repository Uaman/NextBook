package com.nextbook.utils;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/9/2015
 * Time: 10:10 AM
 */
public class FilesUtils {

    public static String getFIleExtensions(String fileName){
        int index = fileName.lastIndexOf('.');
        if(index < 0)
            return "";
        return fileName.substring(index+1);
    }

}
