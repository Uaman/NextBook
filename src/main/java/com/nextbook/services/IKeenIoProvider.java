package com.nextbook.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 9/23/2015
 * Time: 9:06 AM
 */
@Service
public interface IKeenIoProvider {

    void addKeenData(Model model);

}
