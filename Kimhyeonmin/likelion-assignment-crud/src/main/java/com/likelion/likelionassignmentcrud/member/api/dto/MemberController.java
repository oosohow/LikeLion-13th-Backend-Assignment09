//8
//5. MemberController와 RecordMealController의 모든 메서드 반환 타입을 ResponseEntity<ApiResTemplate<...>> 형태로 변경
package com.likelion.likelionassignmentcrud.member.api.dto;

import com.likelion.likelionassignmentcrud.common.response.code.SuccessCode;
import com.likelion.likelionassignmentcrud.common.template.ApiResTemplate;
import com.likelion.likelionassignmentcrud.member.api.dto.request.MemberSaveRequestDto;
import com.likelion.likelionassignmentcrud.member.api.dto.request.MemberUpdateRequestDto;
import com.likelion.likelionassignmentcrud.member.api.dto.response.MemberInfoResponseDto;
import com.likelion.likelionassignmentcrud.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "멤버 API", description = "멤버 관리 api ")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "멤버 회원가입", description = "멤버 회원가입란")
    public ApiResTemplate<Void> memberSave(@RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto) {
        memberService.memberSave(memberSaveRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_SAVE_SUCCESS);
    }

//    public ResponseEntity<String> memberSave(@RequestBody MemberSaveRequestDto memberSaveRequestDto){
//        memberService.memberSave(memberSaveRequestDto);
//        return new ResponseEntity<>("사용자 등록", HttpStatus.CREATED);
//    }

    @GetMapping("/all")
    @Operation(summary = "멤버 전체 조회", description = "멤버 전체 조회")
    public ApiResTemplate<Page<MemberInfoResponseDto>> memberFindAll(
            @ParameterObject
            @PageableDefault(
                    size = 10,
                    sort = "memberId",
                    direction =Sort.Direction.ASC
            ) Pageable pageable
    ){
        Page<MemberInfoResponseDto> members = memberService.memberFindAll(pageable);
        return ApiResTemplate.successResponse(SuccessCode.MEMBER_GET_SUCCESS, members);
     }

//    public ResponseEntity<MemberListResponseDto> memberFindAll(){
//        MemberListResponseDto memberListResponseDto = memberService.memberFindAll();
//        return new ResponseEntity<>(memberListResponseDto, HttpStatus.OK);
//    }

    @GetMapping("/{memberId}")
    @Operation(summary = "멤버 1명 조회", description = "멤버 id로 멤버 조회")
    public ApiResTemplate<MemberInfoResponseDto> memberFindOne(@PathVariable("memberId") Long memberId) {
        MemberInfoResponseDto memberInfoResponseDto = memberService.memberFindOne(memberId);
        return ApiResTemplate.successResponse(SuccessCode.MEMBER_GET_SUCCESS, memberInfoResponseDto);
    }
//    public ResponseEntity<MemberInfoResponseDto> memberFindOne(@PathVariable("memberId") Long memberId) {
//        MemberInfoResponseDto memberInfoResponseDto = memberService.memberFindOne(memberId);
//        return new ResponseEntity<>(memberInfoResponseDto, HttpStatus.OK);
//    }

    @PatchMapping("/{memberId}")
    @Operation(summary = "멤버 업데이트", description = "멤버 업데이트")
    public ApiResTemplate<Void> memberUpdate(@PathVariable("memberId") Long memberId,
                                             @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.memberUpdate(memberId, memberUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_UPDATE_SUCCESS);
    }
//    public ResponseEntity<String> memberUpdate(@PathVariable("memberId") Long memberId, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto){
//        memberService.memberUpdate(memberId, memberUpdateRequestDto);
//        return new ResponseEntity<>("사용자 수정", HttpStatus.OK);
//    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "멤버 삭제", description = "멤버 삭제")
    public ApiResTemplate<Void> memberDelete(@PathVariable("memberId") Long memberId) {
        memberService.memberDelete(memberId);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_DELETE_SUCCESS);
    }
//    public ResponseEntity<String> memberDelete(@PathVariable("memberId") Long memberId){
//        memberService.memberDelete(memberId);
//        return new ResponseEntity<>("사용자 삭제", HttpStatus.OK);
//    }

}
