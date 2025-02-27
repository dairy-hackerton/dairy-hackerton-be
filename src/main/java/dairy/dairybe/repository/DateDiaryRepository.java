package dairy.dairybe.repository;

import dairy.dairybe.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DateDiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByYearAndMonth(int year, int month);

    Optional<Diary> findByYearAndMonthAndDay(int year, int month, int day);

    @Transactional
    void deleteByYearAndMonthAndDay(int year, int month, int day);
}
