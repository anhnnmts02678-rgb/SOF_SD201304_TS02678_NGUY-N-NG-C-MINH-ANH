package nhom3.Servlet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom3.Model.Admin;
import nhom3.Model.Employee;
import nhom3.Model.Users;
import nhom3.Utils.HibernateUtils;

class LoginServletTest {

    @ParameterizedTest
    @CsvSource({
        "wrongUser, wrongPass",
        "test@gmail.com, wrongPass",
        "testUser, 12345678"
    })
    void doPost_WhenLoginInvalid_ShouldForwardToLogin(
            String username, String password) throws Exception {

        // Arrange: Tạo các đối tượng giả
        LoginServlet servlet = new LoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        SessionFactory factory = mock(SessionFactory.class);
        org.hibernate.Session hibernateSession =
                mock(org.hibernate.Session.class);

        Query<Admin> adminQuery = mock(Query.class);
        Query<Employee> employeeQuery = mock(Query.class);
        Query<Users> userQuery = mock(Query.class);

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);

        when(request.getRequestDispatcher("login.jsp"))
                .thenReturn(dispatcher);

        when(factory.openSession()).thenReturn(hibernateSession);

        when(hibernateSession.createQuery(anyString(), eq(Admin.class)))
                .thenReturn(adminQuery);

        when(hibernateSession.createQuery(anyString(), eq(Employee.class)))
                .thenReturn(employeeQuery);

        when(hibernateSession.createQuery(anyString(), eq(Users.class)))
                .thenReturn(userQuery);

        when(adminQuery.setParameter(anyString(), any()))
                .thenReturn(adminQuery);
        when(employeeQuery.setParameter(anyString(), any()))
                .thenReturn(employeeQuery);
        when(userQuery.setParameter(anyString(), any()))
                .thenReturn(userQuery);

        when(adminQuery.uniqueResult()).thenReturn(null);
        when(employeeQuery.uniqueResult()).thenReturn(null);
        when(userQuery.uniqueResult()).thenReturn(null);

        // Act: Giả lập HibernateUtils trả về SessionFactory
        try (MockedStatic<HibernateUtils> mocked =
                     mockStatic(HibernateUtils.class)) {

            mocked.when(HibernateUtils::getSessionFactory)
                    .thenReturn(factory);

            servlet.doPost(request, response);
        }

        // Assert: Kiểm tra thông báo và trang được chuyển đến
        verify(request).setAttribute(
                "message", "Sai tài khoản hoặc mật khẩu!");

        verify(dispatcher).forward(request, response);

        verify(hibernateSession).close();
    }
}
