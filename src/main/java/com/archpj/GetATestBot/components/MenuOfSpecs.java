package com.archpj.GetATestBot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuOfSpecs {

    public static ReplyKeyboardMarkup sendMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRows());

        return replyKeyboardMarkup;
    }


    public static List<KeyboardRow> keyboardRows() {
        List<KeyboardRow> rows = new ArrayList<>();

        rows.add(new KeyboardRow(keyboardButtons("Импланталогия")));
        rows.add(new KeyboardRow(keyboardButtons("Хирургия")));
        rows.add(new KeyboardRow(keyboardButtons("Терапия")));
        rows.add(new KeyboardRow(keyboardButtons("Гигиеническая чистка")));
        rows.add(new KeyboardRow(keyboardButtons("Ортопедия")));
        rows.add(new KeyboardRow(keyboardButtons("Ортодонтия")));

        return rows;
    }


    public static List<KeyboardButton> keyboardButtons(String theme) {
        List<KeyboardButton> buttons = new ArrayList<>();

        buttons.add(new KeyboardButton(theme));

        return buttons;
    }
}
