package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.service.CourseService;
import com.ben.StudentsBase.service.HostelService;
import com.ben.StudentsBase.vo.CourseView;
import com.ben.StudentsBase.vo.HostelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @GetMapping("/hostels")
    List<HostelView> getAllHostels() {
        return hostelService.findAllViews();
    }

    @GetMapping("/hostels/{hostelId}")
    HostelView getHostelById(@PathVariable Long hostelId) {
        return hostelService.findViewById(hostelId).get();
    }

    @PostMapping("/hostels")
    void addCourse(@RequestBody HostelView hostelView) {
        hostelService.saveView(hostelView);
    }
}
