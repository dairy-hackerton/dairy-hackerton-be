package dairy.dairybe.repository;

import dairy.dairybe.entity.DiaryResult;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryResultRepository extends JpaRepository<DiaryResult, Long> {

    @Query("SELECT d FROM DiaryResult d WHERE d.year = :year AND d.month = :month AND d.date = :date")
    Optional<DiaryResult> findByYearMonthDate(
            @Param("year") int year,
            @Param("month") int month,
            @Param("date") int date);

    @Query("SELECT d FROM DiaryResult d WHERE d.year = :year AND d.month = :month")
    List<DiaryResult> findAllByYearMonth(
            @Param("year") int year,
            @Param("month") int month
    );

    void deleteByYearAndMonthAndDate(int year, int month, int date);
}
