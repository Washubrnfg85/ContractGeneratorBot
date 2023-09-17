package com.archpj.GetATestBot.components;

import java.util.ArrayList;
import java.util.List;

public enum CommonTopics {
    GEOGRAPHY ("География"),
    PHYSICS ("Физика"),
    ASTRONOMY ("Астрономия"),
    ANATOMY ("Анатомия"),
    HISTORY ("История")
    ;

    private final String topic;

    CommonTopics(String topic) {
        this.topic = topic;
    }

    public String gettopic() {
        return topic;
    }

    public static List<String> toList() {
        List<String> result = new ArrayList<>(CommonTopics.values().length);
        for (int i = 0; i < CommonTopics.values().length; i++) {
            result.add(CommonTopics.values()[i].gettopic());
        }

        return result;
    }
}
