package com.cric.score;

import java.net.HttpURLConnection;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
public class CricScore
{
	public static void main(String[] args) {

		
		
		  try {	
			  
			
			  URL url1 = new URL("http://cricapi.com/api/matches?apikey=WHZybJYEPzgyc67Hqvd4Y4Anpkq1");
				HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
				conn1.setRequestMethod("GET");
				conn1.setRequestProperty("Accept", "application/json");

				if (conn1.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn1.getResponseCode());
				}

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
					(conn1.getInputStream())));

				StringBuffer output2 = new StringBuffer();
				String output23;
				System.out.println("Output from Server .... \n");
				while ((output23 = br1.readLine()) != null) {
					// System.out.println(output);
					output2.append(output23);
				}
				JSONObject objMatches = new JSONObject(output2.toString());
				System.out.println(objMatches);
				int count = 0;
				JSONArray arr = new JSONArray();
				arr = objMatches.getJSONArray("matches");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject ob = new JSONObject(arr.get(i).toString());
					if(ob.getString("team-1").equalsIgnoreCase("India") && count<1)
					{
						count = count+1;
						//System.out.println("Please enter todays Match Unique Id ::  ");
						//Scanner sc = new Scanner(System.in);
						String uniqueId = ob.getString("unique_id");
						
						URL url = new URL("http://cricapi.com/api/cricketScore?apikey=WHZybJYEPzgyc67Hqvd4Y4Anpkq1&unique_id="+uniqueId);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						conn.setRequestProperty("Accept", "application/json");

						if (conn.getResponseCode() != 200) {
							throw new RuntimeException("Failed : HTTP error code : "
									+ conn.getResponseCode());
						}

						BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));

						StringBuffer output1 = new StringBuffer();
						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							// System.out.println(output);
							output1.append(output);
						}
						
						JSONObject obj = new JSONObject(output1.toString());
						System.out.println(obj);
						String score = obj.getString("score");
						String description = obj.getString("description");
						final String username = "ningu91@gmail.com";
						final String password = "Enjoy@001";

						Properties props = new Properties();
						props.put("mail.smtp.auth", "true");
						props.put("mail.smtp.starttls.enable", "true");
						props.put("mail.smtp.host", "smtp.gmail.com");
						props.put("mail.smtp.port", "587");

						Session session = Session.getInstance(props,
						  new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username, password);
							}
						  });

						try {

							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("ningu91@gmail.com"));
							message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse("ningu91@gmail.com"));
							message.setSubject(score);
							message.setText(description);

							Transport.send(message);

							System.out.println("Done");

						} catch (MessagingException e) {
							throw new RuntimeException(e);
						}
					
						conn.disconnect();
					}
				}
			  
		

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	}
