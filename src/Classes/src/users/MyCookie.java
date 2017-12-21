package users;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * класс для работы с сессиями и куки
 */
public class MyCookie {

    /**
     * максимальное время хранения куки
     */
    private static final int maxAgeCookie = 30*24*60*60;
    /**
     * запрос с текущей страницы
     */
    private HttpServletRequest request;
    /**
     * ответ на запрос данной страницы
     */
    private HttpServletResponse response;

    /**
     * констуктор
     * @param request запрос на странице
     * @param response ответ на данный запрос
     */
    public MyCookie(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * пытается найти информацию о пользователе в куках
     * @return пользователя, если информация была найдена и была акутуальной, null в противном случае
     */
    public User getUser(){
        HttpSession session = request.getSession(true);
        if (!session.isNew()) {
            try {
                Integer id = (Integer)session.getAttribute("id");
                String token = (String)session.getAttribute("token");
                if (id != null && token != null){
                    User user = new User(id);
                    if (user.getToken().equals(token)) return user;
                }
            } catch (Exception ignored){}
        }
        Cookie[] cookies = request.getCookies();
        Integer id = null;
        String token = null;
        for(Cookie c:cookies){
            if (c.getName().equals("id")) id = Integer.parseInt(c.getValue());
            if (c.getName().equals("token")) token = c.getValue();
        }
        if (id != null && token != null){
            User user = null;
            try {
                user = new User(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user != null && user.getToken().equals(token)) return user;
        }
        return null;
    }

    /**
     * добавляет в куки информацию о пользователе
     * @param user пользователь
     */
    public void setCookie(User user){
        Cookie cookieID = new Cookie("id", user.getId().toString());
        cookieID.setMaxAge(maxAgeCookie);
        Cookie cookieToken = new Cookie("token", user.getToken());
        cookieToken.setMaxAge(maxAgeCookie);
        response.addCookie(cookieID);
        response.addCookie(cookieToken);
    }

    /**
     * удаляет из куки информацию о пользователе
     */
    private void removeCookie(){
        Cookie cookieID = new Cookie("id", "");
        cookieID.setMaxAge(0);
        Cookie cookieToken = new Cookie("token", "");
        cookieToken.setMaxAge(0);
        response.addCookie(cookieID);
        response.addCookie(cookieToken);
    }

    /**
     * открывает сессию пользователя
     * @param user пользователь
     */
    public void setSession(User user){
        HttpSession session = request.getSession(true);
        session.setAttribute("id", user.getId());
        session.setAttribute("token", user.getToken());

    }

    /**
     * закрывает сесиию пользовтеля
     */
    private void removeSession(){
        request.getSession(true).invalidate();
    }

    /**
     * удаляет информацию о пользователе из куки и закрывает сессию
     */
    public void remove(){
        removeCookie();
        removeSession();
    }

}
