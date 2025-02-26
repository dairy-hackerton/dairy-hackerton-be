package dairy.dairybe.controller;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.entity.Diary;
import dairy.dairybe.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("/{year}-{month}-{date}")
    public ResponseEntity<?> createDiary(@PathVariable String year,
                                         @PathVariable int month,
                                         @PathVariable int date,
                                         @RequestBody DateDiaryRequestDTO requestDto) {
        try {
            if (requestDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid request body");
            }

            // month와 date가 int 타입이므로 앞의 0이 자동으로 제거됨
            String dateString = String.format("%s-%d-%d", year, month, date);

            String diary = diaryService.createDiary(dateString, requestDto);
            return ResponseEntity.ok(diary); // FastAPI 대신 toString 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }
}
