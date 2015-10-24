package com.nextbook.domain.response;

import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by oleh on 20.10.2015.
 */
public class ResponseOnAjaxRegistration<T> {

    /**
     * -1   - problems with validation
     * 0    - failed to create(problems with db, etc.)(default value)
     * 1    - OK
     */
    private int code = PROBLEMS_WITH_SERVICE;

    public List<String> errors;

    public ResponseOnAjaxRegistration(Set<ConstraintViolation<T>> validationErrors, MessageSource messageSource, Locale locale){
        for(ConstraintViolation<T> error : validationErrors){
            addError(messageSource.getMessage(error.getMessage(), null, locale));
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String errorCode){
        if(errors == null)
            errors = new ArrayList<String>();
        errors.add(errorCode);
        code = PROBLEMS_WITH_VALIDATION;
    }

    public boolean hasErrors(){
        return errors != null && errors.size() != 0;
    }

    public static final int PROBLEMS_WITH_VALIDATION = -1;

    public static final int PROBLEMS_WITH_SERVICE = 0;

    public static final int OK = 1;
}
