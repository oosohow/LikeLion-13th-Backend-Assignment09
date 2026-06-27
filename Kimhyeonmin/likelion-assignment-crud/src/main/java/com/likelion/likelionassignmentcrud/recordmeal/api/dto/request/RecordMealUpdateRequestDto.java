//2
//4. 입력 검증
package com.likelion.likelionassignmentcrud.recordmeal.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecordMealUpdateRequestDto(
        @NotBlank(message = "수정할 식사 종류를 필수로 입력해야 합니다. ")
        @Size(min = 2, max = 50, message = "수정할 식사 종류를 2자 이상, 50자 이하로 입력해야 합니다. ")
        String mealType,

        @NotBlank(message = "수정할 식사 메뉴를 필수로 입력해야 합니다. ")
        @Size(min = 2, max = 50, message = "수정할 식사 메뉴를 2자 이상, 50자 이하로 입력해야 합니다. ")
        String menuName
) {
}
