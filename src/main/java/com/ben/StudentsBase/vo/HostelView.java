package com.ben.StudentsBase.vo;

import com.ben.StudentsBase.model.Hostel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostelView {
    private Long id;

    @NotBlank(message = "Please provide a street")
    private String street;

    @NotBlank(message = "Please provide a building")
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
