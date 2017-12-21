package utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * класс для шифрования данных
 */
public class Crypt {

    /**
     * соль для генерирования токена
     */
    private static final String saltForToken = "h1hny53af1a21f3aw5";

    /**
     * первая соль для генерированя хеша
     */
    private static final String saltForPass1 = "ancf5sa3d5cv6s23v2";

    /**
     * вторая соль для генерирования хеша
     */
    private static final String saltForPass2 = "d1g1s2ge5j4e9a61vs";

    /**
     * @param str строка, от которой берется md5
     * @return строку содержащуюю хеш md5
     */
    private static String md5(String str) {
        MessageDigest m;
        try{
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(str.getBytes("utf-8"));
            String s2 = new BigInteger(1, m.digest()).toString(16);
            StringBuilder sb = new StringBuilder(32);
            for (int i = 0, count = 32 - s2.length(); i < count; i++) {
                sb.append("0");
            }
            return sb.append(s2).toString();
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * возвращает хеш от строки s
     * @param s строка, от которой берется хеш-функция
     * @return хеш от строки
     */
    public static String getHash(String s) {
        return md5(md5(s + saltForPass1) + saltForPass2);
    }

    /**
     * генерирует токен для авторизации
     * @param login логин пользователя
     * @param date дата регистрации пользователя
     * @return токен
     */
    public static String getToken(String login, String date) {
        return md5(login + date + saltForToken);
    }
}
