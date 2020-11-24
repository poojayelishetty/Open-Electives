package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servletsubjectpage
 */
@WebServlet("/servletsubjectpage")
public class servletsubjectpage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletsubjectpage() {
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
		String hallno = request.getParameter("htno");
		String pass = request.getParameter("pwd");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String branch="";
		int cnt=1;
		char sample='a';
		String st;
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
	       st=rs.getString(7);
		}
		else
		{
		    pw.println("unsuccessful login...try again");
		    return;
		}
		ResultSet num =stmt.executeQuery("select count(sdept) from oesubjects where sdept!='"+branch+"' ");
		if(num.next())
			cnt = num.getInt(1);
		int i=0;
		ResultSet rs1= stmt.executeQuery("select * from oesubjects where sdept!='"+branch+"' ");
		rs1.next();
		String sta="N";
		if(st.equals(sta))
		{
		String sqlPstmt ="insert into preferencelist(";
		for(i=0;i<cnt;i++)
		{
			sqlPstmt+=rs1.getString(4)+",";
			rs1.next();
		}
		sqlPstmt+="htno,rdatetime,status) values(";
		for(i=0;i<cnt;i++)
		{
		    sqlPstmt+="?,";
		}
		sqlPstmt+="?,?,?)";
		PreparedStatement ps = con.prepareStatement(sqlPstmt);
		i=0;
		while(i<cnt)
		{
			String s=Character.toString(sample);
			//pw.println(request.getParameter(s));
			int v=Integer.parseInt(request.getParameter(s));
			ps.setInt(i+1,v);
			sample++;
			i++;
		}
		ps.setString(i+1, hallno);
		ps.setTimestamp(i+2, timestamp);
		ps.setString(i+3, "N");
		ps.executeUpdate();
		String sql = "UPDATE studinfo " +
                "SET oestatus ='Y' WHERE htno='"+hallno+"'and pwd='"+pass+"'";
        stmt.executeUpdate(sql);
		}
		ResultSet rs2= stmt.executeQuery("select * from preferencelist where htno='"+hallno+"'");
        String content="<br><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"home.css\">\r\n" + 
	    			"<form action=\"Login.html\"method=\"post\"><table border=1 width=100%><tr><th>S.NO</th><th>Course Name</th><th>Subject preference no.</th><th>Dept.</th><th>No.of credits</th></tr>";
        status=rs2.next();
        int k=0;
        int arr[] = new int[20];  
        for(i=2;i<10;i++)
        {
        	int p=rs2.getInt(i);
        	if(p!=0)
        		arr[k++]=p;
        }
        i=1;
        //pw.println(arr[3]);
		if(status)
		{
			ResultSet rs3= stmt.executeQuery("select * from oesubjects where sdept!='"+branch+"' ");
			while(rs3.next())
			{
				String s=rs3.getString(4);
			    content+="<tr><td>"+i+"</td><td>"+rs3.getString(1)+"</td>";
			    content+="<td>"+arr[i-1];
			    content+="</td><td>"+rs3.getString(2)+"</td><td>"+rs3.getString(3)+"</td>";
			    i++;
			}
			content+="</table><input type=\"submit\" value=\"LOGOUT\"></form></body></html>";	
		}
		pw.println(content);
		}
		catch(Exception e)
		{
			pw.print(e);	
		}
		
	}
}
