package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.TableRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateTableRequestDto;
import org.example.bcm.core.model.dto.response.TableResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TableService {
    TableResponseDto createTable(TableRequestDto tableRequestDto);

    TableResponseDto getTableById(Long tableId);

    List<TableResponseDto> getAllTables();

    TableResponseDto updateTable(UpdateTableRequestDto updateTableRequestDto);

    void deleteTable(Long tableId);
}
