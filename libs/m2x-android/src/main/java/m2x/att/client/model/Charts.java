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
public class Charts {

    public static final int REQUEST_CODE_CHARTS_LIST = 4001;
    public static final int REQUEST_CODE_CHARTS_CREATE = 4002;
    public static final int REQUEST_CODE_CHARTS_VIEW_DETAILS = 4003;
    public static final int REQUEST_CODE_CHARTS_UPDATE = 4004;
    public static final int REQUEST_CODE_CHARTS_DELETE = 4005;

    public static final void list(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.CHARTS_LIST),
                params,
                listener,
                REQUEST_CODE_CHARTS_LIST
        );
    }

    public static final void create(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.CHARTS_CREATE,
                params,
                listener,
                REQUEST_CODE_CHARTS_CREATE
        );
    }

    public static final void viewDetails(Context context,String chartId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.CHARTS_VIEW_DETAILS,chartId),
                null,
                listener,
                REQUEST_CODE_CHARTS_VIEW_DETAILS
        );
    }

    public static final void update(Context context,JSONObject params,String chartId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.CHARTS_UPDATE,chartId),
                params,
                listener,
                REQUEST_CODE_CHARTS_UPDATE
        );
    }

    public static final void delete(Context context,String chartId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.CHARTS_DELETE,chartId),
                null,
                listener,
                REQUEST_CODE_CHARTS_DELETE
        );
    }

    public static final void render(Context context,String chartId,String format, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.CHARTS_RENDER,chartId,format),
                null,
                listener,
                REQUEST_CODE_CHARTS_UPDATE
        );
    }
}
