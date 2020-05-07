package com.ben.StudentsBase.service;

import com.ben.StudentsBase.exception.RecordNotFoundException;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.repository.HostelRepository;
import com.ben.StudentsBase.vo.HostelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hostelService")
public class HostelService extends AbstractService<HostelRepository, Hostel, HostelView> {
    @Autowired
    public HostelService(HostelRepository hostelRepository) {
        super(hostelRepository, HostelView::fromModel, HostelView::toModel);
    }

    public Hostel updateView(Long hostelId, HostelView hostelView) {

        Hostel hostel = getRepository().findById(hostelId)
                .orElseThrow(() -> new RecordNotFoundException(hostelId));

        hostel.setStreet(hostelView.getStreet());
        hostel.setBuilding(hostelView.getBuilding());
        hostel.setCampus(hostelView.getCampus());
        return getRepository().save(hostel);
    }
}
