//3. 비즈니스 로직 중 발생하는 에러를 처리할 커스텀 예외 클래스
package com.likelion.likelionassignmentcrud.common.exception;

import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String customMessage;

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }
}
