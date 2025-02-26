package dairy.dairybe.service;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.entity.Diary;
import dairy.dairybe.repository.DateDiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryService {

    @Autowired
    private DateDiaryRepository dateDiaryRepository;

    @Transactional
    public String createDiary(String dateString, DateDiaryRequestDTO requestDto) {

        Diary diary = Diary.builder()
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
}
