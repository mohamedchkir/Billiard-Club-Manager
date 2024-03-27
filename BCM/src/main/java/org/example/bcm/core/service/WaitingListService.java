package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.WaitingListRequestDto;
import org.example.bcm.core.model.dto.response.WaitingListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WaitingListService {

    public WaitingListResponseDto joinWaitingList(WaitingListRequestDto waitingListRequestDto);
    public List<WaitingListResponseDto> getAllInWaitingListSortedByReservationDate();
}
