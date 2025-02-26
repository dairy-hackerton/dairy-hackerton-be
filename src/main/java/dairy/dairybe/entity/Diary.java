package dairy.dairybe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="dateDiary")
@Getter
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String year;
    private int month;
    private int day;
    private String tone;
    private String mood;
    private String wakeTime;

    @ElementCollection
    private List<String> food;

    @ElementCollection
    private List<String> meetPeople;

    @ElementCollection
    private List<String> userDo;

    private String extSentence;

    @Builder
    public Diary(String year, int month, int day, String wakeTime, String tone, String mood,
                 List<String> food, List<String> userDo, List<String> meetPeople,
                 String extSentence) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.wakeTime = wakeTime;
        this.tone = tone;
        this.mood = mood;
        this.food = food;
        this.userDo = userDo;
        this.meetPeople = meetPeople;
        this.extSentence = extSentence;
    }

    @Override
    public String toString() {
        return "waketime " + wakeTime + " tone " + tone + " mood " + mood;
    }
}
