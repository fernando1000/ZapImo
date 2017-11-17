package br.com.zapimo.util;

import com.android.volley.DefaultRetryPolicy;

public class VolleyTimeout {

	//+- 2.30 min
	private static int IMEOUT_MS = 6000;
	
	
	public static DefaultRetryPolicy recuperarTimeout(){
		
		return (new DefaultRetryPolicy(
				IMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
	}
	
}
