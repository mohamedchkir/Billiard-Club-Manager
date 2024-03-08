package org.example.bcm.core.service.impl;

import org.example.bcm.core.model.dto.request.TableRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateTableRequestDto;
import org.example.bcm.core.model.dto.response.TableResponseDto;
import org.example.bcm.core.service.TableService;

import java.util.List;

public class TableServiceImpl implements TableService {
    @Override
    public TableResponseDto createTable(TableRequestDto tableRequestDto) {
        return null;
    }

    @Override
    public TableResponseDto getTableById(Long tableId) {
        return null;
    }

    @Override
    public List<TableResponseDto> getAllTables() {
        return null;
    }

    @Override
    public TableResponseDto updateTable(UpdateTableRequestDto updateTableRequestDto) {
        return null;
    }

    @Override
    public void deleteTable(Long tableId) {

    }
}
