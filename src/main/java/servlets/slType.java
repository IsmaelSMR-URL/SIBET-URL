package servlets;

import data.dtType;
import entities.tables.typeTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/slType")
public class slType extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public slType() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int opc = 0;
        opc = Integer.parseInt(request.getParameter("type"));
        typeTable type = new typeTable();
        dtType dtt = new dtType();

        type.setTypeId(Integer.parseInt(request.getParameter("typeId")));
        type.setName(request.getParameter("name"));

        switch (opc) {
            case 1:
                try{
                    if(dtt.addType(type)){
                        response.sendRedirect("/inventory/type.jsp?msj=1");
                    }else{
                        response.sendRedirect("/inventory/type.jsp?msj=2");
                    }
                }catch (Exception e){
                    System.out.println("ERROR AL AGREGAR (Servlet) TYPE: " + e.getMessage());
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }
}
