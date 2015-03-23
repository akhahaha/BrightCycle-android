package m2x.att.client.network;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by Joaquin on 28/11/14.
 */

/*
    raw: The raw response body.
    json: The parsed response body.
    status: The status code of the response.
    headers: The headers included on the response.
    success?: Whether response status is a success (status code 2xx)
    client_error?: Whether response status is one of 4xx
    server_error?: Whether response status is one of 5xx
    error?: Whether client_error? or server_error? is true
 */

public class ApiV2Response implements Serializable {

    @SerializedName("raw")
    private String _raw;
    @SerializedName("json")
    private JSONObject _json;
    @SerializedName("status")
    private String _status;
    @SerializedName("headers")
    private String _headers;

    //Generated Fields
    private Boolean _success;
    private Boolean _clientError;
    private Boolean _serverError;
    private Boolean _error;


    public String get_raw() {
        return _raw;
    }

    public void set_raw(String _raw) {
        this._raw = _raw;
    }

    public JSONObject get_json() {
        return _json;
    }

    public void set_json(JSONObject _json) {
        this._json = _json;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_headers() {
        return _headers;
    }

    public void set_headers(String _headers) {
        this._headers = _headers;
    }

    public Boolean get_success() {
        return _success;
    }

    public void set_success(Boolean _success) {
        this._success = _success;
    }

    public Boolean get_clientError() {
        return _clientError;
    }

    public void set_clientError(Boolean _clientError) {
        this._clientError = _clientError;
    }

    public Boolean get_serverError() {
        return _serverError;
    }

    public void set_serverError(Boolean _serverError) {
        this._serverError = _serverError;
    }

    public Boolean get_error() {
        return _error;
    }

    public void set_error(Boolean _error) {
        this._error = _error;
    }
}
