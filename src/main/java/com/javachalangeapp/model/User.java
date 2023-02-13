package com.javachalangeapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private String location;
    private Long answerCount;
    private Long questionCount;
    private String tags;
    private String profile;
    private String avatar;
    private Long reputation;
    private Long id;

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", " + System.lineSeparator()
                + "location='" + location + '\''
                + ", " + System.lineSeparator()
                + "answerCount=" + answerCount
                + ", " + System.lineSeparator()
                + "questionCount=" + questionCount
                + ", " + System.lineSeparator()
                + "tags='" + tags + '\''
                + "," + System.lineSeparator()
                + "profile='" + profile + '\''
                + ", " + System.lineSeparator()
                + "avatar='" + avatar + '\''
                + ", " + System.lineSeparator()
                + "reputation=" + reputation
                + '}';
    }
}
