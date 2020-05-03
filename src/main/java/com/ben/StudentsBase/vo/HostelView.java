package com.ben.StudentsBase.vo;

import com.ben.StudentsBase.model.Hostel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostelView {
    private Long id;
    private String street;
    private String building;
    private String campus;

    public static HostelView fromModel(Hostel hostel) {
        return new HostelView(
                hostel.getId(),
                hostel.getStreet(),
                hostel.getBuilding(),
                hostel.getCampus());
    }

    public static Hostel toModel(HostelView hostelView) {
        return new Hostel(
                hostelView.getId(),
                hostelView.getStreet(),
                hostelView.getBuilding(),
                hostelView.getCampus(),
                new HashSet<>());
    }
}
