package cz.literak.sandbox.so.register;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Date: 5. 4. 2014
 */
public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        // todo check parameters

        User user = new User(name, phone);
        DAO dao = new DAO();
        dao.saveUser(user);
        request.setAttribute("USER", user);// consider using session
        // TODO handle errors

        RequestDispatcher dispatcher = request.getRequestDispatcher("register/index.jsp");
        dispatcher.forward(request, response);
    }
}
