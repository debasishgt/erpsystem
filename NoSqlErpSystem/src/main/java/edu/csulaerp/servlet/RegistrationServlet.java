package edu.csulaerp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.csulaerp.db.DBHandler;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In doGet Registration");
		response.sendRedirect("registration.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBHandler dbHandler = DBHandler.getDBHandler();
		int bRet = dbHandler.registerUser(request.getParameter("passwordsignup"), request.getParameter("emailsignup"));
		
		if(bRet == 1){
			System.out.println("User Registered");
		}
		else{
			//Error Handling
			RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
            view.forward(request, response);
		}
		
	}

}
