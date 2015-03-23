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
public class Device {

    public static final int REQUEST_CODE_SEARCH_PUBLIC_CATALOG = 1001;
    public static final int REQUEST_CODE_SEARCH_DEVICES = 1002;
    public static final int REQUEST_CODE_LIST_DEVICE_GROUP = 1003;
    public static final int REQUEST_CODE_CREATE_DEVICE = 1004;
    public static final int REQUEST_CODE_DEVICE_DETAILS = 1005;
    public static final int REQUEST_CODE_DEVICE_LOCATION = 1006;
    public static final int REQUEST_CODE_DEVICE_UPDATE_LOCATION = 1007;
    public static final int REQUEST_CODE_DEVICE_LIST_DATA_STREAMS = 1008;
    public static final int REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS = 1009;
    public static final int REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE = 1010;
    public static final int REQUEST_CODE_DEVICE_VIEW_DATA_STREAM = 1011;
    public static final int REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES = 1012;
    public static final int REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING = 1013;
    public static final int REQUEST_CODE_DEVICE_DATA_STREAM_STATS = 1014;
    public static final int REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES = 1015;
    public static final int REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES = 1016;
    public static final int REQUEST_CODE_DEVICE_DELETE_DATA_STREAM = 1017;
    public static final int REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES = 1018;
    public static final int REQUEST_CODE_DEVICE_LIST_TRIGGERS = 1019;
    public static final int REQUEST_CODE_DEVICE_CREATE_TRIGGER = 1020;
    public static final int REQUEST_CODE_DEVICE_VIEW_TRIGGER = 1021;
    public static final int REQUEST_CODE_DEVICE_UPDATE_TRIGGER = 1022;
    public static final int REQUEST_CODE_DEVICE_TEST_TRIGGER = 1023;
    public static final int REQUEST_CODE_DEVICE_DELETE_TRIGGER = 1024;
    public static final int REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG = 1025;
    public static final int REQUEST_CODE_DEVICE_DELETE = 1026;

    public static final void searchPublicCatalog(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    public static final void searchDevices(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_DEVICES
        );
    }

    public static final void listDeviceGroups(Context context, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_LIST_GROUPS,
                null,
                listener,
                REQUEST_CODE_LIST_DEVICE_GROUP
        );
    }

    public static final void createDevice(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    public static final void updateDeviceDetails(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_UPDATE_DETAILS,deviceId),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    public static final void viewDeviceDetails(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_VIEW_DETAILS,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    public static final void readDeviceLocation(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_READ_LOCATION,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LOCATION
        );
    }

    public static final void updateDeviceLocation(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_LOCATION, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_LOCATION
        );
    }

    public static final void listDataStreams(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAMS, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAMS
        );
    }

    public static final void createUpdateDataStreams(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_CREATE_UPDATE_DATA_STREAMS, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    public static final void updateDataStreamValue(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_DATA_STREAM_VALUE, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE
        );
    }

    public static final void viewDataStream(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_VIEW_DATA_STREAM, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_DATA_STREAM
        );
    }

    public static final void listDataStreamValues(Context context,HashMap<String,String> params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_VALUES, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES
        );
    }

    public static final void dataStreamSampling(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_SAMPLING, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING
        );
    }

    public static final void dataStreamStats(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_STATS, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    public static final void postDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_POST_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES
        );
    }

    public static final void deleteDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES
        );
    }

    public static final void deleteDataStream(Context context,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE_DATA_STREAM,deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM
        );
    }

    public static final void postDeviceUpdates(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_POST_UPDATES,deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES
        );
    }

    public static final void listTriggers(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_TRIGGERS, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LIST_TRIGGERS
        );
    }

    public static final void createTrigger(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_CREATE_TRIGGER, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_CREATE_TRIGGER
        );
    }

    public static final void viewTrigger(Context context,String deviceId,String triggerId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_VIEW_TRIGGER, deviceId,triggerId),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_TRIGGER
        );
    }

    public static final void updateTrigger(Context context,JSONObject params,String deviceId,String triggerId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_TRIGGER, deviceId,triggerId),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_TRIGGER
        );
    }

    public static final void testTrigger(Context context,JSONObject params,String deviceId,String triggerId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_TEST_TRIGGER, deviceId,triggerId),
                params,
                listener,
                REQUEST_CODE_DEVICE_TEST_TRIGGER
        );
    }

    public static final void deleteTrigger(Context context,String deviceId,String triggerId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE_TRIGGER,deviceId,triggerId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_TRIGGER
        );
    }

    public static final void viewRequestLog(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_REQUEST_LOG, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG
        );
    }

    public static final void deleteDevice(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DELETE
        );
    }

}
