package com.likelion.likelionassignmentcrud.global.config.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodApiResponseDto {

    @JsonProperty("FOOD_NM_KR")
    private String foodNmKr;  //음식명

    @JsonProperty("AMT_NUM1")
    private String amtNum1;    //열량

    @JsonProperty("AMT_NUM3")
    private String amtNum3;    //탄수화물

    @JsonProperty("AMT_NUM4")
    private String amtNum4;    //단백질

    @JsonProperty("AMT_NUM5")
    private String amtNum5;    //지방
}