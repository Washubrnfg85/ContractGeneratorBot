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

        rows.add(new KeyboardRow(keyboardButtons(Specs.IMPLANT.getSpec())));
        rows.add(new KeyboardRow(keyboardButtons(Specs.SURGERY.getSpec())));
        rows.add(new KeyboardRow(keyboardButtons(Specs.THERAPY.getSpec())));
        rows.add(new KeyboardRow(keyboardButtons(Specs.HYGIENIC_CLEANING.getSpec())));
        rows.add(new KeyboardRow(keyboardButtons(Specs.ORTHOPEDIC.getSpec())));
        rows.add(new KeyboardRow(keyboardButtons(Specs.ORTHODONTIC.getSpec())));

        return rows;
    }


    public static List<KeyboardButton> keyboardButtons(String theme) {
        List<KeyboardButton> buttons = new ArrayList<>();

        buttons.add(new KeyboardButton(theme));

        return buttons;
    }
}
