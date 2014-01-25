package com.rickylaishram.posttrack.utils;

import static com.rickylaishram.posttrack.utils.DBUtils.updateItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;

public final class CommonUtils {
	
	public final static String URL		= "http://services.ptcmysore.gov.in/Speednettracking/Track.aspx?articlenumber=";
	
	private static Context CTX;
	
	public final static void fetchItemDetails(Context ctx, String code) {
		
		CTX = ctx;
		
		(new FetchItemDetails()).execute(new String[]{code});
	}
	
	private static class FetchItemDetails extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String id				= params[0];
			
			Document doc 			= null;
			
			String booked_at		= null;
			String booked_on		= null;
			String delivered_at		= null;
			String delivered_on		= null;
			Integer confirmed		= 0;
			
			String eventvalidation	= null; 
			String viewstate		= null;
			
			try {
				//set timeouts
		        int timeoutConnection 		= 5000;
		        int timeoutSocket 			= 15000;
		        HttpParams httpParameters 	= new BasicHttpParams();
		        
		        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		        
				HttpClient httpclient 	= new DefaultHttpClient(httpParameters);
			    HttpGet httpget 		= new HttpGet(URL+id);
			    
			    HttpResponse response 	= httpclient.execute(httpget);
				HttpEntity httpEntity 	= response.getEntity();
			    
				String result 			= EntityUtils.toString(httpEntity);
				
				doc 					= Jsoup.parse(result);
				
				Elements details	= doc.select("table[class=mGrid]>tbody>tr>td[align=left]");
				
				try {
					booked_at			= details.get(0).text();
					booked_on			= details.get(1).text();
					delivered_at		= details.get(2).text();
					delivered_on		= details.get(3).text();
					confirmed			= 1;
					
					//details required for detailed tracking data
					eventvalidation		= (doc.select("input[name=__EVENTVALIDATION]").first())
											.attr("value");
					viewstate			= (doc.select("input[name=__VIEWSTATE]").first())
											.attr("value");
					updateItem(CTX, id, booked_at, booked_on, delivered_at, delivered_on, confirmed);
				} catch (Exception e) {
					e.printStackTrace();
					
					booked_at		= "";
					booked_on		= "";
					delivered_at	= "";
					delivered_on	= "";
					confirmed		= 0;
					eventvalidation	= "";
					viewstate		= "";
					
					updateItem(CTX, id, booked_at, booked_on, delivered_at, delivered_on, confirmed);
				}
				
				httpEntity.consumeContent();
				
				if(confirmed == 1) {
					doc	= null;
					
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();
					parameters.add(new BasicNameValuePair("__VIEWSTATE", viewstate));
					parameters.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation));
					parameters.add(new BasicNameValuePair("__EVENTTARGET", "GridView1"));
					parameters.add(new BasicNameValuePair("__EVENTARGUMENT", "Select$0"));
					
					HttpPost httppost		= new HttpPost(URL+id);
					httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
					HttpResponse response1 	= httpclient.execute(httppost);
					HttpEntity httpEntity1 	= response1.getEntity();
					
					String response_string 	= EntityUtils.toString(httpEntity1);
					doc 					= Jsoup.parse(response_string);
					
					Elements details1		= doc.select("div[id=pnlDetail]>div>table[class=mGrid]>tbody>tr>td");
					
					Integer size			= details1.size()/4;
					String date				= null;
					String time				= null;
					String place			= null;
					String status			= null;
					
					for(Integer i= 0; i < size; i++) {
						date		= details1.get(i*4).text();
						time		= details1.get(i*4 + 1).text();
						place		= details1.get(i*4 + 2).text();
						status		= details1.get(i*4 + 3).text();
					}
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		
	}
}
