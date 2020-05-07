package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.service.HostelService;
import com.ben.StudentsBase.vo.HostelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class HostelController {

    final private HostelService hostelService;

    @Autowired
    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    //READ
    @GetMapping("/hostels")
    List<HostelView> getAllHostels() {
        return hostelService.findAllViews();
    }

    //READ
    @GetMapping("/hostels/{hostelId}")
    HostelView getHostelById(@PathVariable Long hostelId) {
        return hostelService.findViewById(hostelId);
    }

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hostels")
    Hostel createHostel(@Valid @RequestBody HostelView hostelView) {
        return hostelService.saveView(hostelView);
    }

    //UPDATE
    @PutMapping("/hostels/{hostelId}")
    Hostel updateHostel(@PathVariable Long hostelId, @Valid @RequestBody HostelView hostelView) {
        return hostelService.updateView(hostelId, hostelView);
    }

    //DELETE
    @DeleteMapping("/hostels/{hostelId}")
    void deleteHostel(@PathVariable Long hostelId) {
        hostelService.deleteById(hostelId);
    }

}
