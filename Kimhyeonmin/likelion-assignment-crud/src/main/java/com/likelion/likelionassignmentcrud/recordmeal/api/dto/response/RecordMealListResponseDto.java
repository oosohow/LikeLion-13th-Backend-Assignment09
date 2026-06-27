//11
package com.likelion.likelionassignmentcrud.recordmeal.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordMealListResponseDto(
        List<RecordMealInfoResponseDto> recordMeals
) {
    public static RecordMealListResponseDto from(List<RecordMealInfoResponseDto> recordMeals) {
        return RecordMealListResponseDto.builder()
                .recordMeals(recordMeals)
                .build();
    }
}
