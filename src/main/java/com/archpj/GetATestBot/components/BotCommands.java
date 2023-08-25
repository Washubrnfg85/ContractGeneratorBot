package com.archpj.GetATestBot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    final List<BotCommand> COMMANDS = List.of(
            new BotCommand("/start", "Начать все сначала"),
            new BotCommand("/test", "Начать тестирование"),
            new BotCommand("/help", "Помощь")
    );

    String HELP_TEXT =  "Описание функционала и комманд:\n\n" +
            "/help - доступные комманды и пояснения\n" +
            "/test - выбрать тему и начать тест\n\n" +
            "/start - перезапустить все сначала\n" +
            "Каждая тема содержит около 10-15 вопросов.\n" +
            "Ответы принимаются только нажатием кнопок, постарайтесь поменьше писать.\n" +
            "После прохождения теста сведения записываются в базу данных, " +
            "а результат направляется Вашему работодателю. Вы можете в любой момент пройти тест заново, но предыдущие результаты будут стерты.";
}
