package com.likelion.likelionassignmentcrud.recordmeal.api.dto.response;

import com.likelion.likelionassignmentcrud.recordmeal.domain.RecordMeal;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordMealInfoResponseDto(
        Long memberId,
        Long recordMealId,
        String mealType,
        String menuName,

        List<FoodNutritionDto> nutritionInfo // 💡 타입을 새로운 FoodNutritionDto로 변경!
) {
    public static RecordMealInfoResponseDto from(RecordMeal recordMeal, List<FoodNutritionDto> nutritionInfo) {
        return RecordMealInfoResponseDto.builder()
                .memberId(recordMeal.getMember().getMemberId())
                .recordMealId(recordMeal.getRecordMealId())
                .mealType(recordMeal.getMealType())
                .menuName(recordMeal.getMenuName())

                .nutritionInfo(nutritionInfo)
                .build();
    }
}