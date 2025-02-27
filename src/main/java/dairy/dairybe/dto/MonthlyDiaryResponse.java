package dairy.dairybe.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyDiaryResponse {
    private final int year;
    private final int month;
    private final String message;
    private final List<String> monthlyPurpose;
    private final List<DateDiaryResponse> dateDiarySentence;

    /**
     * 📌 생성자: 월별 목표 및 날짜별 요약된 일기 포함
     */
    public MonthlyDiaryResponse(int year, int month, String message,
                                List<String> monthlyPurpose, List<DateDiaryResponse> dateDiarySentence) {
        this.year = year;
        this.month = month;
        this.message = message;
        this.monthlyPurpose = monthlyPurpose;
        this.dateDiarySentence = dateDiarySentence;
    }
}
