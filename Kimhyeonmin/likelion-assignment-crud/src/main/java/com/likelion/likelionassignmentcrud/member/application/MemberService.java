//7
//5. MemberController의 전체 조회 메서드 파라미터에 @PageableDefault와 Pageable 객체를 추가
//반환 타입을 Page<MemberListResponseDto>처럼 Page 객체로 Service 로직도 그에 맞춰 Page를 반환하도록 수정
package com.likelion.likelionassignmentcrud.member.application;

import com.likelion.likelionassignmentcrud.member.api.dto.request.MemberSaveRequestDto;
import com.likelion.likelionassignmentcrud.member.api.dto.request.MemberUpdateRequestDto;
import com.likelion.likelionassignmentcrud.member.api.dto.response.MemberInfoResponseDto;
import com.likelion.likelionassignmentcrud.member.domain.Member;
import com.likelion.likelionassignmentcrud.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void memberSave(MemberSaveRequestDto memberSaveRequestDto){
        Member member = Member.builder()
                .name(memberSaveRequestDto.name())
                .goalCalories(memberSaveRequestDto.goalCalories())
                .build();
        memberRepository.save(member);
    }

    public MemberInfoResponseDto memberFindOne(Long memberId){
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(IllegalArgumentException::new);
        return MemberInfoResponseDto.from(member);
    }

    public Page<MemberInfoResponseDto> memberFindAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberInfoResponseDto::from);
    }

//    public MemberListResponseDto memberFindAll(){
//        List<Member> members = memberRepository.findAll();
//        List<MemberInfoResponseDto> memberInfoResponseDtoList = members.stream()
//                .map(MemberInfoResponseDto::from)
//                .toList();
//        return MemberListResponseDto.from(memberInfoResponseDtoList);
//    }

    @Transactional
    public void memberUpdate(Long memberId, MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.update(memberUpdateRequestDto);
    }
    @Transactional
    public void memberDelete(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        memberRepository.delete(member);
    }
}
