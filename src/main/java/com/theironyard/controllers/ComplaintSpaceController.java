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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String home(Model model, HttpSession session, String category, Integer page) {
        User user = getUserFromSession(session);
        page = (page == null) ? 0 : page;
        Page<Complaint> p;
        PageRequest pr = new PageRequest(page, 5);
        Category cat = categories.findByCategory(category);
        if (category != null) {
            p = complaints.findByCategory(pr, cat);
            if (user != null) {
                for (Complaint complaint : p) {
                    if (user.getUserName().equals(complaint.getUser().getUserName())) {
                        complaint.setAuthor(true);
                    }
                }
                model.addAttribute("user", user);
            }
            model.addAttribute("complaints", p);
        }
        else {
            p = complaints.findAll(pr);
            if (user != null) {
                for (Complaint complaint : p) {
                    if (user.getUserName().equals(complaint.getUser().getUserName())) {
                        complaint.setAuthor(true);
                    }
                }
                model.addAttribute("user", user);
            }
            model.addAttribute("complaints", p);

        }
        model.addAttribute("nextPage", page+1);
        model.addAttribute("showNext", p.hasNext());
        model.addAttribute("showPrevious", p.hasPrevious());
        model.addAttribute("previousPage", page-1);
        model.addAttribute("category", category);

        return "home";
    }
    @RequestMapping(path = "/view-complaint", method = RequestMethod.GET)
    public String view(Model model, HttpSession session, Integer id) {
        User user = getUserFromSession(session);
        Complaint complaint = complaints.findOne(id);
        List<Comment> commentList = comments.findByComplaint(complaint);
        if (user != null) {
            for (Comment comment : commentList) {
                if (user.getUserName().equals(comment.getUser().getUserName())) {
                    comment.setAuthor(true);
                }
            }
            model.addAttribute("user", user);
        }
        model.addAttribute("comments", commentList);
        model.addAttribute("complaint", complaint);
        return "complaint";
    }
    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer complaintId, Integer commentId, HttpSession session) throws Exception {
        User user = getUserFromSession(session);
        if (user ==null) throw new Exception("not logged in");
        if (complaintId != null) {
            model.addAttribute("complaint", complaints.findOne(complaintId));
        }
        else if (commentId != null) {
            model.addAttribute("comment", comments.findOne(commentId));
        }
        return "edit";
    }

    @RequestMapping(path = "/add-complaint", method = RequestMethod.POST)
    public String add(HttpSession session, String text, String category) throws Exception {
        User user = getUserFromSession(session);
        if (user ==null) throw new Exception("not logged in");
        Category cat = categories.findByCategory(category);
        if (cat == null) cat = categories.save(new Category(category));
        Complaint complaint = new Complaint(cat, user, text);
        complaints.save(complaint);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-complaint", method = RequestMethod.POST)
    public String editComplaint(Integer id, String text, HttpSession session) throws Exception {
        User user = getUserFromSession(session);
        if (user ==null) throw new Exception("not logged in");
        Complaint complaint = complaints.findOne(id);
        if (!user.getUserName().equals(complaint.getUser().getUserName())) {
            throw new Exception("this complaint does not belong to you");
        }
        if (!text.isEmpty()) complaint.setText(text);
        complaints.save(complaint);
        return "redirect:/";
    }

    @RequestMapping(path = "delete-complaint", method = RequestMethod.POST)
    public String deleteComplaint(Integer id, HttpSession session) throws Exception {
        User user = getUserFromSession(session);
        if (user == null) throw new Exception("not logged in");
        Complaint complaint = complaints.findOne(id);
        if (!user.getUserName().equals(complaint.getUser().getUserName())) throw new Exception("this complaint does not belong to you");
        complaints.delete(complaint);
        return "redirect:/";
    }

    @RequestMapping(path = "/add-comment", method = RequestMethod.POST)
    public String add(HttpSession session, String text, Integer id) throws Exception {
        User user = getUserFromSession(session);
        Complaint complaint = complaints.findOne(id);
        Comment comment = new Comment(text, complaint, user);
        comments.save(comment);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-comment", method = RequestMethod.POST)
    public String editComment(Integer id, String text, HttpSession session) throws Exception {
        User user = getUserFromSession(session);
        if (user ==null) throw new Exception("not logged in");
        Comment comment = comments.findOne(id);
        if (!user.getUserName().equals(comment.getUser().getUserName())) throw new Exception("this comment does not belong to you");
        if (!text.isEmpty()) comment.setText(text);
        comments.save(comment);
        return "redirect:/";
    }

    @RequestMapping(path = "delete-comment", method = RequestMethod.POST)
    public String deleteComment(Integer id, HttpSession session) throws Exception {
        User user = getUserFromSession(session);
        if (user == null) throw new Exception("not logged in");
        Comment comment = comments.findOne(id);
        if (!user.getUserName().equals(comment.getUser().getUserName())) throw new Exception("this comment does not belong to you");
        comments.delete(comment);
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
