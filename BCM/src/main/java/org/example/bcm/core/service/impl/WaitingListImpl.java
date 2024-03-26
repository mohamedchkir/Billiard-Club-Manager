package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.TokenNotEnoughException;
import org.example.bcm.core.model.dto.request.WaitingListRequestDto;
import org.example.bcm.core.model.dto.response.WaitingListResponseDto;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.model.entity.WaitingList;
import org.example.bcm.core.model.mapper.WaitingListMapper;
import org.example.bcm.core.repository.TableRepository;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.repository.WaitingListRepository;
import org.example.bcm.core.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class WaitingListImpl implements WaitingListService {
    private final WaitingListRepository waitingListRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;

    @Override
    public WaitingListResponseDto joinWaitingList(WaitingListRequestDto waitingListRequestDto) {
        Table table = tableRepository.findById(waitingListRequestDto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", waitingListRequestDto.getTableId()));


        User user = userRepository.findById(waitingListRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", waitingListRequestDto.getUserId()));

        if (user.getNumberOfToken() < table.getTokensNeeded()) {
            throw new TokenNotEnoughException("You don't have enough token to join the table");
        }

        waitingListRequestDto.setReservationDate(LocalDateTime.now());

        WaitingList waitingList = WaitingListMapper.toEntity(waitingListRequestDto, user, table);
        WaitingList savedWaitingList = waitingListRepository.save(waitingList);
        return WaitingListMapper.toDto(savedWaitingList);

    }

    @Override
    public List<WaitingListResponseDto> getAllInWaitingListSortedByReservationDate() {
       List<WaitingList> waitingList = waitingListRepository.findAllByOrderByReservationDateAsc();

       if (waitingList.isEmpty()) {
           throw new ResourceNotFoundException("Waiting List", "empty", "empty");
       }
       return waitingList.stream().map(WaitingListMapper::toDto).collect(Collectors.toList());

    }
}
