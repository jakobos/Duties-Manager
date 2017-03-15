package com.dreamsfactory.dutiesmanager.webServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dreamsfactory.dutiesmanager.app.AppController;
import com.dreamsfactory.dutiesmanager.managers.LogManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba on 2017-03-07.
 */

public class VolleyService {

    private final static String TAG = "VolleyService";
    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public static interface Listener{
        public void onResponse(JSONObject response);
        public void onResponse(JSONArray response);
        public void onResponse(String response);
    }

    protected void makeGETJSONObjectRequest(String tag, String url){

        // Tag used to cancel the request
        //String tag_json_obj = "json_obj_req";

        //String url = "http://api.androidhive.info/volley/person_object.json";

        //ProgressDialog pDialog = new ProgressDialog(this);
        //pDialog.setMessage("Loading...");
        //pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
        //                pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
               // pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);
    }
    protected void makeGETJSONArrayRequest(String tag, String url){
        // Tag used to cancel the request
        //String tag_json_arry = "json_array_req";

        //String url = "http://api.androidhive.info/volley/person_array.json";

//        ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onResponse(response);
//                        Log.d(TAG, response.toString());
//                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag);
    }
    protected void makeGETStringRequest(String tag, String url){
        // Tag used to cancel the request
        //String  tag_string_req = "string_req";

        //String url = "http://api.androidhive.info/volley/string_response.html";

//        ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
 //               Log.d(TAG, response.toString());
//                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag);
    }

    protected void makePOSTJSONObjectRequest(String tag, String url, final Map<String,String> params){
        // Tag used to cancel the request
//        String tag_json_obj = "json_obj_req";

//        String url = "http://api.androidhive.info/volley/person_object.json";

//        ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        LogManager.logInfo(response.toString());
                        listener.onResponse(response);
//                        Log.d(TAG, response.toString());
//                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                LogManager.logError(error.getMessage());
//                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("title", "aaaa");
//                params.put("description", "bbbb");
//                params.put("deadline", "1111");
//                params.put("flat_id", "13");/*Settings.getInstance(getBaseContext()).get(Settings.FLAT_ID)*/

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    protected void makePOSTStringRequest(String tag, String url, final Map<String,String> params){
        // Tag used to cancel the request
        //String  tag_string_req = "string_req";

        //String url = "http://api.androidhive.info/volley/string_response.html";

//        ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
                //               Log.d(TAG, response.toString());
//                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag);
    }

}
