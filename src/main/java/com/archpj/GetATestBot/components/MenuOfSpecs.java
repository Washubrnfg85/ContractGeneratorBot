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

        for(String spec : Specialisations.toList()) {
            rows.add(new KeyboardRow(keyboardButtons(spec)));
        }

//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.IMPLANT.getSpecialisation())));
//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.SURGERY.getSpecialisation())));
//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.THERAPY.getSpecialisation())));
//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.HYGIENIC_CLEANING.getSpecialisation())));
//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.ORTHOPEDIC.getSpecialisation())));
//        rows.add(new KeyboardRow(keyboardButtons(Specialisations.ORTHODONTIC.getSpecialisation())));

        return rows;
    }


    public static List<KeyboardButton> keyboardButtons(String specialisation) {
        List<KeyboardButton> buttons = new ArrayList<>();

        buttons.add(new KeyboardButton(specialisation));

        return buttons;
    }
}
