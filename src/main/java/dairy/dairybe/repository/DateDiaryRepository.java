package dairy.dairybe.repository;

import dairy.dairybe.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateDiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByYearAndMonth(String year, int month);
}
