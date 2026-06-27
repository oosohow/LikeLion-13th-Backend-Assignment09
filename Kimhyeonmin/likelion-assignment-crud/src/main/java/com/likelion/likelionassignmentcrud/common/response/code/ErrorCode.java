//2.
package com.likelion.likelionassignmentcrud.common.response.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    MEMBER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 사용자가 없습니다. memberId = "),
    MEAL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 식단 기록이 없습니다. mealId = "),

    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "유효성 검사에 실패했습니다. "),
    //goalCalories(Integer 타입)에 숫자 10을 보냈을 때, 숫자 타입은 맞지만 @Min(500) 조건을 위반했으므로, @Valid가 이를 잡아내어 이 에러가 발생
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    //사용자가 아예 서버가 읽을 수조차 없는 잘못된 형태의 데이터를 보냈을 때 발생
    //(타입 불일치): goalCalories는 숫자를 받아야 하는데, 사용자가 오백이라는 문자열을 보냈을 때
    //(JSON 문법 에러): 클라이언트가 쉼표나 괄호를 빼먹고 JSON 데이터를 보냈을 때

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다");

private final HttpStatus httpStatus;
private final String message;

public int getHttpStatusCode() {
    return httpStatus.value();
}
}
