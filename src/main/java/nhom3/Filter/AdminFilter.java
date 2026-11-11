package nhom3.Filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AdminFilter implements Filter{

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;

        HttpSession session=req.getSession(false);

        if(session!=null &&
           session.getAttribute("admin")!=null){

            chain.doFilter(request,response);

        }else{

            res.sendRedirect(req.getContextPath()+"/login.jsp");

        }

    }

}