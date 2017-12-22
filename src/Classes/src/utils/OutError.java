package utils;

import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * класс для обработки ошибок
 */
public class OutError {

    /**
     * выводит ошибки в формате JSON
     * @param strArr массив строк, содеражащих описание ошибок
     * @param out поток текстового вывода
     */
    public static void printJSONError(LinkedList<String> strArr, PrintWriter out){
        out.print("[");
        for(int i = 0; i < strArr.size(); i++){
            out.print("\"" );//+ i + "\": \"" );
            out.print(strArr.get(i));
            out.print("\"");
            if (i + 1 != strArr.size()) out.print(",");
        }
        out.print("]");
    }

    /**
     * выводит текст ошибки на экран
     * @param out поток текстового вывода
     * @param path путь к директории проекта
     * @param error сообщение об ошибке
     */
    public static void printError(PrintWriter out, String path, String error){
        out.println("" +
                "<!DOCTYPE html>\n" +
                "<html lang='ru'>\n" +
                "<head>\n" +
                "    <meta charset='utf-8' name='viewport' content='user-scalable=no, width=device-width, height=device-height'/>\n" +
                "    <link rel='stylesheet' TYPE='text/css' href='" + path +"/css/index.css'>\n" +
                "    <title>Ошибка</title>\n" +
                "</head>");
        out.println("" +
                "<body>" +
                "<div id='error'>\n" +
                "    <p>\n" +
                         error +
                "    </p>\n" +
                "</div>" +
                "</body>" +
                "</html");
        out.close();
    }
}
