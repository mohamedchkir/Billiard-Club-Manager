package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.TableRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateTableRequestDto;
import org.example.bcm.core.model.dto.response.TableResponseDto;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.Club;

public class TableMapper {

    public static Table toEntity(TableRequestDto tableRequestDto, Club club) {
        Table table = new Table();
        table.setTableType(tableRequestDto.getTableType());
        table.setTokensNeeded(tableRequestDto.getTokensNeeded());
        table.setClub(club);
        return table;
    }

    public static TableResponseDto toDto(Table table) {
        TableResponseDto dto = new TableResponseDto();
        dto.setId(table.getId());
        dto.setTableType(table.getTableType());
        dto.setTokensNeeded(table.getTokensNeeded());
        dto.setClub(ClubMapper.toDto(table.getClub()));

        return dto;
    }

    public static void updateEntity(Table existingTable, UpdateTableRequestDto updateTableRequestDto, Club club) {
        existingTable.setTableType(updateTableRequestDto.getTableType());
        existingTable.setTokensNeeded(updateTableRequestDto.getTokensNeeded());
        existingTable.setClub(club);
    }
}
