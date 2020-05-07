package com.ben.StudentsBase.repository;

import com.ben.StudentsBase.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("hostelRepository")
public interface HostelRepository extends JpaRepository<Hostel, Long> {

}
