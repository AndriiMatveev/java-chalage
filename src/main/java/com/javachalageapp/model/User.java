package com.javachalageapp.model;

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
                + ", location='" + location + '\''
                + ", answerCount=" + answerCount
                + ", questionCount=" + questionCount
                + ", tags='" + tags + '\''
                + ", profile='" + profile + '\''
                + ", avatar='" + avatar + '\''
                + ", reputation=" + reputation
                + '}';
    }
}
