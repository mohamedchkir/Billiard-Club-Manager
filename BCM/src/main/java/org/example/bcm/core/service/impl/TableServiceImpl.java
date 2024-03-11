package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.core.model.mapper.TableMapper;
import org.example.bcm.core.model.dto.request.TableRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateTableRequestDto;
import org.example.bcm.core.model.dto.response.TableResponseDto;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.repository.ClubRepository;
import org.example.bcm.core.repository.TableRepository;
import org.example.bcm.core.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final ClubRepository clubRepository;

    @Override
    public TableResponseDto createTable(TableRequestDto tableRequestDto) {
        Club club = clubRepository.findById(tableRequestDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", tableRequestDto.getClubId()));

        Table table = TableMapper.toEntity(tableRequestDto, club);
        Table savedTable = tableRepository.save(table);
        return TableMapper.toDto(savedTable);
    }

    @Override
    public TableResponseDto getTableById(Long tableId) {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", tableId));
        return TableMapper.toDto(table);
    }

    @Override
    public List<TableResponseDto> getAllTables() {
        List<Table> tables = tableRepository.findAll();
        return tables.stream()
                .map(TableMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TableResponseDto updateTable(UpdateTableRequestDto updateTableRequestDto) {
        Table existingTable = tableRepository.findById(updateTableRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", updateTableRequestDto.getId()));

        Club club = clubRepository.findById(updateTableRequestDto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", updateTableRequestDto.getClubId()));

        TableMapper.updateEntity(existingTable, updateTableRequestDto, club);

        Table updatedTable = tableRepository.save(existingTable);
        return TableMapper.toDto(updatedTable);
    }

    @Override
    public void deleteTable(Long tableId) {
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", tableId));
        tableRepository.delete(table);
    }
}
