package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.components.CommonTopics;
import com.archpj.GetATestBot.models.QuizQuestion;
import com.archpj.GetATestBot.models.QuizResult;
import com.archpj.GetATestBot.models.Session;
import com.archpj.GetATestBot.utils.OnQuizUpdateHandler;
import com.archpj.GetATestBot.utils.OutOfQuizUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionManager {

    @Autowired
    private final SessionService sessionService;
    private Map<Long, Session> sessions;

    public SessionManager(SessionService sessionService) {
        this.sessionService = sessionService;
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
                Session newSession = new Session(employeeId, employeeName, sendMessage.getText());
                loadQuizQuestions(newSession);
                sessions.put(employeeId, newSession);

                System.out.println("Session created");
            } else {
                sessions.get(employeeId).appendAnswer(sendMessage.getText());
            }
            sendMessage = sendNextQuestion(sessions.get(employeeId));
            incrementIteration(sessions.get(employeeId));
            System.out.println("Sent next question");
        }
        return sendMessage;
    }

    public void loadQuizQuestions(Session session) {
        session.setQuizQuestions(sessionService.loadQuizQuestions(session.getTopic()));
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
        System.out.println("CompleteQuiz");
        long employeeId = session.getEmployeeId();
        String employeeName = session.getEmployeeName();
        String topic = session.getTopic();
        String correctAnswers = session.getCorrectAnswers();
        String employeeAnswers = session.getEmployeeAnswers();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        sessionService.saveQuizResult(new QuizResult(employeeId, employeeName, topic,
                correctAnswers + "\n" + employeeAnswers, timestamp));
        session.setOver(true);
        return SendMessage.builder().
                chatId(employeeId).
                text("Тест по теме " + topic + " завершен.\n" +
                        "Ваш результат: " + calculateScore(session) +
                        "\nВы можете выбрать другую тему или пересдать эту.").
                build();
    }

    public String calculateScore(Session session) {
        System.out.println("Calculate Score");

        String correctAnswers = session.getCorrectAnswers();
        String employeeAnswers = session.getEmployeeAnswers();

        String score = "";

        if (correctAnswers.equals(employeeAnswers)) {
            score = employeeAnswers.length() + "/" + correctAnswers.length();
        } else {
            String[] correctLetters = correctAnswers.split("");
            String[] employeeLetters = employeeAnswers.split("");

            if (correctLetters.length == employeeLetters.length) {
                int numberOfScores = 0;
                for (int i = 0; i < correctLetters.length; i++) {
                    if (correctLetters[i].equals(employeeLetters[i])) numberOfScores++;
                }
                score = numberOfScores + "/" + correctAnswers.length();
            } else {
                score = "Неизвестная ошибка. Необходимо пересдать тест и сообщить разработчику";
                //Logging
            }
        }
        return score;
    }

    public void incrementIteration(Session session) {
        int iteration  = session.getIterationsThroughTest();
        iteration++;
        session.setIterationsThroughTest(iteration);
    }

    public boolean employeeHasSession(long employeeId) {
        return sessions.containsKey(employeeId);
    }

    public boolean sessionIsOver(long employeeId) {
        return sessions.containsKey(employeeId) && sessions.get(employeeId).isOver();
    }

    public void removeSessionIfComplete(long employeeId) {
        if (employeeHasSession(employeeId) && sessions.get(employeeId).isOver()) {
            sessions.remove(employeeId);
            System.out.println("Session removed");
        }
    }

    public SendMessage sendResultToAdmin(long employeeId) {
        Session session = sessions.get(employeeId);
        String employeeName = session.getEmployeeName();
        String topic = session.getTopic();

        return SendMessage.builder().
                chatId(employeeId).
                text(employeeName + " прошел тест по теме " + topic + "\n" +
                        "с результатом " + calculateScore(session)).
                build();
    }

}
