package chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;

public class HttpClient {
public static String userName= null;
public static String password = null;
private static String output = null;
private static String type = null;
private static String threadId = null;
private static ArrayList<ArrayList<String>> messages;
    /**
     * @return the output
     */
    public static String getOutput() {
        return output;
    }

    /**
     * @param aOutput the output to set
     */
    public static void setOutput(String aOutput) {
        output = aOutput;
    }

    /**
     * @return the messages
     */
    public static ArrayList<ArrayList<String>> getMessages() {
        return messages;
    }

    /**
     * @param aMessages the messages to set
     */
    public static void setMessages(ArrayList<ArrayList<String>> aMessages) {
        messages = aMessages;
    }

HttpClient(String uname, String psword, String type){
    this.password = psword;
    this.userName = uname;
    this.type = type;
}
HttpClient(String uname, String psword,String threadId, String type){
    this.password = psword;
    this.userName = uname;
    this.type = type;
    this.threadId = threadId;
    
}

	public static void main(String[] args) {
            sendRequest();
        }

public static void sendRequest(){

	  try {
              URL url;
              if(type.equalsIgnoreCase("login")){
               url = new URL("http://localhost:8080/login.html?name="+userName+"&password="+password+"");}
              else if(type.equalsIgnoreCase("register")){
              url = new URL("http://localhost:8080/register.html?name="+userName+"&password="+password+""); }
               else if(type.equalsIgnoreCase("create")){
                url = new URL("http://localhost:8080/createThread.html?title="+userName+"&author="+password+"");}
               else if(type.equalsIgnoreCase("add")){
                url = new URL("http://localhost:8080/register.html?name="+userName+"&hobby="+password+"");}
              else if(type.equalsIgnoreCase("mainChat")){
               url = new URL("http://localhost:8080/mainChat.html?name="+userName+"&hobby="+password+"");}
              else if (type.equalsIgnoreCase("getMessage")) {
               url = new URL("http://localhost:8080/getMessages.html?id="+userName+"");}
              else if (type.equalsIgnoreCase("addMessage")) {
               url = new URL("http://localhost:8080/addMessage.html?message="+userName+"&author="+password+"&threadId="+threadId+"");}
              else{
                 url = new URL("http://localhost:8080/register.html?name="+userName+"&hobby="+password+"");}
		url = new URL(url.toString().replaceAll(" ", "%20"));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		String output;
                String sendOutput = "";
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
                        sendOutput += output;
		}
            setOutput(sendOutput);
            //setresponse(sendOutput)
		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
}

}