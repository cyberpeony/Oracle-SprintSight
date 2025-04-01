package com.example.Oracle_SprintSight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.Oracle_SprintSight.controller.ToDoItemBotController;
import com.example.Oracle_SprintSight.service.ToDoItemService;
import com.example.Oracle_SprintSight.util.BotMessages;

@SpringBootApplication
public class Oracle_SprintSightApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Oracle_SprintSightApplication.class);

	@Autowired
	private ToDoItemService toDoItemService;

	@Value("${telegram.bot.token}")
	private String telegramBotToken;

	@Value("${telegram.bot.name}")
	private String botName;

	public static void main(String[] args) {
		SpringApplication.run(Oracle_SprintSightApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new ToDoItemBotController(telegramBotToken, botName, toDoItemService));
			logger.info(BotMessages.BOT_REGISTERED_STARTED.getMessage());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
