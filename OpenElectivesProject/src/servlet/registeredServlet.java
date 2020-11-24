package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registeredServlet
 */
@WebServlet("/registeredServlet")
public class registeredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registeredServlet() {
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
		try
		{
			int cnt=0,c=0;
			Class.forName("oracle.jdbc.OracleDriver");
		    String url = "jdbc:oracle:thin:@localhost:1521:xe";
		    Connection con= DriverManager.getConnection(url, "system", "pooja");
		    Statement stmt = con.createStatement();
			ResultSet rs= stmt.executeQuery("select count(*) from preferencelist");
			if(rs.next())
				cnt=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from studinfo");
			if(rs.next())
				c=rs.getInt(1);
		    pw.println(cnt +" out of "+ c +" students registered the preferences");
		}
		catch(Exception e)
		{
			pw.print(e);	
		}
	}

}
