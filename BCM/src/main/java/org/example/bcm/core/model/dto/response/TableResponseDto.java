package org.example.bcm.core.model.dto.response;

import lombok.*;
import org.example.bcm.shared.Enum.TableType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDto {
    private Long id;
    private TableType tableType;
    private Integer tokensNeeded;
}
