package m2x.att.client.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import m2x.att.client.network.ApiV2Response;

/**
 * Created by Joaquin on 28/11/14.
 */
public class APISharedPreferences {

    private static final String TAG = APISharedPreferences.class.getSimpleName();
    public final static String M2X_SHARED_PREFERENCES = "M2XSharedPreferences";

    public final static String SHARED_PREFERENCES_API_KEY = "M2XApiKey";
    public final static String SHARED_PREFERENCES_LAST_RESPONSE = "LastResponse";

    //Singleton
    public static SharedPreferences sharedPreferences = null;

    /**
     * Method to save the Key
     *
     * @param context
     */
    public static void setApiKey(Context context, String apiKey) {
        sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_API_KEY, apiKey);
        editor.commit();
    }

    /**
     * Method to get the Key from shared preferences
     * @param context
     * @return
     */
    public static String getApiKey(Context context) {
        String versionNumber = "0";
        try{
            sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
            if (sharedPreferences != null) {
                versionNumber = sharedPreferences.getString(SHARED_PREFERENCES_API_KEY,"");
            }
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }finally{
            return versionNumber;
        }
    }

    /**
     * Method to save the Key
     *
     * @param context
     */
    public static void saveLastResponse(Context context, ApiV2Response response) {
        sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(response);
        editor.putString(SHARED_PREFERENCES_LAST_RESPONSE, json);
        editor.commit();
    }

    /**
     * Method to get the Key from shared preferences
     * @param context
     * @return
     */
    public static ApiV2Response getLastResponse(Context context) {
        ApiV2Response lastResponse = null;
        Gson gson = new Gson();
        try{
            sharedPreferences = context.getSharedPreferences(M2X_SHARED_PREFERENCES, 0);
            if (sharedPreferences != null) {
                String lr = sharedPreferences.getString(SHARED_PREFERENCES_LAST_RESPONSE,"");
                lastResponse = gson.fromJson(lr, ApiV2Response.class);
            }
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }finally{
            return lastResponse;
        }
    }
}
