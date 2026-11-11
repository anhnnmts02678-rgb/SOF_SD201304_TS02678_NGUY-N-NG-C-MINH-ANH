package nhom3.Servlet;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom3.Dao.GiohangCTDao;
import nhom3.Model.Users;


@WebServlet("/RemoveCartServlet")
public class RemoveCartServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session=request.getSession();


        Users user=
            (Users)session.getAttribute("user");


        if(user==null){

            response.sendRedirect("login.jsp");
            return;
        }



        int maGHCT;


        try{

            maGHCT=Integer.parseInt(
                    request.getParameter("maGHCT")
            );

        }catch(Exception e){

            response.sendRedirect("CartServlet");
            return;
        }



        GiohangCTDao dao=
                new GiohangCTDao();



        dao.delete(maGHCT);



        response.sendRedirect(
                "CartServlet?message=Xoa+thanh+cong"
        );

    }



}