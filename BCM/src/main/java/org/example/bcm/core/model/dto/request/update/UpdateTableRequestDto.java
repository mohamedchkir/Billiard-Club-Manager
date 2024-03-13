package org.example.bcm.core.model.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.bcm.shared.Enum.TableType;

@Data
@Builder
public class UpdateTableRequestDto {
    @NotNull(message = "Id is required for update")
    private Long id;

    @NotNull(message = "Table type is required")
    private TableType tableType;

    @NotNull(message = "Tokens needed is required")
    private Integer tokensNeeded;

    @NotNull(message = "Club ID is required")
    private Long clubId;
}
