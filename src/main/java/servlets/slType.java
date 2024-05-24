package servlets;

import data.dtType;
import entities.tables.typeTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "slType", value = "/slType")
public class slType extends HttpServlet {
    String add ="inventory/type-forms/create-type-form.jsp";
    typeTable t = new typeTable();
    dtType dt = new dtType();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acceso = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("add")) {
            acceso = add;
        }else if (action.equalsIgnoreCase("Agregar")) {
            Integer id = Integer.parseInt(request.getParameter("txtId"));
            String name = request.getParameter("txtName");
            t.setTypeId(id);
            t.setName(name);
            dt.add(t);
        }
        request.getRequestDispatcher(acceso).forward(request, response);
    }

    public void destroy() {
    }
}