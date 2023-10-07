package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

abstract class UpdateHandler {

    public static SendMessage handleUpdate(Update update) {
        if (update.hasMessage()) return handleMessage(update);
        if (update.hasCallbackQuery()) return handleCallbackQuery(update);

        return org.telegram.telegrambots.meta.api.methods.send.SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text(BotCommands.USER_ERROR_TEXT).
                build();
    }

    static SendMessage handleMessage(Update update) {
        return null;
    }

    static SendMessage handleCallbackQuery(Update update) {
        return null;
    }
}
