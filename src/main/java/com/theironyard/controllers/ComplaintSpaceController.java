package com.theironyard.controllers;

import com.theironyard.entities.Category;
import com.theironyard.entities.Comment;
import com.theironyard.entities.Complaint;
import com.theironyard.entities.User;
import com.theironyard.services.CategoryRepository;
import com.theironyard.services.CommentRepository;
import com.theironyard.services.ComplaintRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.UUID;

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
        User user = getUserFromSession(session);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
    }

    @RequestMapping(path = "/add-complaint", method = RequestMethod.POST)
    public String add(HttpSession session, String text, String category) {
        User user = getUserFromSession(session);
        Category cat = categories.findByCategory(category);
        if (cat == null) cat = categories.save(new Category(UUID.randomUUID(), category));
        Complaint complaint = new Complaint(UUID.randomUUID(), cat, user, text);
        complaints.save(complaint);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-complaint", method = RequestMethod.POST)
    public String editComplaint(UUID id, String text) {
        complaints.findById(id).setText(text);
        return "redirect:/";
    }

    @RequestMapping(path = "delete-complaint", method = RequestMethod.POST)
    public String deleteComplaint(UUID id) {
        complaints.delete(complaints.findById(id));
        return "redirect:/";
    }

    @RequestMapping(path = "/add-comment", method = RequestMethod.POST)
    public String add(HttpSession session, String text, UUID complaintId) {
        User user = getUserFromSession(session);
        Complaint complaint = complaints.findById(complaintId);
        Comment comment = new Comment(UUID.randomUUID(),text, complaint, user);
        comments.save(comment);
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findByUserName(userName);
        if (user == null) {
            user = new User(UUID.randomUUID(), userName, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Invalid username/password");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    public User getUserFromSession(HttpSession session) {
        return users.findByUserName((String)session.getAttribute("userName"));
    }
}
