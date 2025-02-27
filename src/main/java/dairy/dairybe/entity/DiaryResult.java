package dairy.dairybe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="diary_summary")
@Getter
@NoArgsConstructor
public class DiaryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private int month;
    private int date;

    @Lob
    private String diaryKo;
    @Lob
    private String diaryEn;
    @Lob
    private String diaryJa;
    @Lob
    private String diaryCh;
    @Lob
    private String diaryLa;

    private String summary;

    @Builder
    public DiaryResult(String year, int month, int date,
                       String diaryKo, String diaryEn, String diaryJa,
                       String diaryCh, String diaryLa, String summary) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.diaryKo = diaryKo;
        this.diaryEn = diaryEn;
        this.diaryJa = diaryJa;
        this.diaryCh = diaryCh;
        this.diaryLa = diaryLa;
        this.summary = summary;
    }

}
