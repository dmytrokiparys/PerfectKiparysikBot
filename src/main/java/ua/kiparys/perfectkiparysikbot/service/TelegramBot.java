package ua.kiparys.perfectkiparysikbot.service;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.kiparys.perfectkiparysikbot.Joke;
import ua.kiparys.perfectkiparysikbot.Movie;
import ua.kiparys.perfectkiparysikbot.config.BotConfig;
import ua.kiparys.perfectkiparysikbot.model.User;
import ua.kiparys.perfectkiparysikbot.model.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;

    final BotConfig config;

    final static String ERROR_TEXT = "Error occurred: ";

    final static String HELP_TEXT = "Цей бот був створений, щоб зробити ваше життя трішечки кращим \n\n" +
            "Натисніть /start щоб побачити повідомлення з привітанням\n\n" +
            "Натисніть /mydata щоб побачити, які дані про вас зберігаються в пам'яті бота\n\n" +
            "Натисніть /deletedata to delete data about yourself\n\n" +
            "Натисніть /help щоб побачити це повідомлення знову";



    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> list_of_commands = new ArrayList<>();
        list_of_commands.add(new BotCommand("/start", "привітальне повідомлення"));
        list_of_commands.add(new BotCommand("/mydata", "подивитися дані"));
        list_of_commands.add(new BotCommand("/deletedata", "видалити всі дані про мене"));
        list_of_commands.add(new BotCommand("/help", "інфо по командам"));

        try{
            this.execute(new SetMyCommands(list_of_commands, new BotCommandScopeDefault(), null));
        }catch (TelegramApiException e){
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(messageText.contains("/send") && config.getOwner() == chatId){
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userRepository.findAll();
                for (User user : users){
                sendMessage(user.getChatId(), textToSend);
                }
            }else if(messageText.contains("Подивитись")) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                movieSearch(chatId, textToSend);
            }else
                switch (messageText) {
                    case "/start":
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        sendMessage(chatId, HELP_TEXT);
                        break;
                    case "/mydata":
                        var user = userRepository.findById(chatId);
                        sendMessage(chatId, String.valueOf(user));
                        break;
                    case "Допомога":
                        sendMessage(chatId, HELP_TEXT);
                        break;
                    case "/deletedata":
                        userRepository.deleteById(chatId);
                        sendMessage(chatId, "Всі Ваші дані були видалені.");
                        break;
                    case "Випадковий жарт":
                        Joke joke = new Joke();
                        sendMessage(chatId, joke.getJoke());
                        break;
                    case "Що б його глянути...":
                        Movie movie = new Movie();
                        String recommendedMovie = movie.getMovie();
                        sendMessage(chatId, "Рекомендуєм подивитися ось такий фільм:\n\n" + recommendedMovie + "\n\nАбо просто напишіть \"Подивитись\" і назву того фільма, який вас цікавить.");
                        movieSearch(chatId, recommendedMovie);
                        break;
                    case "Погода":
                        weather(chatId);
                        break;
                    case "Мапа тривог":
                        sendMessage(chatId, "https://alerts.in.ua");
                        break;
                    default:
                        sendMessage(chatId, "Вибачте, невідома команда");
                }
            } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals("ODS")){
                String text = "https://ua.sinoptik.ua/погода-одеса";
                executeEditMessageText(text, chatId, messageId);
            }else if(callbackData.equals("KIR")){
                String text = "https://ua.sinoptik.ua/погода-кропивницький";
                executeEditMessageText(text, chatId, messageId);
            }
        }
    }

    private void weather(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Яка саме область Вас цікавить?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var buttonKir = new InlineKeyboardButton();   //пока только 2 области
        buttonKir.setText("Кіровоградська");
        buttonKir.setCallbackData("KIR");

        var buttonOds = new InlineKeyboardButton();
        buttonOds.setText("Одеська");
        buttonOds.setCallbackData("ODS");

        rowInLine.add(buttonKir);
        rowInLine.add(buttonOds);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);

        message.setReplyMarkup(markupInLine);

        executeMessage(message);
    }

    private void registerUser(Message msg) {

        if(userRepository.findById(msg.getChatId()).isEmpty()){
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved " + user);
        }
    }

    private void startCommandReceived(long chatId, String name){
        String answer = EmojiParser.parseToUnicode("Привіт, " + name + ", радий тебе тут бачити!" + ":tada:");
        log.info("Replied to user "+ name);
        sendMessage(chatId, answer);
    }

    private void movieSearch(long chatId, String movieName){
        String[] words = movieName.split(" ");
        String request = "https://rezka.ag/search/?do=search&subaction=search&q=";
        for(String word : words){
            request += "+" + word;
        }
        sendMessage(chatId, request);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Погода");
        row.add("Випадковий жарт");
        //row.add("help");                 //добавить кнопочек с функциями
        keyboardRows.add(row);

        row = new KeyboardRow();
        //row.add("register");
        row.add("Мапа тривог");
        row.add("Що б його глянути...");
        //keyboardRows.add(row);
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);

    }

    private void executeEditMessageText(String text, long chatId, long messageId){

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);   //возможно String.valueof()
        message.setText(text);
        message.setMessageId((int)messageId);
        try{
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void executeMessage(SendMessage message){
        try{
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

}
