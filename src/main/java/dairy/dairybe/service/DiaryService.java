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
        System.out.println("40 " + year + " " + month + " " + day);
        Map<String, Object> requestBody = Map.of(
                "tone", requestDto.getTone(),
                "mood", requestDto.getMood(),
                "wakeTime", requestDto.getWakeTime(),
                "food", requestDto.getFood(),
                "userDo", requestDto.getUserDo(),
                "meetPeople", requestDto.getMeetPeople(),
                "extSentence", requestDto.getExtSentence()
        );
        System.out.println("50 " + year + " " + month + " " + day);

        String fastApiUrl = "http://13.124.98.245:8000/generate_diary";
        ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, requestBody, Map.class);
        System.out.println("54 " + year + " " + month + " " + day);

        Map responseData = response.getBody();
        System.out.println("57 " + year + " " + month + " " + day);
        if (responseData == null) {
            System.out.println("59 " + year + " " + month + " " + day);
            throw new RuntimeException("FastAPI 오류");
        }

        System.out.println("62 " + year + " " + month + " " + day);

        String diaryKo = (String) responseData.get("diary_kor");
        String diaryEn = (String) responseData.get("diary_eng");
        String diaryJa = (String) responseData.get("diary_japan");
        String diaryCh = (String) responseData.get("diary_China");
        String diaryLa = (String) responseData.get("diary_latin");
        String summary = (String) responseData.get("summary");

        System.out.println("72 " + year + " " + month + " " + day);

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
        System.out.println("86 " + year + " " + month + " " + day);
        dateDiaryRepository.save(diary);
        System.out.println("88 " + year + " " + month + " " + day);

        System.out.println("90" + diary.getYear() + " " + diary.getMonth() + " " + diary.getDay());
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
                .diary(diary)
                .build();
        System.out.println("103줄" + diaryResult.getDiaryKo());
        diaryResultRepository.save(diaryResult);
        System.out.println("105줄" + diaryResult.getDiaryKo());

        return new DateDiaryResponse(diary, diaryResult);
    }

    @Transactional(readOnly = true)
    public MonthlyDiaryResponse getMonthlyDiary(String year, int month) {
        try {
            // 1. 데이터 조회 전 로그 출력
            System.out.println("114 " + year + ", month: " + month);
            Optional<MonthlyPurpose> optionalPurpose = monthlyPurposeRepository.findByyearAndMonth(year, month);

            // 2. 데이터가 있는지 로그 출력
            if (optionalPurpose.isPresent()) {
                System.out.println("Monthly purposes found: " + optionalPurpose.get().getPurposes());
            } else {
                System.out.println("No monthly purposes found.");
            }

            List<String> monthlyPurposes = optionalPurpose.map(MonthlyPurpose::getPurposes)
                    .orElse(Collections.emptyList());

            // 3. diaryResults 조회 전 로그
            System.out.println("Fetching diary results for year: " + year + ", month: " + month);
            List<DiaryResult> diaryResults = Optional.ofNullable(diaryResultRepository.findAllByYearMonth(year, month))
                    .orElse(Collections.emptyList());
            System.out.println("131 " + diaryResults);
            for (DiaryResult diaryResult : diaryResults) {
                System.out.println(diaryResult.getDiary());
            }

            System.out.println("Diary results found: " + diaryResults.size());
            List<DateDiaryResponse> dateDiaryResponses = diaryResults.stream()
                    .map(diaryResult -> {
                        Diary diary = diaryResult.getDiary();
                        System.out.println("139 " + diary);
                        return new DateDiaryResponse(diary, diaryResult);
                    })
                    .collect(Collectors.toList());

            return new MonthlyDiaryResponse(year, month, "monthly_diary_success", monthlyPurposes, dateDiaryResponses);
        } catch (Exception e) {
            System.err.println("Error in getMonthlyDiary: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
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