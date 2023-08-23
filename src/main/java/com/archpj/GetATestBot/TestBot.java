package com.archpj.GetATestBot;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.models.Employee;
import com.archpj.GetATestBot.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;
import static com.archpj.GetATestBot.components.BotCommands.HELP_TEXT;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    final BotConfig botConfig;

    @Autowired
    private EmployeeService employeeService;


    public TestBot (BotConfig botConfig) {
        this.botConfig = botConfig;
        try {
            this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return botConfig.getName();
    }

    public String getBotToken() {
        return botConfig.getToken();
    }


    public void onUpdateReceived(Update update) {

        long chatId = 0;
        String userName = null;
        String receivedMessage;

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName, update);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName, update);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName, Update update) {
        switch (receivedMessage) {
            case "/start" -> {
                startBot(chatId, userName);
                registerEmployee(update.getMessage());
            }
            case "/help" -> sendHelpText(chatId, HELP_TEXT);
            default -> {
            }
        }
    }

    private void registerEmployee(Message message) {
        if(employeeService.findEmployee(message.getChatId()) == null) {
            long telegramId = message.getFrom().getId();

            Employee employee = new Employee();
            employee.setTelegramId(telegramId);
            employee.setName(message.getFrom().getFirstName() + " " +
                    message.getFrom().getLastName());
            employee.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            employeeService.save(employee);
            log.info("Employee saved to database: " + employee);
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello, " + userName + "! I'm a Telegram bot.");
        message.setReplyMarkup(Buttons.inlineMarkup());


        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


//    public ReplyKeyboardMarkup sendStartMenu() {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        replyKeyboardMarkup.setKeyboard(keyboardRows());
//
//        return replyKeyboardMarkup;
//    }
//
//
//    public List<KeyboardRow> keyboardRows() {
//        List<KeyboardRow> rows = new ArrayList<>();
//
//        rows.add(new KeyboardRow(keyboardButtons("Тестируем")));
//        rows.add(new KeyboardRow(keyboardButtons("Импланталогия")));
//        rows.add(new KeyboardRow(keyboardButtons("Хирургия")));
//        rows.add(new KeyboardRow(keyboardButtons("Терапия")));
//        rows.add(new KeyboardRow(keyboardButtons("Гигиеническая чистка")));
//        rows.add(new KeyboardRow(keyboardButtons("Ортопедия")));
//        rows.add(new KeyboardRow(keyboardButtons("Ортодонтия")));
//
//        return rows;
//    }
//
//
//    public List<KeyboardButton> keyboardButtons(String theme) {
//        List<KeyboardButton> buttons = new ArrayList<>();
//
//        buttons.add(new KeyboardButton(theme));
//
//        return buttons;
//    }
}
