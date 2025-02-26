package dairy.dairybe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="diary_summary")
@Getter
@NoArgsConstructor
public class DiarySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private int month;
    private int date;

    private String summary;

    @Builder
    public DiarySummary(String year, int month, int date, String summary) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.summary = summary;
    }

}
