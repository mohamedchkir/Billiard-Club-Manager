package org.example.bcm.core.repository;

import org.example.bcm.core.model.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {

    List<WaitingList> findAllByOrderByReservationDateAsc();

}
