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
 * Servlet implementation class allotmentServlet
 */
@WebServlet("/allotmentServlet")
public class allotmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public allotmentServlet() {
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
			int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0;
			Class.forName("oracle.jdbc.OracleDriver");
		    String url = "jdbc:oracle:thin:@localhost:1521:xe";
		    Connection con= DriverManager.getConnection(url, "system", "pooja");
		    Statement stmt = con.createStatement();
		    Statement stmt1 = con.createStatement();
		    ResultSet rs= stmt.executeQuery("select count(*) from ce");
			if(rs.next())
				c1=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from nm");
			if(rs.next())
				c2=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from pm");
			if(rs.next())
				c3=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from rb");
			if(rs.next())
				c4=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from ja");
			if(rs.next())
				c5=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from db");
			if(rs.next())
				c6=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from pe");
			if(rs.next())
				c7=rs.getInt(1);
			rs= stmt.executeQuery("select count(*) from dc");
			if(rs.next())
				c8=rs.getInt(1);
			rs= stmt.executeQuery("select * from preferencelist");
			while(rs.next())
			{
				String hno = rs.getString(1);
				String status = rs.getString(11);
				String sql = "UPDATE preferencelist SET status ='A' WHERE htno='"+hno+"'";
				int p=1;
				while(status.contentEquals("N"))
				{
					for(int i=2;i<10;i++)
					{
					if(rs.getInt(i)==p)
					{
						//pw.println("done");
						if(i==2 && c1<2)
						{
						   stmt1.executeUpdate("insert into ce values('"+rs.getString(1)+"')");
						   c1++;
					       stmt1.executeUpdate(sql);
					       status = "A";
					       continue;
						}
						if(i==3 && c2<2)
						{
							   stmt1.executeUpdate("insert into nm values('"+rs.getString(1)+"')");
							   c2++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==4 && c3<2) {
							   stmt1.executeUpdate("insert into pm values('"+rs.getString(1)+"')");
							   c3++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==5 && c4<2) {
							   stmt1.executeUpdate("insert into rb values('"+rs.getString(1)+"')");
							   c4++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==6 && c5<2) {
							   stmt1.executeUpdate("insert into ja values('"+rs.getString(1)+"')");
							   c5++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==7 && c6<2) {
							   stmt1.executeUpdate("insert into db values('"+rs.getString(1)+"')");
						       c6++;	
						       stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==8 && c7<2) {
							   stmt1.executeUpdate("insert into pe values('"+rs.getString(1)+"')");
							   c7++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
						else if(i==9 && c8<2) {
							   stmt1.executeUpdate("insert into dc values('"+rs.getString(1)+"')");
							   c8++;
							   stmt1.executeUpdate(sql);
						       status = "A";
						       continue;
						}
					}
					}
					p++;
				}
			}
		}
		catch(Exception e)
		{
			pw.print(e);	
		}
		pw.print("courses allocated succesfully");
	}
}
