package helloJDBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import helloJDBC.test.Tude;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;;
public class transferTest {
	public static Tude transfer(String address) throws IOException {
		
		String auth = "KakaoAK " + "ceda06bf85d11c526ca076e35ddaf7ee";
//		String address="서울 종로구 삼청로 91";
		
		String parameter = String.format("query=%s", URLEncoder.encode(address,"UTF-8"));
		
		
		
		
		URL link= new URL("https://dapi.kakao.com/v2/local/search/address.json"+"?"+ parameter);

		try {
		HttpsURLConnection hc = (HttpsURLConnection)link.openConnection();

		hc.setRequestMethod("GET");
		hc.setRequestProperty("Authorization", auth);
        int responseCode = hc.getResponseCode();
        
        StringBuilder sb = new StringBuilder();
        if(responseCode==200) { // 정상 호출
            BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream(),"UTF-8"));
            
            System.out.println("정상");            
            String line;
            String result="";
			while ((line = br.readLine()) != null) {
				result=result.concat(line);
				System.out.println(result);
			}
			
//			System.out.println("" + sb.toString());
			
			
			
			//json으로 변환
			JSONParser jsonParser=new JSONParser();
			JSONObject jsonObj=(JSONObject)jsonParser.parse(result);
			
			
			JSONArray addrInfoArray = (JSONArray) jsonObj.get("documents");
			
			JSONObject addressObj=(JSONObject)addrInfoArray.get(0);
			
			//경도
			String longtitude=(String) addressObj.get("y");
			//위도
			String latitude=(String) addressObj.get("x");
			
			
			
			br.close();
			
			Tude tude=new Tude(longtitude,latitude);
			
			return tude;
			

        } else {  // 에러 발생
        	BufferedReader br = new BufferedReader(new InputStreamReader(hc.getErrorStream()));
        }
		
		} catch (Exception e){
			System.err.println(e.toString());
		}
		
		
		
		
		return null;

		
		
		
	}
	

}
