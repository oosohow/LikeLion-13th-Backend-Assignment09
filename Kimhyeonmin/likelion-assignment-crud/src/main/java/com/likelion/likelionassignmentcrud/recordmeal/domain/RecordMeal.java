package com.likelion.likelionassignmentcrud.recordmeal.domain;

import com.likelion.likelionassignmentcrud.member.domain.Member;
import com.likelion.likelionassignmentcrud.recordmeal.api.dto.request.RecordMealUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordmeal_id")

    private Long recordMealId;
    private String mealType;
    private String menuName;
    private String calories;

    //N:1 관계, 식단 기록들은 한명의 회원만
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public RecordMeal(String mealType, String menuName, String calories, Member member){
        this.mealType = mealType;
        this.menuName = menuName;
        this.calories = calories;
        this.member = member;
    }

    public void update(RecordMealUpdateRequestDto recordMealUpdateRequestDto){
        this.mealType = recordMealUpdateRequestDto.mealType();
        this.menuName = recordMealUpdateRequestDto.menuName();
    }
}