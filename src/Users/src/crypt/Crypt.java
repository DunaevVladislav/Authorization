package crypt;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {

    private static final String saltForToken = "h1hny53af1a21f3aw5";
    private static final String saltForPass1 = "ancf5sa3d5cv6s23v2";
    private static final String saltForPass2 = "d1g1s2ge5j4e9a61vs";

    private static String md5(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(str.getBytes("utf-8"));
        String s2 = new BigInteger(1, m.digest()).toString(16);
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0, count = 32 - s2.length(); i < count; i++) {
            sb.append("0");
        }
        return sb.append(s2).toString();
    }

    public static String getHash(String s)throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(md5(s + saltForPass1) + saltForPass2);
    }

    public static String getToken(String login, String date) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(login + date + saltForToken);
    }
}
