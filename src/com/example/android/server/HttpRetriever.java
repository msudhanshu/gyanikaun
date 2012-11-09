package com.example.android.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

///import com.toi.reader.io.FlushedInputStream;

public class HttpRetriever {
//private DefaultHttpClient client = new DefaultHttpClient();	
	
    public void fireVideoFeedRetrieval(String url){
    	DefaultHttpClient client = new DefaultHttpClient();
        if(!url.startsWith("http://"))
	    	return ;
        HttpGet getRequest = new HttpGet(url);
        
		try {
			
			HttpResponse getResponse = client.execute(getRequest);
		} 
		catch (IOException e) {
			getRequest.abort();
	        Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

    }
    
    public static String generateString(InputStream stream) {

    	        InputStreamReader reader = new InputStreamReader(stream);
    	        	
    	        BufferedReader buffer = new BufferedReader(reader);

    	        StringBuilder sb = new StringBuilder();

    	   

    	        try {

    	            String cur;

    	            while ((cur = buffer.readLine()) != null) {

    	                sb.append(cur + "\n");

    	           }

    	       } catch (IOException e) {

    	           // TODO Auto-generated catch block

    	           e.printStackTrace();

    	       }

    	  
    	       try {

    	           stream.close();

    	      } catch (IOException e) {

    	          // TODO Auto-generated catch block

    	          e.printStackTrace();

    	       }

    	       return sb.toString();

    	   }
  //  HttpGet request = new HttpGet(...);
  //  request.setHeader("Authorization", "Basic "+Base64.encodeBytes("login:password".getBytes()));
    //request.setHeader("Authorization", "Basic " + Base64.encodeToString("user:password".getBytes(), Base64.NO_WRAP));

    public String retrieveFeedsWithAuth(String url,String username, String password) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams httpParameters = new BasicHttpParams();
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 25000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		client.setParams(httpParameters);
		HttpGet getRequest = null;
		try{
			getRequest = new HttpGet(url);
			String auth= username+":"+password;
			getRequest.setHeader("Authorization", "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP));
			//getRequest.setHeader("Authorization", "Basic "+Base64.encodeBytes("login:password".getBytes()));
		}
		catch (Exception e) {
			return null;
		}
        
		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) { 
				if(getRequest != null)
					getRequest.abort();
	            Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
	            return null;
	        }
			
			HttpEntity getResponseEntity = getResponse.getEntity();
			
			if (getResponseEntity != null) {
				InputStream data = getResponseEntity.getContent();

				   return generateString(data);
			}
			
		} 
		catch (IOException e) {
			if(getRequest != null)
				getRequest.abort();
	        Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		
		return null;
		
	}

	public static String retrieveFeeds(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams httpParameters = new BasicHttpParams();
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 25000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		client.setParams(httpParameters);
		HttpGet getRequest = null;
		try{
			getRequest = new HttpGet(url);
		}
		catch (Exception e) {
			return null;
		}
        
		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) { 
				if(getRequest != null)
					getRequest.abort();
	            Log.w("HttpRetriever", "Error " + statusCode + " for URL " + url); 
	            return null;
	        }
			
			HttpEntity getResponseEntity = getResponse.getEntity();
			
			if (getResponseEntity != null) {
				InputStream data = getResponseEntity.getContent();

				   return generateString(data);
			}
			
		} 
		catch (IOException e) {
			if(getRequest != null)
				getRequest.abort();
	        Log.w("HttpRetriever", "Error for URL " + url, e);
		}
		
		return null;
		
	}
	
	public InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
	    if(!url.startsWith("http://"))
	    	return null;
		HttpGet getRequest = new HttpGet(url);
        
		try {
			
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) { 
	            Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
	            return null;
	        }

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
			
		} 
		catch (IOException e) {
			if(getRequest != null)
				getRequest.abort();
	        Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		
		return null;
		
	}
	
	/*public Bitmap retrieveBitmap(String url) throws Exception {
		
		InputStream inputStream = null;
        try {
            inputStream = this.retrieveStream(url);
            final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
            return bitmap;
        } 
        finally {
            inputStream.close();
        }
		
	}*/
	
/*	public Bitmap retrieveBitmap(String url) {
		final DefaultHttpClient client = new DefaultHttpClient();
	    if(!url.startsWith("http://"))
	    	return null;
	    final HttpGet getRequest = new HttpGet(url);
	    try {
	        HttpResponse response = client.execute(getRequest);
	        final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) { 
	            Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
	            return null;
	        }
	        
	        final HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream inputStream = null;
	            try {
	                inputStream = entity.getContent(); 
	                final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
	                return bitmap;
	            } catch(Exception e){
	            	System.gc();
	            	e.printStackTrace();
	            }
	            finally {
	                if (inputStream != null) {
	                    inputStream.close();  
	                }
	                entity.consumeContent();
	                
	            }
	        }
	    } catch (Exception e) {
	        // Could provide a more explicit error message for IOException or IllegalStateException
	        getRequest.abort();
	        //Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e.toString());
	    } 
	    if(getRequest != null)
			getRequest.abort();
	    return null;
	}
*/
}
