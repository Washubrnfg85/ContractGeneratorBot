package com.archpj.getatestbot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {

    List<BotCommand> COMMANDS = List.of(
            new BotCommand("/start", "Начать все сначала"),
            new BotCommand("/help", "Помощь")
    );

    String HELP_TEXT = """
            Описание функционала и комманд:

            /help - доступные комманды и пояснения
            /start - перезапустить все сначала

            Каждая тема содержит 5 вопросов.
            Ответы принимаются только нажатием кнопок.
            После прохождения теста сведения записываются в базу данных, а результат направляется Вашему работодателю. Вы можете в любой момент пройти тест заново, при этом история предыдущего тестированя сохранится.
            """;

    String USER_ERROR_TEXT = """
                        Обработка такого типа сообщений не предусмотрена функционалом.
                        Воспользуйтесь меню.""";

    String ERROR_TEXT = """
                        Если Вы читаете это сообщение, то что-то пошло не так.
                        Ошибка была записана в журнал.
                        Обратитесь, пожалуйста, к разработчику""";
}