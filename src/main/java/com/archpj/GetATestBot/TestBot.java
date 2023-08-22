package com.archpj.GetATestBot;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;
import static com.archpj.GetATestBot.components.BotCommands.HELP_TEXT;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    final BotConfig botConfig;

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
        long chatId;
        long userId = 0;
        String userName;
        String receivedMessage;

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                startBot(chatId, userName);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            default: break;
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


    public ReplyKeyboardMarkup sendStartMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRows());

        return replyKeyboardMarkup;
    }


    public List<KeyboardRow> keyboardRows() {
        List<KeyboardRow> rows = new ArrayList<>();

        rows.add(new KeyboardRow(keyboardButtons("Тестируем")));
        rows.add(new KeyboardRow(keyboardButtons("Импланталогия")));
        rows.add(new KeyboardRow(keyboardButtons("Хирургия")));
        rows.add(new KeyboardRow(keyboardButtons("Терапия")));
        rows.add(new KeyboardRow(keyboardButtons("Гигиеническая чистка")));
        rows.add(new KeyboardRow(keyboardButtons("Ортопедия")));
        rows.add(new KeyboardRow(keyboardButtons("Ортодонтия")));

        return rows;
    }


    public List<KeyboardButton> keyboardButtons(String theme) {
        List<KeyboardButton> buttons = new ArrayList<>();

        buttons.add(new KeyboardButton(theme));

        return buttons;
    }
}
