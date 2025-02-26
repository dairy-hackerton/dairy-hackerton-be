package dairy.dairybe.repository;

import dairy.dairybe.entity.DiarySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiarySummaryRepository extends JpaRepository<DiarySummary, Long> {

    @Query("SELECT d FROM DiarySummary d WHERE d.year = :year AND d.month = :month AND d.date = :date")
    Optional<DiarySummary> findSummaryByYearMonthDate(
            @Param("year") String year,
            @Param("month") int month,
            @Param("date") int date);
}
