package helloJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.net.ssl.HttpsURLConnection;
import helloJDBC.transferTest;

/*크롤링한 디비 데이터의 도로명 주소를 위도 경도 좌표로 변환
*/
public class test {
	
	 static String className = "oracle.jdbc.driver.OracleDriver"; // MySQL 연결 Drvier 
	 static String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
	 static String user = "hellozero"; 
	 static String password = "hellozero";
	 static String sql = null;              // SQL 문장

	 
	public static class Tude{
		public  String longtitude; //경도
		public  String latitude; //위도
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
		System.out.println("탈출");
	   

	    
	    
	 
	}
	

	public static String findAddr(int i) {
		Connection con = null;              // DBMS 연결
	    PreparedStatement pstmt1 = null; // SQL 실행
	    
	    ResultSet rs = null;                   // SELECT 결과를 저장
	    String addr="";
	    try {
	    	
	    		Class.forName(className);
	    		con=DriverManager.getConnection(url,user,password);
	    		
		    	  //주소 찾기 
			      sql="SELECT ROAD_ADDR FROM (SELECT ROWNUM AS RNUM, T.* FROM pay_info T) A \r\n" + 
			      		"WHERE A.RNUM ="+i;
			       
			      pstmt1 = con.prepareStatement(sql); // SQL 실행 객체 생성

			      
			      rs = pstmt1.executeQuery(); // INSERT, UPDATE, DELETE
			     
			      if(rs.next()) {
			    	  addr=rs.getString("road_addr");
			    	  System.out.print("주소: "+addr+ "		");
			      }
			      else {
			    	  System.out.println("주소 찾기 에러"+ "		");
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
		Connection con = null;              // DBMS 연결
	    PreparedStatement pstmt2 = null; // SQL 실행
	    
	    int count=0;                  //결과를 저장
		try{
		      Class.forName(className); // memory로 클래스를 로딩함, 객체는 생성하지 않음.
		      con = DriverManager.getConnection(url, user, password ); // MySQL 연결
		      
		     
			      
			      //해당 주소 뽑아서 위도 경도 변환
			      transferTest Hp=new transferTest();
			      Tude tude= Hp.transfer(addr);
			      
			      
			      String lati=tude.latitude;
			      String longti=tude.longtitude;
			      
			      System.out.println("위경도: "+lati+"  "+longti);

			      
			      sql = "update pay_info A  set LATITUDE=? , LONGTITUDE=? WHERE A.ROAD_ADDR =? ";
			      
			      
			      pstmt2 = con.prepareStatement(sql); // SQL 실행 객체 생성
			      pstmt2.setString(1, lati); 
			      pstmt2.setString(2, longti); 
			      pstmt2.setString(3, addr); 

			      
			      
			      count = pstmt2.executeUpdate(); // INSERT, UPDATE, DELETE
			      // rs = pstmt.executeQuery();    // SELECT 


			      if (count == 1){
			        System.out.println("업데이트 성공");
			        
			      }else{
			        System.out.println("업데이트 실패");
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
