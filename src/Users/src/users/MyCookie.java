package users;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyCookie {

    HttpServletRequest request = null;
    HttpServletResponse response = null;

    private static final int maxAgeCookie = 30*24*60*60;

    public MyCookie(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public User getUser() throws Exception{
        HttpSession session = request.getSession(true);
        if (!session.isNew()) {
            try {
                Integer id = (Integer)session.getAttribute("id");
                String token = (String)session.getAttribute("token");
                if (id != null && token != null){
                    User user = new User(id);
                    if (user.getToken().equals(token)) return user;
                }
            } catch (Exception ignored){};
        }
        Cookie[] cookies = request.getCookies();
        Integer id = null;
        String token = null;
        for(Cookie c:cookies){
            if (c.getName().equals("id")) id = Integer.parseInt(c.getValue());
            if (c.getName().equals("token")) token = c.getValue();
        }
        if (id != null && token != null){
            User user = new User(id);
            if (user.getToken().equals(token)) return user;
        }
        return null;
    }

    public void setCookie(User user) throws Exception{
        Cookie cookieID = new Cookie("id", user.getId().toString());
        cookieID.setMaxAge(maxAgeCookie);
        Cookie cookieToken = new Cookie("token", user.getToken());
        cookieToken.setMaxAge(maxAgeCookie);
        response.addCookie(cookieID);
        response.addCookie(cookieToken);
    }

    private void removeCookie()throws Exception{
        Cookie cookieID = new Cookie("id", "");
        cookieID.setMaxAge(0);
        Cookie cookieToken = new Cookie("token", "");
        cookieToken.setMaxAge(0);
        response.addCookie(cookieID);
        response.addCookie(cookieToken);
    }

    public void setSession(User user) throws Exception{
        HttpSession session = request.getSession(true);
        session.setAttribute("id", user.getId());
        session.setAttribute("token", user.getToken());

    }

    private void removeSession(){
        request.getSession(true).invalidate();
    }

    public void remove()throws Exception{
        removeCookie();
        removeSession();
    }


}
