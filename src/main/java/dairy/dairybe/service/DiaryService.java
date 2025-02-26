package dairy.dairybe.service;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.dto.DateDiaryResponse;
import dairy.dairybe.dto.MonthlyDiaryResponse;
import dairy.dairybe.entity.Diary;
import dairy.dairybe.entity.DiarySummary;
import dairy.dairybe.entity.MonthlyPurpose;
import dairy.dairybe.repository.DateDiaryRepository;
import dairy.dairybe.repository.DiarySummaryRepository;
import dairy.dairybe.repository.MonthlyPurposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiaryService {

    @Autowired
    private DateDiaryRepository dateDiaryRepository;
    @Autowired
    private DiarySummaryRepository diarySummaryRepository;
    @Autowired
    private MonthlyPurposeRepository monthlyPurposeRepository;

    @Transactional
    public String createDiary(String year, int month, int day, DateDiaryRequestDTO requestDto) {

        Diary diary = Diary.builder()
                .year(year)
                .month(month)
                .day(day)
                .tone(requestDto.getTone())
                .mood(requestDto.getMood())
                .wakeTime(requestDto.getWakeTime())
                .food(requestDto.getFood())
                .userDo(requestDto.getUserDo())
                .meetPeople(requestDto.getMeetPeople())
                .extSentence(requestDto.getExtSentence())
                .build();

        dateDiaryRepository.save(diary);

        // FastAPI로 전송 및 결과 반환(이건 나중에)
        // ResponseEntity<String> response = fastApiClient.processDiary(diary);

        // return response.getBody();
        return diary.toString(); // 이건 테스트로 뱉는거
    }

    @Transactional(readOnly = true)
    public MonthlyDiaryResponse getMonthlyDiary(String year, int month) {

        List<Diary> monthlyDiaries = dateDiaryRepository.findByYearAndMonth(year, month);

        List<String> monthlyPurposes = monthlyPurposeRepository.findByyearAndMonth(year, month)
                .map(MonthlyPurpose::getPurposes)
                .orElse(Collections.emptyList());

        // ✅ 다이어리 데이터를 매핑하여 응답 객체 생성
        List<DateDiaryResponse> dateDiaryResponses = monthlyDiaries.stream()
                .map(diary -> {
                    Optional<DiarySummary> summaryOpt = diarySummaryRepository.findSummaryByYearMonthDate(
                            diary.getYear(), diary.getMonth(), diary.getDay());

                    if (summaryOpt.isEmpty()) {
                        System.out.println("❌ [diary_summary] 데이터 없음: " + diary.getYear() + "-" + diary.getMonth() + "-" + diary.getDay());
                    } else {
                        System.out.println("✅ [diary_summary] 조회 성공: " + summaryOpt.get().getSummary());
                    }

                    String summary = summaryOpt.map(DiarySummary::getSummary).orElse("요약 없음");

                    return new DateDiaryResponse(diary.getYear(), diary.getMonth(), diary.getDay(),
                            summary, diary.getMood());
                })
                .collect(Collectors.toList());

        return new MonthlyDiaryResponse("monthly_diary_success", monthlyPurposes, dateDiaryResponses);
    }

}