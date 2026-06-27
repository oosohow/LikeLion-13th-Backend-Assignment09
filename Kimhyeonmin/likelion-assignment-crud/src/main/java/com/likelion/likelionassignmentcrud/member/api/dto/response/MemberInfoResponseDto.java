//5
package com.likelion.likelionassignmentcrud.member.api.dto.response;

import com.likelion.likelionassignmentcrud.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponseDto(
        Long memberId,
        String name,
        int goalCalories
) {
    public static MemberInfoResponseDto from(Member member){
        return MemberInfoResponseDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .goalCalories(member.getGoalCalories())
                .build();
    }
}
