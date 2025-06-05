package com.example.inviligation.controller;

import com.example.inviligation.model.Faculty;

import com.example.inviligation.service.IFacultyService;
import com.example.inviligation.service.ITimeTableService;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Controller
public class FacultyController {

    @Autowired
    private IFacultyService facultyService;

    @Autowired
    private HttpServletRequest req;
    @Autowired
    private ITimeTableService tableService;


    @GetMapping("/time")
    private String time(Model model) {
        Faculty faculty = (Faculty) req.getSession().getAttribute("faculty");
        model.addAttribute("time", facultyService.all().stream().filter(i -> i.getName().equals(faculty.getName())));
        return "faculty/timeTable";
    }

    @GetMapping("/facultyHome")
    private String facultyHome(Model model) {
        try {
            Faculty faculty = (Faculty) req.getSession().getAttribute("faculty");
            if (faculty != null) {
                model.addAttribute("faculty", facultyService.getById(faculty.getId()));
                return "faculty/home";
            } else {
                model.addAttribute("error", "Faculty Not found");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }


    @PostMapping("/addFaculty")
    private String addFaculty(Model model, String name, String email, String password, String dept, Long phoneNumber) {
        try {
            Faculty checkFaculty = facultyService.getByEmailAndPassword(email, password);
            Faculty faculty = Faculty.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .dept(dept)
                    .phoneNumber(phoneNumber)
                    .build();
            if (checkFaculty == null) {
                facultyService.add(faculty);
                model.addAttribute("success", "Faculty added successfully");
                return "admin/addFaculty";
            } else {
                model.addAttribute("error", "Details already exists");
                return "admin/addFaculty";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/addFaculty";
        }
    }

    public static final String TWILIO_ACCOUNT_SID = "";
    public static final String TWILIO_AUTH_TOKEN = "";

    @PostMapping("/sendsms")
    private String sendSms(Model model, LocalDate date, String fromTime, String toTime) {

        boolean check = true;
        List<Faculty> facultyList = facultyService.all().stream().filter(i -> i.isStatus() == check).toList();
        for (Faculty faculty : facultyList) {
            faculty.setDate(date);
            faculty.setFromTime(fromTime);
            faculty.setToTime(toTime);
            Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
            com.twilio.rest.api.v2010.account.Message message = Message.creator(
                    new PhoneNumber("+91".concat(faculty.getPhoneNumber().toString())),
                    new PhoneNumber("+15077095064"),
                    "your Invigilation room number  is: " + faculty.getRoomNumber()).create();
        }
        model.addAttribute("success", "Invigilation assigned successfully");
        return "hod/assign";
    }

    @PostMapping("/assign")
    private String assign(Model model, LocalDate date, String fromTime, String toTime) throws UnirestException {

        boolean check = true;
        List<Faculty> facultyList = facultyService.all().stream().filter(i -> i.isStatus() == check).toList();
        String apiUrl = "https://www.fast2sms.com/dev/bulkV2";
        String apiKey = "8b295mfuL31JwNxVoPegHzqsCGvDQc4kFUA6IlBSZTYMROatErHfCn3eV0pWBQOSbl9c8RYdPjG5uFNx";

        for (Faculty faculty : facultyList
        ) {
            String message = "Invigilation assigned for you the room Number is : " + faculty.getRoomNumber() ;
            faculty.setDate(date);
            faculty.setFromTime(fromTime);
            faculty.setToTime(toTime);
            Unirest.post(apiUrl)
                    .header("authorization", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("message=" + message + "&route=q&numbers= " + faculty.getPhoneNumber())
                    .asString();
            facultyService.update(faculty);
        }
        model.addAttribute("success", "Invigilation assigned successfully");
        return "hod/assign";
    }

    @PostMapping("/status")
    private String status(Long[] arr) {
        System.out.println(arr);
        Random random = new Random();
        Random random1 = new Random(System.currentTimeMillis());
        int high = 110;
        int low = 101;
        for (Long i : arr) {
            Faculty faculty1 = facultyService.getById(i);
            Integer randomNumber = random.nextInt(high - low) + low;
            faculty1.setRoomNumber(randomNumber);
            faculty1.setStatus(true);

            facultyService.update(faculty1);
        }
        return "hod/assign";
    }

    @PostMapping("/assignRoom")
    private String assignRoom(Model model) {
        try {
            List<Faculty> facultyList = facultyService.all();
            for (Faculty faculty : facultyList) {
                facultyService.update(faculty);
            }
            return "redirect:/allFaculty";
        } catch (Exception e) {
            return "index";
        }
    }


//    @PostMapping("/assign/{id}")
//    private String assign(Model model, Long[] arr, @PathVariable Long id, Integer roomNumber, String fromTime, String toTime, String date) {
//        try {
//            Faculty faculty = facultyService.getById(id);
//            String phoneNumber = String.valueOf(faculty.getPhoneNumber());
//            String apiUrl = "https://www.fast2sms.com/dev/bulkV2";
//            String apiKey = "8b295mfuL31JwNxVoPegHzqsCGvDQc4kFUA6IlBSZTYMROatErHfCn3eV0pWBQOSbl9c8RYdPjG5uFNx";
//            String message = "Invigilation assigned for you";
//            List<Integer> rooms = new ArrayList<>(30);
//            Random random = new Random(System.currentTimeMillis());
//            List<TimeTable> tableList = tableService.all();
//            boolean checkDayAndTime = tableService.getByDateAndFromTimeAndToTime(date, fromTime, toTime) != null;
//            TimeTable timeTable = tableList.get(random.nextInt(rooms.size()));
//            TimeTable table = TimeTable.builder()
//                    .facultyName(faculty.getName())
//                    .roomNumber(timeTable.getRoomNumber())
//                    .date(date)
//                    .fromTime(fromTime)
//                    .toTime(toTime)
//                    .build();
//            if (checkDayAndTime) {
//                model.addAttribute("success", "Already exist in this time, please try another slot");
//                return "hod/assign";
//            } else {
//                tableService.add(table);
//                Unirest.post(apiUrl)
//                        .header("authorization", apiKey)
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .body("message=" + message + "&route=q&numbers= " + phoneNumber)
//                        .asString();
//                model.addAttribute("success", "Assigned successfully");
//                return "redirect:/allFaculty";
//            }
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "hod/assign";
//        }
//    }
}
