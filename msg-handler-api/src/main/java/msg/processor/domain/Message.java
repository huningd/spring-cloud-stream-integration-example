package msg.processor.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String id;
    private String text;

    @JsonCreator
    public Message(@JsonProperty("id") String id,
                   @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }


    public String getText() {
        return text;
    }

}
