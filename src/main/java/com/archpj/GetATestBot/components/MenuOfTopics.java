package com.archpj.GetATestBot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuOfTopics {

    public static ReplyKeyboardMarkup generateMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRows());

        return replyKeyboardMarkup;
    }


    public static List<KeyboardRow> keyboardRows() {
        List<KeyboardRow> rows = new ArrayList<>();

        for(String topic : CommonTopics.toList()) {
            rows.add(new KeyboardRow(keyboardButtons(topic)));
        }
        return rows;
    }


    public static List<KeyboardButton> keyboardButtons(String topic) {
        List<KeyboardButton> buttons = new ArrayList<>();

        buttons.add(new KeyboardButton(topic));

        return buttons;
    }
}
