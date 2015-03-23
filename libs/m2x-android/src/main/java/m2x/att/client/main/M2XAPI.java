package m2x.att.client.main;

import android.content.Context;

import m2x.att.client.network.ApiV2Response;
import m2x.att.client.sharedPreferences.APISharedPreferences;

/**
 * Created by Joaquin on 1/12/14.
 */
public class M2XAPI {

    /**
     * Initialize library with Master API Key used for every request.
     * @param apiKey
     */
    public static void initialize(Context context,String apiKey){
        // Save api
        APISharedPreferences.setApiKey(context,apiKey);
    }

    /**
     * Returns last response from API
     * @param context
     */
    public static ApiV2Response getLastResponse(Context context){
        return APISharedPreferences.getLastResponse(context);
    }

}
