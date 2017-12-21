package utils;

import java.io.PrintWriter;

/**
 * класс для обработки ошибок
 */
public class OutError {

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
