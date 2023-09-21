package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.components.CommonTopics;
import com.archpj.GetATestBot.models.Session;
import com.archpj.GetATestBot.utils.OnQuizUpdateHandler;
import com.archpj.GetATestBot.utils.OutOfQuizUpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManager {

    private Map<Long, Session> sessions;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    public SendMessage processUpdate(Update update, long employeeId, String employeeName) {
        SendMessage sendMessage;

        if (employeeHasSession(employeeId)) {
            System.out.println("if 1");
            sendMessage = OnQuizUpdateHandler.handleUpdate(update);
        } else {
            System.out.println("else 1");
            sendMessage = OutOfQuizUpdateHandler.handleUpdate(update);
        }

        if (sendMessage.getText().matches("[A-D]") || CommonTopics.containsValue(sendMessage.getText())) {
            System.out.println("if 2");
            if (!employeeHasSession(employeeId)) {
                sessions.put(employeeId, new Session(employeeId,
                        employeeName,
                        sendMessage.getText()));
                System.out.println("Session created");
            } else {
                sessions.get(employeeId).appendAnswer(sendMessage.getText());
            }
            sendMessage = sessions.get(employeeId).sendNextQuestion();
            System.out.println("Sent next question");
        }
        return sendMessage;
    }


    public boolean employeeHasSession(long employeeId) {
        return sessions.containsKey(employeeId);
    }

    public void removeSessionIfComplete(long employeeId) {
        if (employeeHasSession(employeeId) && sessions.get(employeeId).isOver()) {
            sessions.remove(employeeId);
            System.out.println("Session removed");
        }
    }
}
