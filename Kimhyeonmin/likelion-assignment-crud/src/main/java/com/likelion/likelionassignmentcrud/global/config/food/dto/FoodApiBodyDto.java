package com.likelion.likelionassignmentcrud.global.config.food.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodApiBodyDto {
    private List<FoodApiResponseDto> items;
}
