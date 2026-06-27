package com.likelion.likelionassignmentcrud.global.client.food;

import com.likelion.likelionassignmentcrud.common.exception.BusinessException;
import com.likelion.likelionassignmentcrud.common.response.code.ErrorCode;
import com.likelion.likelionassignmentcrud.global.config.food.dto.FoodApiResponseDto;
import com.likelion.likelionassignmentcrud.global.config.food.dto.FoodApiWrapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class FoodApiClient {

    private final RestClient restClient;

    @Value("${public-api.food.url}")
    private String apiUrl;

    @Value("${public-api.food.key}")
    private String apiKey;

    public FoodApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<FoodApiResponseDto> getFoodNutrition(String foodName) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("FOOD_NM_KR", foodName)
                    .queryParam("type", "json")
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "10")
                    .build(false)
                    .encode()
                    .toUri();

            log.debug("식약처 API 호출 URI: {}", uri);

            FoodApiWrapperDto wrapper = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, res) -> {
                        log.error("식약처 API 오류 발생: 상태 코드 {}", res.getStatusCode());
                        throw new BusinessException(
                                ErrorCode.INTERNAL_SERVER_ERROR,
                                "식약처 API 호출 중 에러가 발생함");
                    })
                    .body(FoodApiWrapperDto.class);

            if (wrapper == null
                    || wrapper.getBody() == null
                    || wrapper.getBody().getItems() == null
                    || wrapper.getBody().getItems().isEmpty()) {

                log.warn("식약처 API에서 찾은 음식 데이터가 없습니다. (입력값: {})", foodName);
                throw new BusinessException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "요청한 음식의 영양 정보를 찾을 수 없음");
            }

            return wrapper.getBody().getItems();

        } catch (ResourceAccessException e) {
            log.error("식약처 서버 자체가 응답이 없습니다 (연결 실패): {}", e.getMessage());
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "공공 API 서버와 연결할 수 없음");

        } catch (BusinessException e) {
            throw e;

        } catch (Exception e) {
            log.error("식약처 API 통신 중 예기치 못한 오류: {}", e.getMessage());
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "서버 내부에서 알 수 없는 통신 에러가 발생함");
        }
    }
}