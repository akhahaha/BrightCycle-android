package m2x.att.client.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import m2x.att.client.listeners.ResponseListener;
import m2x.att.client.sharedPreferences.APISharedPreferences;
import m2x.att.client.utils.ArrayUtils;

/**
 * Created by Joaquin on 1/12/14.
 */
public class JsonRequest {

	public static void makePostRequest(final Context context,
	                                   String url,
	                                   JSONObject params,
	                                   final ResponseListener listener,
	                                   final int requestCode
	) {

		final ApiV2Response apiResponse = new ApiV2Response();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Request.Method.POST,
				url,
				params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject o) {
						apiResponse.set_json(o);
						apiResponse.set_clientError(Boolean.FALSE);
						apiResponse.set_error(Boolean.FALSE);
						apiResponse.set_serverError(Boolean.FALSE);
						apiResponse.set_success(Boolean.TRUE);
						//Save response
						APISharedPreferences.saveLastResponse(context,apiResponse);
						listener.onRequestCompleted(apiResponse,requestCode);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				apiResponse.set_json(null);
				if(error.networkResponse!=null)
					apiResponse.set_status(String.valueOf(error.networkResponse.statusCode));

				if(error.networkResponse!=null && error.networkResponse.data!=null){
					try {
						apiResponse.set_raw(new String(error.networkResponse.data,"utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				if(error.networkResponse!=null && error.networkResponse.statusCode<500){
					apiResponse.set_clientError(Boolean.TRUE);
					apiResponse.set_serverError(Boolean.FALSE);
				}else{
					apiResponse.set_clientError(Boolean.FALSE);
					apiResponse.set_serverError(Boolean.TRUE);
				}
				apiResponse.set_error(Boolean.TRUE);
				apiResponse.set_success(Boolean.FALSE);

				//Save response
				APISharedPreferences.saveLastResponse(context,apiResponse);
				listener.onRequestError(apiResponse,requestCode);
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
				params.put("User-agent", "M2X-Android/2.0.0 java/21 (".
						concat(System.getProperty("os.arch")).
						concat(" ").
						concat(android.os.Build.VERSION.RELEASE).
						concat(")"));
				return params;
			}

			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
					apiResponse.set_raw(new String(response.data,"utf-8"));
					apiResponse.set_status(String.valueOf(response.statusCode));
					apiResponse.set_headers(response.headers.toString());
					if(apiResponse.get_raw()==null || apiResponse.get_raw().equals("")
							&& response.statusCode<400){
						return Response.success(
								null,
								HttpHeaderParser.parseCacheHeaders(response));
					}else{
						return Response.success(
								new JSONObject(apiResponse.get_raw()),
								HttpHeaderParser.parseCacheHeaders(response));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					return Response.error(new ParseError(e));
				}
			}
		};
		//It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
		VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
	}

	public static void makeGetRequest(final Context context,
	                                  String url,
	                                  HashMap<String,String> params,
	                                  final ResponseListener listener,
	                                  final int requestCode) {

		final ApiV2Response apiResponse = new ApiV2Response();

		if(params!=null)
			url = url.concat("?".concat(ArrayUtils.mapToQueryString(params)));

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Request.Method.GET,
				url,
				null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject o) {
						apiResponse.set_json(o);
						apiResponse.set_clientError(Boolean.FALSE);
						apiResponse.set_error(Boolean.FALSE);
						apiResponse.set_serverError(Boolean.FALSE);
						apiResponse.set_success(Boolean.TRUE);
						//Save response
						APISharedPreferences.saveLastResponse(context,apiResponse);
						listener.onRequestCompleted(apiResponse,requestCode);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				apiResponse.set_json(null);
				if(error.networkResponse!=null)
					apiResponse.set_status(String.valueOf(error.networkResponse.statusCode));

				if(error.networkResponse!=null && error.networkResponse.data!=null){
					try {
						apiResponse.set_raw(new String(error.networkResponse.data,"utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				if(error.networkResponse!=null && error.networkResponse.statusCode<500){
					apiResponse.set_clientError(Boolean.TRUE);
					apiResponse.set_serverError(Boolean.FALSE);
				}else{
					apiResponse.set_clientError(Boolean.FALSE);
					apiResponse.set_serverError(Boolean.TRUE);
				}
				apiResponse.set_error(Boolean.TRUE);
				apiResponse.set_success(Boolean.FALSE);

				//Save response
				APISharedPreferences.saveLastResponse(context,apiResponse);
				listener.onRequestError(apiResponse,requestCode);
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
				params.put("User-agent", "M2X-Android/2.0.0 java/21 (".
						concat(System.getProperty("os.arch")).
						concat(" ").
						concat(android.os.Build.VERSION.RELEASE).
						concat(")"));
				return params;
			}

			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
					apiResponse.set_raw(new String(response.data,"utf-8"));
					apiResponse.set_status(String.valueOf(response.statusCode));
					apiResponse.set_headers(response.headers.toString());
					if(apiResponse.get_raw()==null || apiResponse.get_raw().equals("")
							&& response.statusCode<400){
						return Response.success(
								null,
								HttpHeaderParser.parseCacheHeaders(response));
					}else{
						return Response.success(
								new JSONObject(apiResponse.get_raw()),
								HttpHeaderParser.parseCacheHeaders(response));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					return Response.error(new ParseError(e));
				}
			}
		};
		//It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
		VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
	}

