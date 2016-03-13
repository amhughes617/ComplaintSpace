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
import java.util.List;

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
            List<Complaint> complaintList = (List) complaints.findAll();
            for (Complaint complaint : complaintList) {
                if (user.getUserName().equals(complaint.getUser().getUserName())) {
                    complaint.setAuthor(true);
                }
            }
            model.addAttribute("complaints", complaintList);
            model.addAttribute("user", user);
        }
        return "home";
    }
    @RequestMapping(path = "/view-complaint", method = RequestMethod.GET)
    public String view(Model model, HttpSession session, Integer id) {
        User user = getUserFromSession(session);
        Complaint complaint = complaints.findOne(id);
        List<Comment> commentList = comments.findByComplaint(complaint);
        for (Comment comment : commentList) {
            if (user.getUserName().equals(comment.getUser().getUserName())) {
                comment.setAuthor(true);
            }
        }
        model.addAttribute("comments", comments);
        model.addAttribute("complaint", complaint);
        model.addAttribute("user", user);
        return "complaint";
    }
    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer complaintId, Integer commentId) {
        model.addAttribute("complaint", complaints.findOne(complaintId));
        model.addAttribute("comment", comments.findOne(commentId));
        return "edit";
    }

    @RequestMapping(path = "/add-complaint", method = RequestMethod.POST)
    public String add(HttpSession session, String text, String category) {
        User user = getUserFromSession(session);
        Category cat = categories.findByCategory(category);
        if (cat == null) cat = categories.save(new Category(category));
        Complaint complaint = new Complaint(cat, user, text);
        complaints.save(complaint);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-complaint", method = RequestMethod.POST)
    public String editComplaint(Integer id, String text) {
        complaints.findOne(id).setText(text);
        return "redirect:/";
    }

    @RequestMapping(path = "delete-complaint", method = RequestMethod.POST)
    public String deleteComplaint(Integer id) {
        complaints.delete(complaints.findOne(id));
        return "redirect:/";
    }

    @RequestMapping(path = "/add-comment", method = RequestMethod.POST)
    public String add(HttpSession session, String text, Integer complaintId) {
        User user = getUserFromSession(session);
        Complaint complaint = complaints.findOne(complaintId);
        Comment comment = new Comment(text, complaint, user);
        comments.save(comment);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-commment", method = RequestMethod.POST)
    public String editComment(Integer id, String text) {
        comments.findOne(id).setText(text);
        return "redirect:/";
    }

    @RequestMapping(path = "delete-comment", method = RequestMethod.POST)
    public String deleteComment(Integer id) {
        comments.delete(comments.findOne(id));
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password));
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
