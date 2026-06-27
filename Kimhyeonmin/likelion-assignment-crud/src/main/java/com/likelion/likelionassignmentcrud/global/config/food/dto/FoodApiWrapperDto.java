package com.likelion.likelionassignmentcrud.global.config.food.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodApiWrapperDto {
    private FoodApiBodyDto body;
}
