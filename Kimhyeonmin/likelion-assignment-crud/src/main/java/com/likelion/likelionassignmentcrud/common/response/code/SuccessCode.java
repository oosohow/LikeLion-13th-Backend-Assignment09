//2.
package com.likelion.likelionassignmentcrud.common.response.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    //Member 성공 코드
    MEMBER_SAVE_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
    MEMBER_GET_SUCCESS(HttpStatus.OK, "회원 조회에 성공했습니다."),
    MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보 수정에 성공했습니다."),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "회원 삭제에 성공했습니다."),

    //RecordMeal 성공 코드
    MEAL_SAVE_SUCCESS(HttpStatus.CREATED, "식단 기록에 성공했습니다."),
    MEAL_GET_SUCCESS(HttpStatus.OK, "식단 기록 조회에 성공했습니다."),
    MEAL_UPDATE_SUCCESS(HttpStatus.OK, "식단 기록 수정에 성공했습니다."),
    MEAL_DELETE_SUCCESS(HttpStatus.OK, "식단 기록 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}