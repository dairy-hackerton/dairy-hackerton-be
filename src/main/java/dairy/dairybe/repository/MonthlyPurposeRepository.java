package dairy.dairybe.repository;

import dairy.dairybe.entity.MonthlyPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyPurposeRepository extends JpaRepository<MonthlyPurpose, Long> {
    Optional<MonthlyPurpose> findByyearAndMonth(String year, int month);
}
