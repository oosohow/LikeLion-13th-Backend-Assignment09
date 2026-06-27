//3
package com.likelion.likelionassignmentcrud.recordmeal.domain.repository;

import com.likelion.likelionassignmentcrud.recordmeal.domain.RecordMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordMealRepository extends JpaRepository<RecordMeal, Long> {
    List<RecordMeal> findByMemberMemberId(Long memberId);
}
