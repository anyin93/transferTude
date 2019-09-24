package helloJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.net.ssl.HttpsURLConnection;
import helloJDBC.transferTest;

/*ũ�Ѹ��� ��� �������� ���θ� �ּҸ� ���� �浵 ��ǥ�� ��ȯ
*/
public class test {
	
	 static String className = "oracle.jdbc.driver.OracleDriver"; // MySQL ���� Drvier 
	 static String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
	 static String user = "hellozero"; 
	 static String password = "hellozero";
	 static String sql = null;              // SQL ����

	 
	public static class Tude{
		public  String longtitude; //�浵
		public  String latitude; //����
		public String getLongtitude() {
			return longtitude;
		}
		public void setLongtitude(String longtitude) {
			this.longtitude = longtitude;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public Tude(String longtitude, String latitude) {
			super();
			this.longtitude = longtitude;
			this.latitude = latitude;
		}
		@Override
		public String toString() {
			return "Tude [longtitude=" + longtitude + ", latitude=" + latitude + "]";
		}
		
		
		
		
		
		
	}
	public static void main(String[] args) {
		String addr="";

		for(int i=1;;i++) {

			addr=findAddr(i);
			if(addr.equals("")) {
				break;
			}
			transfer(addr);
			
			
			
		}
		System.out.println("Ż��");
	   

	    
	    
	 
	}
	

	public static String findAddr(int i) {
		Connection con = null;              // DBMS ����
	    PreparedStatement pstmt1 = null; // SQL ����
	    
	    ResultSet rs = null;                   // SELECT ����� ����
	    String addr="";
	    try {
	    	
	    		Class.forName(className);
	    		con=DriverManager.getConnection(url,user,password);
	    		
		    	  //�ּ� ã�� 
			      sql="SELECT ROAD_ADDR FROM (SELECT ROWNUM AS RNUM, T.* FROM pay_info T) A \r\n" + 
			      		"WHERE A.RNUM ="+i;
			       
			      pstmt1 = con.prepareStatement(sql); // SQL ���� ��ü ����

			      
			      rs = pstmt1.executeQuery(); // INSERT, UPDATE, DELETE
			     
			      if(rs.next()) {
			    	  addr=rs.getString("road_addr");
			    	  System.out.print("�ּ�: "+addr+ "		");
			      }
			      else {
			    	  System.out.println("�ּ� ã�� ����"+ "		");
			      }
			      
			      
	    }catch(Exception e){
		      e.printStackTrace();
		    }finally{
		      try{
		        if (rs != null){ rs.close(); }
		      }catch(Exception e){ }
		 
		      try{
		        if (pstmt1 != null){ pstmt1.close(); }
		      }catch(Exception e){ }
		     
		      try{
		        if (con != null){ con.close(); }
		      }catch(Exception e) { }
		    }
		 
	    
	    return addr;
	}
	
	public static void transfer(String addr) {
		Connection con = null;              // DBMS ����
	    PreparedStatement pstmt2 = null; // SQL ����
	    
	    int count=0;                  //����� ����
		try{
		      Class.forName(className); // memory�� Ŭ������ �ε���, ��ü�� �������� ����.
		      con = DriverManager.getConnection(url, user, password ); // MySQL ����
		      
		     
			      
			      //�ش� �ּ� �̾Ƽ� ���� �浵 ��ȯ
			      transferTest Hp=new transferTest();
			      Tude tude= Hp.transfer(addr);
			      
			      
			      String lati=tude.latitude;
			      String longti=tude.longtitude;
			      
			      System.out.println("���浵: "+lati+"  "+longti);

			      
			      sql = "update pay_info A  set LATITUDE=? , LONGTITUDE=? WHERE A.ROAD_ADDR =? ";
			      
			      
			      pstmt2 = con.prepareStatement(sql); // SQL ���� ��ü ����
			      pstmt2.setString(1, lati); 
			      pstmt2.setString(2, longti); 
			      pstmt2.setString(3, addr); 

			      
			      
			      count = pstmt2.executeUpdate(); // INSERT, UPDATE, DELETE
			      // rs = pstmt.executeQuery();    // SELECT 


			      if (count == 1){
			        System.out.println("������Ʈ ����");
			        
			      }else{
			        System.out.println("������Ʈ ����");
			      }
			      //Thread.sleep(1);
			      
			      pstmt2.clearParameters();

		    
		      
		    }catch(Exception e){
		      e.printStackTrace();
		    }finally{
		      
		 
		      try{
		        if (pstmt2 != null){ pstmt2.close(); }
		      }catch(Exception e){ }
		      try{
			        if (pstmt2 != null){ pstmt2.close(); }
			      }catch(Exception e){ }
			 
		      try{
		        if (con != null){ con.close(); }
		      }catch(Exception e){ }
		    }
		
	}
	
}
