package com.likelion.likelionassignmentcrud.recordmeal.application;

import com.likelion.likelionassignmentcrud.global.config.food.dto.FoodApiResponseDto;
import com.likelion.likelionassignmentcrud.member.domain.Member;
import com.likelion.likelionassignmentcrud.member.domain.repository.MemberRepository;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.request.RecordMealSaveRequestDto;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.request.RecordMealUpdateRequestDto;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.response.FoodNutritionDto;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.response.RecordMealInfoResponseDto;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.response.RecordMealListResponseDto;
import com.likelion.likelionassignmentcrud.recordmeal.domain.RecordMeal;
import com.likelion.likelionassignmentcrud.recordmeal.domain.repository.RecordMealRepository;

//통신 클라이언트 import
import com.likelion.likelionassignmentcrud.global.client.food.FoodApiClient;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordMealService {
    private final RecordMealRepository recordMealRepository;
    private final MemberRepository memberRepository;

    //주입?받기
    private final FoodApiClient foodApiClient;

    @Transactional
    public void recordMealSave(RecordMealSaveRequestDto recordMealSaveRequestDto){
        Member member = memberRepository.findById(recordMealSaveRequestDto.memberId()).orElseThrow(IllegalArgumentException::new);

        //식단 저장 전 공공 API 통신
        // 사용자가 입력한 menuName으로 식약처 데이터 조회
        List<FoodApiResponseDto> foodNutritionList = foodApiClient.getFoodNutrition(recordMealSaveRequestDto.menuName());

        String fetchedCalories = null;

        //영양 정보 활용 -> 여기서 꺼내서 사용
        if (foodNutritionList != null && !foodNutritionList.isEmpty()) {
            FoodApiResponseDto nutrition = foodNutritionList.get(0);
            //System.out.println("조회된 칼로리: " + nutrition.getNUTR_CONT1() + " kcal");
            fetchedCalories = nutrition.getAmtNum1();
        }

        RecordMeal recordMeal = RecordMeal.builder()
                .mealType(recordMealSaveRequestDto.mealType())
                .menuName(recordMealSaveRequestDto.menuName())
                .calories(fetchedCalories)
                .member(member)
                .build();

        recordMealRepository.save(recordMeal);
    }

//    public RecordMealListResponseDto recordMealFindMember(Long memberId){
//        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
//
//        List<RecordMeal> recordMeals = recordMealRepository.findByMemberMemberId(memberId);
//        List<RecordMealInfoResponseDto> recordMealInfoResponseDtos = recordMeals.stream()
//                .map(RecordMealInfoResponseDto::from)
//                .toList();
//
//        return RecordMealListResponseDto.from(recordMealInfoResponseDtos);
//    }

    public RecordMealListResponseDto recordMealFindMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        List<RecordMeal> recordMeals = recordMealRepository.findByMemberMemberId(memberId);
        List<RecordMealInfoResponseDto> recordMealInfoResponseDtos = recordMeals.stream()
                .map(recordMeal -> {
                    //DB에 저장된 메뉴 이름으로 공공 API 불러오기
                    List<FoodNutritionDto> nutritionInfo = null;

                    //검색된 리스트 모두 가져옴
                    List<FoodApiResponseDto> searchedList = foodApiClient.getFoodNutrition(recordMeal.getMenuName());

                    //첫번째 데이터만 가져옥
                    if (searchedList != null && !searchedList.isEmpty()) {
                        FoodApiResponseDto apiDto = searchedList.get(0);
                        nutritionInfo = List.of(FoodNutritionDto.from(apiDto));
                    }

                    return RecordMealInfoResponseDto.from(recordMeal, nutritionInfo);
                })
                .toList();

        return RecordMealListResponseDto.from(recordMealInfoResponseDtos);
    }

    @Transactional
    public void recordMealUpdate(Long recordMealId, RecordMealUpdateRequestDto recordMealUpdateRequestDto) {
        RecordMeal recordMeal = recordMealRepository.findById(recordMealId)
                .orElseThrow(IllegalArgumentException::new);

        recordMeal.update(recordMealUpdateRequestDto);
    }

    @Transactional
    public void recordMealDelete(Long recordMealId){
        RecordMeal recordMeal = recordMealRepository.findById(recordMealId)
                .orElseThrow(IllegalArgumentException::new);

        recordMealRepository.delete(recordMeal);
    }
}