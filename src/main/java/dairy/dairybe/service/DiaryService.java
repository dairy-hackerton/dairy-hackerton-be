package dairy.dairybe.service;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.dto.DateDiaryResponse;
import dairy.dairybe.dto.MonthlyDiaryResponse;
import dairy.dairybe.entity.Diary;
import dairy.dairybe.entity.DiaryResult;
import dairy.dairybe.entity.MonthlyPurpose;
import dairy.dairybe.repository.DateDiaryRepository;
import dairy.dairybe.repository.DiaryResultRepository;
import dairy.dairybe.repository.MonthlyPurposeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final RestTemplate restTemplate;
    @Autowired
    private DateDiaryRepository dateDiaryRepository;
    @Autowired
    private DiaryResultRepository diaryResultRepository;
    @Autowired
    private MonthlyPurposeRepository monthlyPurposeRepository;

    @Transactional
    public DateDiaryResponse createDiary(String year, int month, int day, DateDiaryRequestDTO requestDto) {

        Map<String, Object> requestBody = Map.of(
                "tone", requestDto.getTone(),
                "mood", requestDto.getMood(),
                "wakeTime", requestDto.getWakeTime(),
                "food", requestDto.getFood(),
                "userDo", requestDto.getUserDo(),
                "meetPeople", requestDto.getMeetPeople(),
                "extSentence", requestDto.getExtSentence()
        );

        String fastApiUrl = "https://orange-chainsaw-q7qgpx95p9gxc6wr9-8000.app.github.dev/generate_diary"; // 수정예정
        ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, requestBody, Map.class);

        Map responseData = response.getBody();
        if (responseData == null) {
            throw new RuntimeException("FastAPI 오류");
        }

        String diaryKo = (String) responseData.get("diary_kor");
        String diaryEn = (String) responseData.get("diary_eng");
        String diaryJa = (String) responseData.get("diary_japan");
        String diaryCh = (String) responseData.get("diary_China");
        String diaryLa = (String) responseData.get("diary_latin");
        String summary = (String) responseData.get("summary");

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

        DiaryResult diaryResult = DiaryResult.builder()
                .year(year)
                .month(month)
                .date(day)
                .diaryKo(diaryKo)
                .diaryEn(diaryEn)
                .diaryJa(diaryJa)
                .diaryCh(diaryCh)
                .diaryLa(diaryLa)
                .summary(summary)
                .build();
        diaryResultRepository.save(diaryResult);

        return new DateDiaryResponse(diary, diaryResult);
    }

    @Transactional(readOnly = true)
    public MonthlyDiaryResponse getMonthlyDiary(String year, int month) {

        List<String> monthlyPurposes = monthlyPurposeRepository.findByyearAndMonth(year, month)
                .map(MonthlyPurpose::getPurposes)
                .orElse(Collections.emptyList());

        List<DiaryResult> diaryResults = diaryResultRepository.findAllByYearMonth(year, month);

        List<DateDiaryResponse> dateDiaryResponses = diaryResults.stream()
                .map(diary -> new DateDiaryResponse(
                        diary.getYear(), diary.getMonth(), diary.getDate(),
                        diary.getSummary(), diary.getDiaryKo(), diary.getDiaryEn(),
                        diary.getDiaryJa(), diary.getDiaryCh(), diary.getDiaryLa()
                ))
                .collect(Collectors.toList());

        return new MonthlyDiaryResponse(year, month, "monthly_diary_success", monthlyPurposes, dateDiaryResponses);
    }

    @Transactional
    public void deleteDiary(String year, int month, int day) {
        // 다이어리 존재 여부 확인
        Optional<Diary> diaryOpt = dateDiaryRepository.findByYearAndMonthAndDay(year, month, day);
        if (diaryOpt.isEmpty()) {
            throw new EntityNotFoundException("해당 날짜의 다이어리가 존재하지 않습니다.");
        }

        // 해당 날짜의 다이어리 삭제
        dateDiaryRepository.deleteByYearAndMonthAndDay(year, month, day);

        // 해당 날짜의 요약 및 외국어 일기 삭제
        diaryResultRepository.deleteByYearAndMonthAndDate(year, month, day);
    }
}