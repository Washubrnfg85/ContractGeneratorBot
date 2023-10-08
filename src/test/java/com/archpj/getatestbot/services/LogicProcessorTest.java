package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class LogicProcessorTest {

    private Map<Long, Session> sessions = new HashMap<>();
    private LogicProcessor logicProcessor = Mockito.mock(LogicProcessor.class);

    @BeforeEach
    void initializeMap() {
        Session session1 = new Session(0L, "A", "topic");
        Session session2 = new Session(1L, "B", "topic");
        Session session3 = new Session(2L, "C", "topic");
        Session session4 = new Session(3L, "D", "topic");

        session1.setOver(false);
        session2.setOver(true);
        session3.setOver(false);
        session4.setOver(false);

        sessions.put(0L, session1);
        sessions.put(1L, session2);
        sessions.put(2L, session3);
        sessions.put(3L, session4);
    }


    @Test
    void processUpdate() {
    }

    @Test
    void sessionIsOver_shouldReturnFalse() {
        Mockito.when(logicProcessor.sessionIsOver(2L)).
                thenReturn(sessions.containsKey(2L) && sessions.get(2L).isOver());

        assertFalse(logicProcessor.sessionIsOver(2L));
    }

    @Test
    void sessionIsOver_shouldReturnFalseAlso() {
        Mockito.when(logicProcessor.sessionIsOver(4L)).
                thenReturn(sessions.containsKey(4L) && sessions.get(4L).isOver());

        assertFalse(logicProcessor.sessionIsOver(2L));
    }

    @Test
    void removeSessionIfComplete_shouldReturnFalse() {
    }

    @Test
    void sendResultToAdmin() {
    }
}