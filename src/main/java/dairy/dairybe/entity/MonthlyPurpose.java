package dairy.dairybe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "monthly_purpose")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyPurpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;

    @ElementCollection
    private List<String> purposes;

    @Builder
    public MonthlyPurpose(int year, int month, List<String> purposes) {
        this.year = year;
        this.month = month;
        this.purposes = purposes;
    }
}
