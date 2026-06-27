//1
//4. 입력 검증
package com.likelion.likelionassignmentcrud.member.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberSaveRequestDto(
        @NotBlank(message = "이름을 필수로 입력해야 합니다. ")
        @Size(min = 2, max = 10, message = "이름은 2자 이상, 10자 이하로 입력해야 합니다. ")
        String name,

        @NotNull(message = "목표 칼로리를 필수로 입력해야 합니다. ")
        @Min(value = 500, message = "목표 칼로리는 500칼로리 이상으로 입력해야 합니다. ")
        int goalCalories
) {
}