	public static void makePutRequest(final Context context,
	                                  String url,
	                                  JSONObject params,
	                                  final ResponseListener listener,
	                                  final int requestCode) {

		final ApiV2Response apiResponse = new ApiV2Response();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Request.Method.PUT,
				url,
				params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject o) {
						apiResponse.set_json(o);
						apiResponse.set_clientError(Boolean.FALSE);
						apiResponse.set_error(Boolean.FALSE);
						apiResponse.set_serverError(Boolean.FALSE);
						apiResponse.set_success(Boolean.TRUE);
						//Save response
						APISharedPreferences.saveLastResponse(context,apiResponse);
						listener.onRequestCompleted(apiResponse,requestCode);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				apiResponse.set_json(null);
				if(error.networkResponse!=null)
					apiResponse.set_status(String.valueOf(error.networkResponse.statusCode));

				if(error.networkResponse!=null && error.networkResponse.data!=null){
					try {
						apiResponse.set_raw(new String(error.networkResponse.data,"utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				if(error.networkResponse!=null && error.networkResponse.statusCode<500){
					apiResponse.set_clientError(Boolean.TRUE);
					apiResponse.set_serverError(Boolean.FALSE);
				}else{
					apiResponse.set_clientError(Boolean.FALSE);
					apiResponse.set_serverError(Boolean.TRUE);
				}
				apiResponse.set_error(Boolean.TRUE);
				apiResponse.set_success(Boolean.FALSE);

				//Save response
				APISharedPreferences.saveLastResponse(context,apiResponse);
				listener.onRequestError(apiResponse,requestCode);
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
				params.put("User-agent", "M2X-Android/2.0.0 java/21 (".
						concat(System.getProperty("os.arch")).
						concat(" ").
						concat(android.os.Build.VERSION.RELEASE).
						concat(")"));
				return params;
			}

			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
					apiResponse.set_raw(new String(response.data,"utf-8"));
					apiResponse.set_status(String.valueOf(response.statusCode));
					apiResponse.set_headers(response.headers.toString());
					if(apiResponse.get_raw()==null || apiResponse.get_raw().equals("")
							&& response.statusCode<400){
						return Response.success(
								null,
								HttpHeaderParser.parseCacheHeaders(response));
					}else{
						return Response.success(
								new JSONObject(apiResponse.get_raw()),
								HttpHeaderParser.parseCacheHeaders(response));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					return Response.error(new ParseError(e));
				}
			}
		};
		//It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
		VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
	}

	public static void makeDeleteRequest(final Context context,
	                                     String url,
	                                     JSONObject params,
	                                     final ResponseListener listener,
	                                     final int requestCode) {

		final ApiV2Response apiResponse = new ApiV2Response();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Request.Method.DELETE,
				url,
				params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject o) {
						apiResponse.set_json(o);
						apiResponse.set_clientError(Boolean.FALSE);
						apiResponse.set_error(Boolean.FALSE);
						apiResponse.set_serverError(Boolean.FALSE);
						apiResponse.set_success(Boolean.TRUE);
						//Save response
						APISharedPreferences.saveLastResponse(context,apiResponse);
						listener.onRequestCompleted(apiResponse,requestCode);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				apiResponse.set_json(null);
				if(error.networkResponse!=null)
					apiResponse.set_status(String.valueOf(error.networkResponse.statusCode));

				if(error.networkResponse!=null && error.networkResponse.data!=null){
					try {
						apiResponse.set_raw(new String(error.networkResponse.data,"utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				if(error.networkResponse!=null && error.networkResponse.statusCode<500){
					apiResponse.set_clientError(Boolean.TRUE);
					apiResponse.set_serverError(Boolean.FALSE);
				}else{
					apiResponse.set_clientError(Boolean.FALSE);
					apiResponse.set_serverError(Boolean.TRUE);
				}
				apiResponse.set_error(Boolean.TRUE);
				apiResponse.set_success(Boolean.FALSE);

				//Save response
				APISharedPreferences.saveLastResponse(context,apiResponse);
				listener.onRequestError(apiResponse,requestCode);
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
				params.put("User-agent", "M2X-Android/2.0.0 java/21 (".
						concat(System.getProperty("os.arch")).
						concat(" ").
						concat(android.os.Build.VERSION.RELEASE).
						concat(")"));
				return params;
			}

			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
					apiResponse.set_raw(new String(response.data,"utf-8"));
					apiResponse.set_status(String.valueOf(response.statusCode));
					apiResponse.set_headers(response.headers.toString());
					if(apiResponse.get_raw()==null || apiResponse.get_raw().equals("")
							&& response.statusCode<400){
						return Response.success(
								null,
								HttpHeaderParser.parseCacheHeaders(response));
					}else{
						return Response.success(
								new JSONObject(apiResponse.get_raw()),
								HttpHeaderParser.parseCacheHeaders(response));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					return Response.error(new ParseError(e));
				}
			}
		};
		//It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
		VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
	}


}