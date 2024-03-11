package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.bcm.core.model.dto.request.TableRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateTableRequestDto;
import org.example.bcm.core.model.dto.response.TableResponseDto;
import org.example.bcm.core.service.TableService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppEndpoint.TABLE_ENDPOINT)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TableController {
    private final TableService tableService;

    @PostMapping
    public ResponseEntity<TableResponseDto> createTable(@Valid @RequestBody TableRequestDto tableRequestDto) {
        TableResponseDto createdTable = tableService.createTable(tableRequestDto);
        return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<TableResponseDto> getTableById(@PathVariable Long tableId) {
        TableResponseDto table = tableService.getTableById(tableId);
        return ResponseEntity.ok(table);
    }

    @GetMapping
    public ResponseEntity<List<TableResponseDto>> getAllTables() {
        List<TableResponseDto> tables = tableService.getAllTables();
        return ResponseEntity.ok(tables);
    }

    @PutMapping
    public ResponseEntity<TableResponseDto> updateTable(@Valid @RequestBody UpdateTableRequestDto updateTableRequestDto) {
        TableResponseDto updatedTable = tableService.updateTable(updateTableRequestDto);
        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.noContent().build();
    }
}
