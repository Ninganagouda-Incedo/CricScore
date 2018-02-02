package com.cric.score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFromSMS {
	  // Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "AC786899547e930ab2a1be4e62d058bcbc";
	  public static final String AUTH_TOKEN = "d6549131759ffecac57d793362aaec83";

	  public static void main(String[] args) {
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    
	    
	    
	    try {
	        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=560029,In&appid=c1bb32ab5e3f3207378c548dc39eb581");
	        URLConnection urlConnection = url.openConnection();
	        HttpURLConnection connection = null;
	        if(urlConnection instanceof HttpURLConnection) {
	           connection = (HttpURLConnection) urlConnection;
	        }else {
	           System.out.println("Please enter an HTTP URL.");
	           return;
	        }
	        
	        BufferedReader in = new BufferedReader(
	           new InputStreamReader(connection.getInputStream()));
	        String urlString = "";
	        String current;
	        
	        while((current = in.readLine()) != null) {
	           urlString += current;
	        }
	        JSONObject obj = new JSONObject(urlString);
	        JSONArray arr = obj.getJSONArray("weather");
	        //System.out.println(arr.get(0));
	        JSONObject obj1 = arr.getJSONObject(0);
	       
	        float kelvin = Float.parseFloat(obj.getJSONObject("main").getString("temp"));
			// Kelvin to Degree Celsius Conversion
			float celsius = kelvin - 273.15F;
			System.out.println(obj1.get("main")+" "+obj1.get("description")+" For the City "+obj.getString("name")+" with temprature "+celsius);
	        
			
			Message message = Message.creator(new PhoneNumber("+919663097711"),
			        new PhoneNumber("+12407542924"), 
			        obj1.get("main")+" "+obj1.get("description")+" For the City "+obj.getString("name")+" with temprature "+celsius).create();

			    System.out.println(message.getSid());
			
	     } catch (IOException e) {
	        e.printStackTrace();
	     } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  }
}
