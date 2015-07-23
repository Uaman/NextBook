package com.nextbook.utils;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/15/2015
 * Time: 7:06 AM
 */
public class DozerMapperFactory {

    private static final List<String> mappingFile = Arrays.asList(new String[]{"dozer.mapping.xml"});

    private static DozerBeanMapper dozerBeanMapper;

    private static void init(){
        if(dozerBeanMapper == null){
            dozerBeanMapper = new DozerBeanMapper();

            if(dozerBeanMapper.getCustomConverters() == null || dozerBeanMapper.getCustomConverters().size() == 0) {
                List<CustomConverter> customConverters = new ArrayList<CustomConverter>();
                //customConverters.add(new StringAndListConverter(String.class, List.class));
                //customConverters.add(new StringAndCategoryPropertyListConverter(String.class, List.class));
                dozerBeanMapper.setCustomConverters(customConverters);
            }

            dozerBeanMapper.setMappingFiles(mappingFile);
        }
    }

    public static DozerBeanMapper getDozerBeanMapper(){
        if(dozerBeanMapper == null){
            init();
        }
        return dozerBeanMapper;
    }

}
