package com.likelion.likelionassignmentcrud.recordmeal.api.dto.response;

import com.likelion.likelionassignmentcrud.global.config.food.dto.FoodApiResponseDto;
import lombok.Builder;

@Builder
public record FoodNutritionDto(
        String foodName,       //식품 이름
        String calories,       //열량 (kcal)
        String carbohydrates,  //탄수화물 (g)
        String protein,        //단백질 (g)
        String fat             //지방 (g)
) {
    // 공공 API 응답 객체를 받아서 직관적인 우리 DTO로 변환해주는 메서드
    public static FoodNutritionDto from(FoodApiResponseDto apiResponse) {
        return FoodNutritionDto.builder()
                .foodName(apiResponse.getFoodNmKr())
                .calories(apiResponse.getAmtNum1())
                .carbohydrates(apiResponse.getAmtNum3())
                .protein(apiResponse.getAmtNum4())
                .fat(apiResponse.getAmtNum5())
                .build();
    }
}