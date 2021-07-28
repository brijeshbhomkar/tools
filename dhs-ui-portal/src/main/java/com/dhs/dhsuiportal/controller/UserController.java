package com.dhs.dhsuiportal.controller;

import com.dhs.dhsuiportal.model.User;
import com.dhs.dhsuiportal.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private AuthenticateService authenticateService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView showTokenForm(ModelMap model) {
        String token = (String) model.getAttribute("token");
        if (token != null && !token.isEmpty()) {
            model.addAttribute("token", token);
        }
        return new ModelAndView("user", "user", new User());
    }

    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model, HttpSession session) {
        String token = authenticateService.authenticate(user.getUsername(), user.getPassword());
        model.addAttribute("token", token);
        return new ModelAndView("redirect:/download", model);
    }
}
