package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		try {
		String hallno = request.getParameter("htno");
		String pass = request.getParameter("pwd");
		String branch="",s="";
		int i=1;
		boolean status=false;  
		Class.forName("oracle.jdbc.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    Connection con= DriverManager.getConnection(url, "system", "pooja");
	    Statement stmt = con.createStatement();
		ResultSet rs= stmt.executeQuery("select * from studinfo where htno='"+hallno+"'and pwd='"+pass+"' ");
		status=rs.next();
		if(status)
		{
		    pw.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"home.css\">\r\n" + 
		    			"</head><body><h2>Subject Registration Form</h2><h4>Student Info<h4>"
		    			+"<table width=100%><tr><td>Name: "+rs.getString(1)+"</td><td>HTNO: "+rs.getString(2)+"</td></tr>"
		    			+ "<tr><td>Branch: "+rs.getString(3)+"</td><td>Semester: "+rs.getInt(4)+"</td></tr>"
		    			+"<tr><td>Section: "+rs.getString(5)+"</td></tr>"
		    			+ "</table>"
		    			+ "</body></html>");
		    	branch=rs.getString(3);
		    	s=rs.getString(7);
		}
		else
		{
		    pw.println("unsuccessful login...try again");
		    return;
		}
        String sta="N";
		if(s.equals(sta))
		{
		int cnt=1,x=0;
		char sample='a';
	    ResultSet num =stmt.executeQuery("select count(sdept) from oesubjects where sdept!='"+branch+"' ");
	    if(num.next())
	         cnt=num.getInt(1);
		rs= stmt.executeQuery("select * from oesubjects where sdept!='"+branch+"' ");
		    
		String content="<br><html><head><script src=\"validation.js\"></script><link rel=\"stylesheet\" type=\"text/css\" href=\"home.css\">\r\n" + 
	    			"<form method=\"post\" action=\"servletsubjectpage\" onsubmit=\"return validateForm()\"><table border=1 width=100%><tr><th>S.NO</th><th>Course Name</th><th>Subject preference no.</th><th>Dept.</th><th>No.of credits</th></tr>";
		while(rs.next())
		{
		    content+="<tr><td>"+i+"</td><td>"+rs.getString(1)+"</td>";
		    content+="<td><select name="+sample+" id="+sample+"><optgroup label="+"preference"+">";
		    sample++;
		    x=1;
		    while(x<=cnt) {
		    	content+="<option>"+x+"</option>";
		    	x++;
		    }
		    content+="</optgroup></td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td></tr>";
		    i++;
		}
		content+="</table><input type=\"submit\" value=\"SUBMIT\"><input type=\"hidden\" name=\"count\" id=\"count\" value="+cnt+"><input type=\"hidden\" name=\"htno\" value="+hallno+"><input type=\"hidden\" name=\"pwd\" value="+pass+"></form></body></html>";
		pw.println(content);
		}
		else
		{
			 RequestDispatcher rd=request.getRequestDispatcher("servletsubjectpage");  
		     rd.forward(request, response);  
		}
		}
		catch(Exception e)
		{
			pw.print(e);	
		}
	}
}
