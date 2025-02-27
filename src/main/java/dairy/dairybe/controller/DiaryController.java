package dairy.dairybe.controller;

import dairy.dairybe.dto.DateDiaryRequestDTO;
import dairy.dairybe.dto.DateDiaryResponse;
import dairy.dairybe.dto.MonthlyDiaryResponse;
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

    @GetMapping("/{year}-{month}")
    public ResponseEntity<?> getMonthlyDiary(@PathVariable String year,
                                             @PathVariable int month) {
        try {
            System.out.println("25 " + year + "-" + month);
            MonthlyDiaryResponse response = diaryService.getMonthlyDiary(year, month);
            System.out.println("27 " + year + "-" + month);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("30 " + year + "-" + month);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "internal_server_error", "data", null));
        }
    }

    @PostMapping("/{year}-{month}-{date}")
    public ResponseEntity<DateDiaryResponse> createDiary(
            @PathVariable String year, @PathVariable int month, @PathVariable int date,
            @RequestBody DateDiaryRequestDTO requestDto) {
        DateDiaryResponse response = diaryService.createDiary(year, month, date, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{year}-{month}-{date}")
    public ResponseEntity<String> deleteDiary(@PathVariable String year, @PathVariable int month, @PathVariable int date) {
        diaryService.deleteDiary(year, month, date);
        return ResponseEntity.ok("delete_success");
    }

    // https 테스트용 메서드
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}