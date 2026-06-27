//1
package com.likelion.likelionassignmentcrud.member.domain;

import com.likelion.likelionassignmentcrud.member.api.dto.request.MemberUpdateRequestDto;
import com.likelion.likelionassignmentcrud.recordmeal.domain.RecordMeal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA를 위한 기본 생성자
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")

    private Long memberId;
    private String name;
    private int goalCalories;

    //1:N 관계, 회원 한명이 여러번의 식단을 기록
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordMeal> recordMeals = new ArrayList<>();

    @Builder
    public Member(String name, int goalCalories){
        this.name = name;
        this.goalCalories = goalCalories;
    }

    public void update(MemberUpdateRequestDto memberUPdateRequestDto){
        this.name = memberUPdateRequestDto.name();
        this.goalCalories = memberUPdateRequestDto.goalCalories();
    }

}
