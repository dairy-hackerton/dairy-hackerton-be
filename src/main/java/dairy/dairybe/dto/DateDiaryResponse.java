package dairy.dairybe.dto;

import dairy.dairybe.entity.Diary;
import dairy.dairybe.entity.DiaryResult;
import lombok.Getter;

import java.util.List;

@Getter
public class DateDiaryResponse {
    private final int year;
    private final int month;
    private final int day;
    private final String tone;
    private final String mood;
    private final String wakeTime;
    private final List<String> food;
    private final List<String> userDo;
    private final List<String> meetPeople;
    private final String extSentence;
    private final String diaryKo;
    private final String diaryEn;
    private final String diaryJa;
    private final String diaryCh;
    private final String diaryLa;
    private final String summary;

    public DateDiaryResponse(Diary diary, DiaryResult diaryResult) {
        this.year = diary.getYear();
        this.month = diary.getMonth();
        this.day = diary.getDay();
        this.tone = diary.getTone();
        this.mood = diary.getMood();
        this.wakeTime = diary.getWakeTime();
        this.food = diary.getFood();
        this.userDo = diary.getUserDo();
        this.meetPeople = diary.getMeetPeople();
        this.extSentence = diary.getExtSentence();
        this.diaryKo = diaryResult.getDiaryKo();
        this.diaryEn = diaryResult.getDiaryEn();
        this.diaryJa = diaryResult.getDiaryJa();
        this.diaryCh = diaryResult.getDiaryCh();
        this.diaryLa = diaryResult.getDiaryLa();
        this.summary = diaryResult.getSummary();
    }

    public DateDiaryResponse(int year, int month, int day, String summary, String diaryKo, String diaryEn,
                             String diaryJa, String diaryCh, String diaryLa) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.tone = null;
        this.mood = null;
        this.wakeTime = null;
        this.food = null;
        this.userDo = null;
        this.meetPeople = null;
        this.extSentence = null;
        this.diaryKo = diaryKo;
        this.diaryEn = diaryEn;
        this.diaryJa = diaryJa;
        this.diaryCh = diaryCh;
        this.diaryLa = diaryLa;
        this.summary = summary;
    }
}
