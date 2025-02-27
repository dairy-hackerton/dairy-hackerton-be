package dairy.dairybe.repository;

import dairy.dairybe.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DateDiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByYearAndMonth(String year, int month);

    Optional<Diary> findByYearAndMonthAndDay(String year, int month, int day);

    @Transactional
    void deleteByYearAndMonthAndDay(String year, int month, int day);
}
