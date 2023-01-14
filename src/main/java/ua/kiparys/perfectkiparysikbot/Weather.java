package ua.kiparys.perfectkiparysikbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    public SendMessage weather(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Яка саме область Вас цікавить?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

        var buttonKir = new InlineKeyboardButton();
        buttonKir.setText("Кіровоградська");
        buttonKir.setCallbackData("KIR");

        var buttonOds = new InlineKeyboardButton();
        buttonOds.setText("Одеська");
        buttonOds.setCallbackData("ODS");

        var buttonVin = new InlineKeyboardButton();
        buttonVin.setText("Вінницька");
        buttonVin.setCallbackData("VIN");

        var buttonVol = new InlineKeyboardButton();
        buttonVol.setText("Волинська");
        buttonVol.setCallbackData("VOL");

        var buttonDnp = new InlineKeyboardButton();
        buttonDnp.setText("Дніпропетровська");
        buttonDnp.setCallbackData("DNP");

        rowInLine1.add(buttonKir);
        rowInLine1.add(buttonOds);
        rowInLine1.add(buttonVin);
        rowInLine1.add(buttonVol);
        rowInLine1.add(buttonDnp);

        rowsInLine.add(rowInLine1);
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        var buttonDon = new InlineKeyboardButton();
        buttonDon.setText("Донецька");
        buttonDon.setCallbackData("DON");

        var buttonJht = new InlineKeyboardButton();
        buttonJht.setText("Житомирська");
        buttonJht.setCallbackData("JHT");

        var buttonZak = new InlineKeyboardButton();
        buttonZak.setText("Закарпатська");
        buttonZak.setCallbackData("ZAK");

        var buttonZap = new InlineKeyboardButton();
        buttonZap.setText("Запорізька");
        buttonZap.setCallbackData("ZAP");

        var buttonIvf = new InlineKeyboardButton();
        buttonIvf.setText("Івано-Франківська");
        buttonIvf.setCallbackData("IVF");

        rowInLine2.add(buttonDon);
        rowInLine2.add(buttonJht);
        rowInLine2.add(buttonZak);
        rowInLine2.add(buttonZap);
        rowInLine2.add(buttonIvf);

        rowsInLine.add(rowInLine2);
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();

        var buttonKyv = new InlineKeyboardButton();
        buttonKyv.setText("Київська");
        buttonKyv.setCallbackData("KYV");

        var buttonLuh = new InlineKeyboardButton();
        buttonLuh.setText("Луганська");
        buttonLuh.setCallbackData("LUH");

        var buttonLvv = new InlineKeyboardButton();
        buttonLvv.setText("Львівська");
        buttonLvv.setCallbackData("LVV");

        var buttonMyk = new InlineKeyboardButton();
        buttonMyk.setText("Миколаївська");
        buttonMyk.setCallbackData("MYK");

        var buttonPol = new InlineKeyboardButton();
        buttonPol.setText("Полтавська");
        buttonPol.setCallbackData("POL");

        rowInLine3.add(buttonKyv);
        rowInLine3.add(buttonLuh);
        rowInLine3.add(buttonLvv);
        rowInLine3.add(buttonMyk);
        rowInLine3.add(buttonPol);

        rowsInLine.add(rowInLine3);
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();

        var buttonRiv = new InlineKeyboardButton();
        buttonRiv.setText("Рівненська");
        buttonRiv.setCallbackData("RIV");

        var buttonSum = new InlineKeyboardButton();
        buttonSum.setText("Сумська");
        buttonSum.setCallbackData("SUM");

        var buttonTer = new InlineKeyboardButton();
        buttonTer.setText("Тернопільська");
        buttonTer.setCallbackData("TER");

        var buttonHar = new InlineKeyboardButton();
        buttonHar.setText("Харківська");
        buttonHar.setCallbackData("HAR");

        var buttonHer = new InlineKeyboardButton();
        buttonHer.setText("Херсонська");
        buttonHer.setCallbackData("HER");

        rowInLine4.add(buttonRiv);
        rowInLine4.add(buttonSum);
        rowInLine4.add(buttonTer);
        rowInLine4.add(buttonHar);
        rowInLine4.add(buttonHer);

        rowsInLine.add(rowInLine4);
        List<InlineKeyboardButton> rowInLine5 = new ArrayList<>();

        var buttonKhm = new InlineKeyboardButton();
        buttonKhm.setText("Хмельницька");
        buttonKhm.setCallbackData("KHM");

        var buttonChk = new InlineKeyboardButton();
        buttonChk.setText("Черкаська");
        buttonChk.setCallbackData("CHK");

        var buttonChn = new InlineKeyboardButton();
        buttonChn.setText("Чернівецька");
        buttonChn.setCallbackData("CHN");

        var buttonChg = new InlineKeyboardButton();
        buttonChg.setText("Чернігівська");
        buttonChg.setCallbackData("CHG");

        var buttonCrm = new InlineKeyboardButton();
        buttonCrm.setText("Крим");
        buttonCrm.setCallbackData("CRM");

        rowInLine5.add(buttonKhm);
        rowInLine5.add(buttonChk);
        rowInLine5.add(buttonChn);
        rowInLine5.add(buttonChg);
        rowInLine5.add(buttonCrm);

        rowsInLine.add(rowInLine5);


        markupInLine.setKeyboard(rowsInLine);

        message.setReplyMarkup(markupInLine);

        return message;
    }

    public String weatherSender(String regionToCheck){
        switch (regionToCheck) {
            case "KIR":
                return "https://ua.sinoptik.ua/погода-кропивницький";
            case "ODS":
                return "https://ua.sinoptik.ua/погода-одеса";
            case "VIN":
                return "https://ua.sinoptik.ua/погода-вінниця";
            case "VOL":
                return "https://ua.sinoptik.ua/погода-луцьк";
            case "DNP":
                return "https://ua.sinoptik.ua/погода-дніпро";
            case "DON":
                return "https://ua.sinoptik.ua/погода-донецьк";
            case "JHT":
                return "https://ua.sinoptik.ua/погода-житомир";
            case "ZAK":
                return "https://ua.sinoptik.ua/погода-ужгород";
            case "ZAP":
                return "https://ua.sinoptik.ua/погода-запоріжжя";
            case "IVF":
                return "https://ua.sinoptik.ua/погода-івано-франківськ";
            case "KYV":
                return "https://ua.sinoptik.ua/погода-київ";
            case "LUH":
                return "https://ua.sinoptik.ua/погода-луганськ";
            case "LVV":
                return "https://ua.sinoptik.ua/погода-львів";
            case "MYK":
                return "https://ua.sinoptik.ua/погода-миколаїв";
            case "POL":
                return "https://ua.sinoptik.ua/погода-полтава";
            case "RIV":
                return "https://ua.sinoptik.ua/погода-рівне";
            case "SUM":
                return "https://ua.sinoptik.ua/погода-суми";
            case "TER":
                return "https://ua.sinoptik.ua/погода-тернопіль";
            case "HAR":
                return "https://ua.sinoptik.ua/погода-харків";
            case "HER":
                return "https://ua.sinoptik.ua/погода-херсон";
            case "KHM":
                return "https://ua.sinoptik.ua/погода-хмельницький";
            case "CHK":
                return "https://ua.sinoptik.ua/погода-черкаси";
            case "CHN":
                return "https://ua.sinoptik.ua/погода-чернівці";
            case "CHG":
                return "https://ua.sinoptik.ua/погода-чернігів";
            case "CRM":
                return "https://ua.sinoptik.ua/погода-сімферополь";
            default:
                return "Невідомий регіон";
        }
    }
}
