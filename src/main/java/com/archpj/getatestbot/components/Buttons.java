package com.archpj.getatestbot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton REJECT_TEST_BUTTON = new InlineKeyboardButton("Отказаться");
    private static final InlineKeyboardButton A_BUTTON = new InlineKeyboardButton("A");
    private static final InlineKeyboardButton B_BUTTON = new InlineKeyboardButton("B");
    private static final InlineKeyboardButton C_BUTTON = new InlineKeyboardButton("C");
    private static final InlineKeyboardButton D_BUTTON = new InlineKeyboardButton("D");


    public static InlineKeyboardMarkup rejectTest() {
        REJECT_TEST_BUTTON.setCallbackData("/test rejected");

        List<InlineKeyboardButton> buttonsInRow = List.of(REJECT_TEST_BUTTON);
        List<List<InlineKeyboardButton>> rows = List.of(buttonsInRow);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);

        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup suggestAnswers() {
        A_BUTTON.setCallbackData("A");
        B_BUTTON.setCallbackData("B");
        C_BUTTON.setCallbackData("C");
        D_BUTTON.setCallbackData("D");

        List<InlineKeyboardButton> buttonsInRow = List.of(A_BUTTON, B_BUTTON, C_BUTTON, D_BUTTON);
        List<List<InlineKeyboardButton>> rows = List.of(buttonsInRow);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);

        return keyboardMarkup;
    }
}
