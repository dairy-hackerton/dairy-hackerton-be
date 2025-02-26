package dairy.dairybe;

import dairy.dairybe.entity.DiarySummary;
import dairy.dairybe.repository.DiarySummaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class) // ✅ JUnit 5 방식으로 변경
public class DiaryServiceTest {

    @Autowired
    private DiarySummaryRepository diarySummaryRepository;

    @Test
    public void testFindDiarySummary() {
        String year = "2025";
        int month = 2;
        int date = 1;

        Optional<DiarySummary> summary = diarySummaryRepository.findByYearAndMonthAndDate(year, month, date);

        assertTrue(summary.isPresent()); // ✅ 데이터가 있어야 테스트 통과
        System.out.println("🔍 Retrieved summary: " + summary.get().getSummary());
    }
}
