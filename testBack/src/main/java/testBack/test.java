package testBack;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class test {
	
	/* public String get(String url, Map params, String encoding){
         HttpClient client = new DefaultHttpClient();
  
         try{
             List<NameValuePair> paramList = convertParam(params);
             HttpGet get = new HttpGet(url+"?"+URLEncodedUtils.format(paramList, encoding));
             System.out.println("GET : " + get.getURI());
              
             ResponseHandler<String> rh = new BasicResponseHandler();
              
             return client.execute(get, rh);
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             client.getConnectionManager().shutdown();
         }
          
         return "error";
     }
      
     public String get(String url, Map params){
         return get(url, params, "UTF-8");
     }
      
      
      
     private List<NameValuePair> convertParam(Map params){
         List<NameValuePair> paramList = new ArrayList<NameValuePair>();
         Iterator<String> keys = params.keySet().iterator();
         while(keys.hasNext()){
             String key = keys.next();
             paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
         }
          
         return paramList;
     }
*/

 
 public static void main(String[] args) {
/*	 test p = new test();
      
      Map params = new HashMap();
      String ip = null;
      String hostName = null;
      try {
		ip = InetAddress.getLocalHost().getHostAddress();
		hostName = InetAddress.getLocalHost().getHostName();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      params.put("ip", ip);
      params.put("hostName", hostName);
       
*/

	 CloseableHttpClient httpclient = HttpClients.createDefault();
     //GET 방식으로 parameter를 전달
	 
	 Map params = new HashMap();
     String ip = null;
     String hostName = null;
     CloseableHttpResponse response = null;
     
     
     try {
		ip = InetAddress.getLocalHost().getHostAddress();
		hostName = InetAddress.getLocalHost().getHostName();
		System.out.println(ip);
		System.out.println(hostName);
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
     params.put("ip", ip);
     params.put("hostName", hostName);
    
     HttpGet httpGet = new HttpGet("http://192.168.202.139:8181/observer/index?ip="+ip+"&hostName="+hostName);
     try {
    	 response = httpclient.execute(httpGet);
         System.out.println(response.getStatusLine());
         //API서버로부터 받은 JSON 문자열 데이터
         System.out.println(EntityUtils.toString(response.getEntity()));
         HttpEntity entity = response.getEntity();
         
         
         
         EntityUtils.consume(entity);
         
         
         
         
     } catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
         try {
			response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }  
 }
     
} 
	 
	 
    
 
 
 
	
	
	

