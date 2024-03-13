package org.example.bcm.shared.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TableType {
    RUSSIAN_BILLIARDS("Russian Billiards"),
    POOL("Pool"),
    ENGLISH_POOL("English Pool"),
    SNOOKER("Snooker"),
    CARAMBOLE("Carambole");

    private final String displayName;

}
