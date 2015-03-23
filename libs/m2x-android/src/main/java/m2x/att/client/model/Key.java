package m2x.att.client.model;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import m2x.att.client.common.Constants;
import m2x.att.client.listeners.ResponseListener;
import m2x.att.client.network.JsonRequest;

/**
 * Created by Joaquin on 28/11/14.
 */
public class Key {

    public static final int REQUEST_CODE_KEYS_LIST = 3001;
    public static final int REQUEST_CODE_KEYS_CREATE = 3002;
    public static final int REQUEST_CODE_KEYS_DETAIL = 3003;
    public static final int REQUEST_CODE_KEYS_UPDATE = 3004;
    public static final int REQUEST_CODE_KEYS_REGENERATE = 3005;
    public static final int REQUEST_CODE_KEYS_DELETE = 3006;

    public static final void list(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_LIST),
                params,
                listener,
                REQUEST_CODE_KEYS_LIST
        );
    }

    public static final void create(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.KEYS_CREATE,
                params,
                listener,
                REQUEST_CODE_KEYS_CREATE
        );
    }

    public static final void viewDetails(Context context, String keyId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_DETAIL,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DETAIL
        );
    }

    public static final void update(Context context,JSONObject params,String keyId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.KEYS_UPDATE,keyId),
                params,
                listener,
                REQUEST_CODE_KEYS_UPDATE
        );
    }

    public static final void regenerate(Context context, String keyId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.KEYS_REGENERATE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_REGENERATE
        );
    }

    public static final void delete(Context context,String keyId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.KEYS_DELETE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DELETE
        );
    }


}
