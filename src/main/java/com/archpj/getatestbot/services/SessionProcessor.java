package com.archpj.getatestbot.services;

import com.archpj.getatestbot.components.Buttons;
import com.archpj.getatestbot.components.CommonTopics;
import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;
import com.archpj.getatestbot.models.Session;
import com.archpj.getatestbot.utils.OnQuizUpdateHandler;
import com.archpj.getatestbot.utils.OutOfQuizUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionProcessor {

    private final DataService dataService;
    private Map<Long, Session> sessions;

    @Autowired
    public SessionProcessor(DataService dataService) {
        this.dataService = dataService;
        this.sessions = new HashMap<>();
    }

    public SendMessage processUpdate(Update update, long employeeId, String employeeName) {
        SendMessage sendMessage;

        if (employeeHasSession(employeeId)) {
            sendMessage = OnQuizUpdateHandler.handleUpdate(update);
        } else {
            sendMessage = OutOfQuizUpdateHandler.handleUpdate(update);
        }

        if (sendMessage.getText().matches("[A-D]") || CommonTopics.containsValue(sendMessage.getText())) {
            if (!employeeHasSession(employeeId)) {
                Session newSession = new Session(employeeId, employeeName, sendMessage.getText());
                loadQuizQuestions(newSession);
                sessions.put(employeeId, newSession);
            } else {
                sessions.get(employeeId).appendAnswer(sendMessage.getText());
            }
            sendMessage = sendNextQuestion(sessions.get(employeeId));
            incrementIteration(sessions.get(employeeId));
        }
        return sendMessage;
    }

    public void loadQuizQuestions(Session session) {
        session.setQuizQuestions(dataService.loadQuizQuestions(session.getTopic()));
        initializeCorrectAnswers(session);
    }

    public void initializeCorrectAnswers(Session session) {
        StringBuilder result = new StringBuilder();
        for (QuizQuestion question : session.getQuizQuestions()) {
            result.append(question.getCorrectAnswer());
        }
        session.setCorrectAnswers(result.toString());
    }

    public SendMessage sendNextQuestion(Session session) {
        long employeeId = session.getEmployeeId();
        int iterationsThroughTest = session.getIterationsThroughTest();
        List<QuizQuestion> quizQuestions = session.getQuizQuestions();

        if (iterationsThroughTest < quizQuestions.size()) {
            return SendMessage.builder().
                    chatId(employeeId).
                    text(quizQuestions.get(iterationsThroughTest).getQuestion()).
                    replyMarkup(Buttons.suggestAnswers()).
                    build();
        }
        return completeQuiz(session);
    }

    public SendMessage completeQuiz(Session session) {
        long employeeId = session.getEmployeeId();
        String employeeName = session.getEmployeeName();
        String topic = session.getTopic();
        String correctAnswers = session.getCorrectAnswers();
        String employeeAnswers = session.getEmployeeAnswers();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        QuizResult quizResult = new QuizResult(employeeId, employeeName, topic,
                correctAnswers + "\n" + employeeAnswers, timestamp);

        sessions.get(employeeId).setQuizResult(quizResult);
        dataService.saveQuizResult(quizResult);
        session.setOver(true);

        return SendMessage.builder().
                chatId(employeeId).
                text("Тест по теме " + topic + " завершен.\n" +
                        "Ваш результат: " + quizResult.calculateScore() +
                        "\nВы можете выбрать другую тему или пересдать эту.").
                build();
    }


    public void incrementIteration(Session session) {
        int iteration  = session.getIterationsThroughTest();
        iteration++;
        session.setIterationsThroughTest(iteration);
    }

    public boolean sessionIsOver(long employeeId) {
        return sessions.containsKey(employeeId) && sessions.get(employeeId).isOver();
    }

    public void removeSessionIfComplete(long employeeId) {
        if (employeeHasSession(employeeId) && sessions.get(employeeId).isOver()) {
            sessions.remove(employeeId);
        }
    }

    public SendMessage sendResultToAdmin(long employeeId) {
        Session session = sessions.get(employeeId);
        String employeeName = session.getEmployeeName();
        String topic = session.getTopic();

        return SendMessage.builder().
                chatId(employeeId).
                text(employeeName + " прошел тест по теме " + topic + "\n" +
                        "с результатом " + sessions.get(employeeId).getQuizResult().calculateScore()).
                build();
    }

    public boolean employeeHasSession(long employeeId) {
        return sessions.containsKey(employeeId);
    }

}
