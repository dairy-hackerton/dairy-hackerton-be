package dairy.dairybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateDiaryResponse {
    private String year;  // ✅ LocalDate 대신 String year 사용
    private int month;
    private int date;
    private String summary;
    private String mood;
}