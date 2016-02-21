package jsonparse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;
//import org.json.*;
import org.apache.*;
import org.apache.http.client.fluent.Request;
import org.json.*;
public class MostPrevalentLocation {
	public static Map<String,Map<String,Integer>> diseaseLocationMap=new HashMap<String,Map<String,Integer>>();
	public static void main(String args[]) throws JSONException
	{ String q,query;
		int l=0;
		int pi=0;
		//Map<String,Map<String,Integer>> diseaseLocationMap=new HashMap<String,Map<String,Integer>>();
		Object response=""; 
			String string="";
	
	try {
		void search(q)
		{
		query = q;	
		}
		response= Request.Get("http://hackillinois2016-api.e-imo.com/api/logs/search?query="  +query)
				.connectTimeout(1000)
				.socketTimeout(1000)
				.execute().returnContent();
		
		System.out.println(response.toString());
		
	//	JSONObject 
		JSONArray responseArray1 = new JSONArray(response.toString());
	//	System.out.println("1: "+responseArray1.getJSONObject(0).getString("Zip"));
		System.out.println("1: "+responseArray1.getJSONObject(0).getString("SearchResult"));
		
		
		
		for(int i=0;i<responseArray1.length();i++)
		{
			//Map<String,Integer> disease=diseaseLocationMap.get(responseArray1.getJSONObject(i).getString("Zip"));
			Map<String,Integer> disease=diseaseLocationMap.get(responseArray1.getJSONObject(i).getString("SearchResult"));
			if(disease==null)
			{
				
				 disease=new HashMap<String,Integer>();
			//	System.out.println("location: "+responseArray1.getJSONObject(i).getString("SearchResult"));
					System.out.println("location: "+responseArray1.getJSONObject(i).getString("Zip"));
				//disease.put(responseArray1.getJSONObject(i).getString("SearchResult"), 1);
					disease.put(responseArray1.getJSONObject(i).getString("Zip"), 1);
				//diseaseLocationMap.put(responseArray1.getJSONObject(i).getString("Zip"), disease);
					diseaseLocationMap.put(responseArray1.getJSONObject(i).getString("SearchResult"), disease);
			
			}
			else
			{
				
			//	Object count=disease.get(responseArray1.getJSONObject(i).getString("SearchResult"));
				Object count=disease.get(responseArray1.getJSONObject(i).getString("Zip"));
				if(count==null)
				{
					//disease.put(responseArray1.getJSONObject(i).getString("SearchResult"), 1);
					disease.put(responseArray1.getJSONObject(i).getString("Zip"), 1);
				}
					
				else
			//	disease.put(responseArray1.getJSONObject(i).getString("SearchResult"), (int)count+1);
					disease.put(responseArray1.getJSONObject(i).getString("Zip"), (int)count+1);
				//diseaseLocationMap.put(responseArray1.getJSONObject(i).getString("Zip"), disease);
				diseaseLocationMap.put(responseArray1.getJSONObject(i).getString("SearchResult"), disease);
				
			}
			
			
			
		}
		
		System.out.println("prevalent: "+getPrevalentLocation("Atrial fibrillation"));
		
//		Iterator it=diseaseLocationMap.entrySet().iterator();
//		int max=Integer.MIN_VALUE;
//		while(it.hasNext())
//		{
//			Entry obj=(Entry)it.next();
//			
//			
//		}
//		
//		
//		
//	//System.out.println("obj1: "+responseObject1.getJSONArray("1"));
//		//JSONObject obj2 = (JSONObject) responseObject1.get("1");
//		//JSONObject obj = (JSONObject) responseObject1.get("1");
//		//String secondArray=(String) responseObject.get("2");
//		//System.out.println("first 1: "+firstArray);
//	//	System.out.println("second 2: "+response);
////	JSONArray responseArrayList1=responseObject1.getJSONArray("1");
////	int length=responseArrayList1.length();
////	System.out.println("length"+length);
////	JSONArray arr1=responseArrayList1.getJSONArray(0);
////	System.out.println(arr1);
////	System.out.println(arr1.get(0));
//		JSONArray arraySet1[]=new JSONArray[5];
//	int k=1;
//	Point p[]=new Point[10];
//	
//	for(int i=0;i<2;i++)
//	{
//		arraySet1[i]=responseObject1.getJSONArray(i+1+"");
//		//System.out.println(i+" : "+arraySet1[i]);
//		 l=arraySet1[i].length();
//		//System.out.println("length: "+l);
//		int a,b;
//		
//		for(int j=0;j<l;j++)
//		{
//			p[pi]=new Point();
//			 a=(int) arraySet1[i].getJSONArray(j).get(0);
//			 b=(int) arraySet1[i].getJSONArray(j).get(1);
//			// System.out.println("a: "+a+"b: "+b);
//			 
//			 p[pi].x=a;
//			 p[pi].y=b;
//			 pi++;
//			 
//		}
//		//System.out.println("a: "+a);
//	}
//	//System.out.println("l: "+l);
//	for(int i=0;i<pi;i++)
//	{
//		System.out.println("i: "+i);
//		System.out.println("Point p x: "+p[i].x);
//		System.out.println("Point p y: "+p[i].y);
//		
//	}
//	
//	
//
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	}
	static String getPrevalentLocation(String disease)
	{
		Map<String,Integer> location=diseaseLocationMap.get(disease);
		Iterator it=location.entrySet().iterator();
		int max=Integer.MIN_VALUE;
		int count=0;
		String prevLocation="";
		while(it.hasNext())
		{
			Entry obj=(Entry)it.next();
			count=(int) obj.getValue();
			if(count>max)
			{
				max=count;
				prevLocation=(String) obj.getKey();
			}
			
			
			
		}
		
		return prevLocation;
	}
}
