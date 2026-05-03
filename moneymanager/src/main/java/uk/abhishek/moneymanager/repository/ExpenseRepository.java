package uk.abhishek.moneymanager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.abhishek.moneymanager.entity.ExpenseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

    List<ExpenseEntity>findByProfileIdOrderByDateDesc(Long profileId);

  List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

  @Query("Select SUM(e.amount) FROM ExpenseEntity e where e.profile.id =:profileId")
  BigDecimal getTotalExpenseByProfileId(@Param("profileId") Long profileId);
List<ExpenseEntity>findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String keyword , Sort sort);

List<ExpenseEntity>findByProfileIdAndDateBetween(Long profileId,LocalDate startDate,LocalDate endDate);

List<ExpenseEntity>findByProfileIdAndDate(Long profileId,LocalDate date);
}

