package dairy.dairybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MonthlyDiaryResponse {
    private String message;
    private List<String> monthlyPurpose;  // ✅ 여러 개의 목표를 저장 가능
    private List<DateDiaryResponse> dateDiarySentence;
}