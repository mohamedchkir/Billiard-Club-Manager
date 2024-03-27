package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.WaitingListRequestDto;
import org.example.bcm.core.model.dto.response.WaitingListResponseDto;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.model.entity.WaitingList;

public class WaitingListMapper {

    public static WaitingList toEntity(WaitingListRequestDto waitingListRequestDto , User user, Table table) {
        WaitingList waitingList = new WaitingList();
        waitingList.setReservationDate(waitingListRequestDto.getReservationDate());
        waitingList.setGuestName(waitingListRequestDto.getGuestName());
        waitingList.setUser(user);
        waitingList.setTable(table);
        return waitingList;
    }

    public static WaitingListResponseDto toDto(WaitingList waitingList) {
        return WaitingListResponseDto.builder()
                .id(waitingList.getId())
                .reservationDate(waitingList.getReservationDate().toString())
                .guestName(waitingList.getGuestName())
                .user(UserMapper.toDto(waitingList.getUser()))
                .table(TableMapper.toDto(waitingList.getTable()))
                .build();
    }

    public static void updateEntity(WaitingList existingWaitingList, WaitingListRequestDto waitingListRequestDto, User user, Table table) {
        existingWaitingList.setReservationDate(waitingListRequestDto.getReservationDate());
        existingWaitingList.setGuestName(waitingListRequestDto.getGuestName());
        existingWaitingList.setUser(user);
        existingWaitingList.setTable(table);
    }
}
