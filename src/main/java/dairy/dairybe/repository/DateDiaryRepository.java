package dairy.dairybe.repository;

import dairy.dairybe.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateDiaryRepository extends JpaRepository<Diary, Long> {
}
