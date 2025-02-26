package dairy.dairybe.controller;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.dto.DateDiaryResponse;
import dairy.dairybe.dto.MonthlyDiaryResponse;
import dairy.dairybe.entity.Diary;
import dairy.dairybe.entity.DiarySummary;
import dairy.dairybe.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/{year}-{month}")
    public ResponseEntity<?> getMonthlyDiary(@PathVariable String year,
                                             @PathVariable int month) {
        try {
            MonthlyDiaryResponse response = diaryService.getMonthlyDiary(year, month);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "internal_server_error", "data", null));
        }
    }

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

            String diary = diaryService.createDiary(year, month, date, requestDto);
            return ResponseEntity.ok(diary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }
}
