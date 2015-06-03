package com.kutsyk.pdf.uploader.web;

import com.kutsyk.pdf.uploader.service.FileUpload;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Kutsyk on 03.06.2015.
 */

public class FileUploadValidator implements Validator{

    @Override
    public boolean supports(Class clazz) {
        //just validate the FileUpload instances
        return FileUpload.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FileUpload file = (FileUpload)target;

        if(file.getFile().getSize()==0){
            errors.rejectValue("file", "required.fileUpload");
        }
    }
}