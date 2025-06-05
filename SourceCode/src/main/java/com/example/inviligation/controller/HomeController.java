package com.example.inviligation.controller;

import com.example.inviligation.model.Faculty;
import com.example.inviligation.service.IFacultyService;
import com.example.inviligation.service.ITimeTableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private IFacultyService facultyService;
    @Autowired
    private HttpServletRequest req;

    @Autowired
    private ITimeTableService tableService;

    private static final String ADMINLOGIN = "admin/home";
    private static final String HODLOGIN = "hod/home";
    private static final String LOGIN = "index";

    @GetMapping("/")
    private String index() {
        return "index";
    }

    @GetMapping("/addFaculty")
    private String addFac(){
        return "admin/addFaculty";
    }


    @GetMapping("/viewFaculty")
    private String allFaculty(Model model){
        model.addAttribute("faculty",facultyService.all());
        return "admin/viewFaculty";
    }

    @GetMapping("/timetable")
    private String timeTable(Model model){
        model.addAttribute("time",facultyService.all().stream().filter(i->i.isStatus() == true).toList());
        return "hod/timeTable";
    }


    @GetMapping("/allFaculty")
    private String faculty(Model model){
        model.addAttribute("time",facultyService.all().stream().filter(i->i.isStatus() == false).toList());
        return "hod/allFaculty";
    }
    @GetMapping("/faculty")
    private String students(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null) {
            model.addAttribute("faculty", facultyService.getByKeyword(keyword));

        } else {
            model.addAttribute("faculty", facultyService.all());
        }
        return "hod/timeTable";
    }
    @PostMapping("/login")
    private String login(Model model, String email, String password) {
        try {
            if (email.equals("admin@gmail.com") && password.equals("admin")) {
                req.getSession().setAttribute("admin","admin");
                model.addAttribute("success", "Admin login successfully");
                return "admin/home";
            }
            if (email.equals("examcoordinator@gmail.com") && password.equals("coordinator")) {
                req.getSession().setAttribute("hod","hod");
                model.addAttribute("success", "Hod login successfully");
                return "hod/home";
            }
            if (email.equals("") && password.equals("")){
                model.addAttribute("error","Please fill all the fields");
                return "login";
            }
            Faculty check=facultyService.getByEmailAndPassword(email, password);
            if (check !=null){
                req.getSession().setAttribute("faculty",check);
                model.addAttribute("success","Faculty login successfully");
                return "redirect:/facultyHome";
            }else{
                model.addAttribute("success","Faculty login successfully");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return LOGIN;
        }
    }
}
