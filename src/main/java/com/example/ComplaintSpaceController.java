package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Controller
public class ComplaintSpaceController {
    @Autowired
    UserRepository users;
    @Autowired
    CategoryRepository categories;
    @Autowired
    ComplaintRepository complaints;
    @Autowired
    CommentRepository comments;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {

        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) {

    }

}
