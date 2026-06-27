//3
package com.likelion.likelionassignmentcrud.member.domain.repository;

import com.likelion.likelionassignmentcrud.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
