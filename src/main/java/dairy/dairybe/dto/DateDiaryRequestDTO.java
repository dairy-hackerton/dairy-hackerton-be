package dairy.dairybe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class DateDiaryRequestDTO {
    private String tone;
    private String mood;
    private String wakeTime;
    private List<String> food;
    private List<String> userDo;
    private List<String> meetPeople;
    private String extSentence;

    @JsonCreator
    public DateDiaryRequestDTO(@JsonProperty("tone") String tone,
                               @JsonProperty("mood") String mood,
                               @JsonProperty("wakeTime") String wakeTime,
                               @JsonProperty("food") List<String> food,
                               @JsonProperty("userDo") List<String> userDo,
                               @JsonProperty("meetPeople") List<String> meetPeople,
                               @JsonProperty("extSentence") String extSentence) {
        this.tone = tone;
        this.mood = mood;
        this.wakeTime = wakeTime;
        this.food = food;
        this.userDo = userDo;
        this.meetPeople = meetPeople;
        this.extSentence = extSentence;
    }
}