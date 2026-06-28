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
    private final FoodApiClient foodApiClient;

    //트랜잭션 없이 외부 API 통신만 먼저
    public void recordMealSave(RecordMealSaveRequestDto recordMealSaveRequestDto) {
        //트랜잭션 시작 전에 외부 API 호출
        List<FoodApiResponseDto> foodNutritionList =
                foodApiClient.getFoodNutrition(recordMealSaveRequestDto.menuName());

        String fetchedCalories = null;
        if (foodNutritionList != null && !foodNutritionList.isEmpty()) {
            fetchedCalories = foodNutritionList.get(0).getAmtNum1();
        }

        saveRecordMeal(recordMealSaveRequestDto, fetchedCalories);
    }

    //DB 저장용 짧은 트랜잭션 메서드 분리
    @Transactional
    public void saveRecordMeal(RecordMealSaveRequestDto recordMealSaveRequestDto, String calories) {
        Member member = memberRepository.findById(recordMealSaveRequestDto.memberId())
                .orElseThrow(IllegalArgumentException::new);

        RecordMeal recordMeal = RecordMeal.builder()
                .mealType(recordMealSaveRequestDto.mealType())
                .menuName(recordMealSaveRequestDto.menuName())
                .calories(calories)
                .member(member)
                .build();

        recordMealRepository.save(recordMeal);
    }

    public RecordMealListResponseDto recordMealFindMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        List<RecordMeal> recordMeals = recordMealRepository.findByMemberMemberId(memberId);
        List<RecordMealInfoResponseDto> recordMealInfoResponseDtos = recordMeals.stream()
                .map(recordMeal -> {
                    List<FoodNutritionDto> nutritionInfo = null;
                    //API에서 못 찾는 음식이 있어도 에러 안 나게
                    try {
                        List<FoodApiResponseDto> searchedList = foodApiClient.getFoodNutrition(recordMeal.getMenuName());

                        if (searchedList != null && !searchedList.isEmpty()) {
                            FoodApiResponseDto apiDto = searchedList.get(0);
                            nutritionInfo = List.of(FoodNutritionDto.from(apiDto));
                        }
                    } catch (Exception e) {
                        // 검색 실패 시 조회를 멈추지 않고, 영양 정보만 비워둔채 통과
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