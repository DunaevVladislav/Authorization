package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Проверка введеных данных на валидность
 */
public class CheckData {

    /**
     * регулярное выражения для проверки логина и пароля
     */
    private static final Pattern pattern = Pattern.compile("\\w{3,32}");

    /**
     * регулярное выражение для проверки email
     */
    private static final Pattern patternEmail = Pattern.compile("(\\w+[\\.-]?\\w+)+@(\\w+[\\.-]?\\w+)+[\\.][a-z]{2,4}");

    /**
     * проверяет строку s, согласно регулрному выражению pattern
     * @param s проверяемая строка
     * @return соотвествует ли строку регулярному выражению pattern
     */
    public static boolean check(String s){
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * проверяет строку s, согласно регулрному выражению patternEmail
     * @param s проверяемая строка
     * @return соотвествует ли строку регулярному выражению patternEmail
     */
    public static boolean checkEmail(String s){
        Matcher matcher = patternEmail.matcher(s);
        return matcher.matches();
    }

}
