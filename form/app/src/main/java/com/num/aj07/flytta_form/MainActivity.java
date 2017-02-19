package com.num.aj07.flytta_form;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.num.aj07.flytta_form.app.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.filepicker.Filepicker;
import io.filepicker.models.FPFile;

public class MainActivity extends AppCompatActivity {
    private static final String FILEPICKER_API_KEY = "put_your_filestack_api_here";
    EditText name,address,area,price_rooms,contact_name,contact_num,amenities,bcard;
    String lat,lon,url1;
    String[] amen;
    int m=0,f=0;
    // OPTIONAL FIELD
    private static final String PARENT_APP = "FLYTTA_FORM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Filepicker.setKey(FILEPICKER_API_KEY);
        Filepicker.setAppName(PARENT_APP);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        lat = sharedPreferences.getString("Lat", "");
        lon = sharedPreferences.getString("Lon", "");
        name=(EditText)findViewById(R.id.editText);
        address=(EditText)findViewById(R.id.editText2);
        area=(EditText)findViewById(R.id.editText3);
        price_rooms=(EditText)findViewById(R.id.editText4);
        contact_name=(EditText)findViewById(R.id.editText5);
        contact_num=(EditText)findViewById(R.id.editText6);
        amenities=(EditText)findViewById(R.id.editText7);
        bcard=(EditText)findViewById(R.id.editText8);

        //Toast.makeText(getBaseContext(),"1:"+strSavedMem1+"2:"+strSavedMem2,Toast.LENGTH_LONG).show();
    }
    public void male(View view)
    {
        if(m==0)m=1;
        if(m==1)m=0;
    }
    public void female(View view)
    {
        if(f==0)f=1;
        if(f==1)f=0;
    }
    public void abcd(View view)
    {

        String s=amenities.getText().toString();
        //List<String> elephantList = Arrays.asList(s.split(","));
        amen=s.split(",");
        final JSONArray mJSONArray = new JSONArray(Arrays.asList(amen));
        //networkpost
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        final String url = "api_to_send_the_json object_url";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("lat", lat);
        params.put("lon",lon);
        params.put("namepg", name.getText().toString());
        params.put("address", address.getText().toString());
        params.put("area", area.getText().toString());
        params.put("price_rooms", price_rooms.getText().toString());
        params.put("Contactname", contact_name.getText().toString());
        params.put("Contactnum", contact_num.getText().toString());
        params.put("amenities",mJSONArray.toString());
        params.put("url",url1);
        params.put("bcard",bcard.getText().toString());
        params.put("Gender_male",Integer.toString(m));
        params.put("Gender_female",Integer.toString(f));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("post", response.toString());
                        pDialog.hide();
                        Toast.makeText(getBaseContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("post", "Error: " + error.getMessage());
                pDialog.hide();
                Toast.makeText(getBaseContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", lat);
                params.put("lon",lon);
                params.put("namepg", name.getText().toString());
                params.put("address", address.getText().toString());
                params.put("area", area.getText().toString());
                params.put("price_rooms", price_rooms.getText().toString());
                params.put("Contactname", contact_name.getText().toString());
                params.put("Contactnum", contact_num.getText().toString());
                params.put("amenities",mJSONArray.toString());
                params.put("url",url1);
                params.put("bcard",bcard.getText().toString());
                params.put("Gender_male",Integer.toString(m));
                params.put("Gender_female",Integer.toString(f));

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }
    public void getFromAll(View view) {
        Intent intent = new Intent(this, Filepicker.class);
        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }

    public void getFromFacebook(View view) {
        Intent intent = new Intent(this, Filepicker.class);

        String[] services = {"FACEBOOK"};
        intent.putExtra("services", services);

        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }

    public void getFromCamera(View view) {
        Intent intent = new Intent(this, Filepicker.class);

        String[] services = {"CAMERA"};
        intent.putExtra("services", services);

        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }

    public void getMultiple(View view) {
        Intent intent = new Intent(this, Filepicker.class);
        intent.putExtra("multiple", true);
        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }


    @Override
    protected void onDestroy() {
        Filepicker.unregistedLocalFileUploadCallbacks();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (requestCode == Filepicker.REQUEST_CODE_GETFILE) {
            if (resultCode == RESULT_OK) {
                // Filepicker always returns array of FPFile objects
                ArrayList<FPFile> fpFiles = data.getParcelableArrayListExtra(Filepicker.FPFILES_EXTRA);

                // Get first object (use if multiple option is not set)
                FPFile file = fpFiles.get(0);

                // Load image using Picasso library
                ImageView imageView = (ImageView) findViewById(R.id.imageResult);
                Picasso.with(getBaseContext()).load(file.getUrl()).into(imageView);
                url1=file.getUrl();
            } else if(resultCode == RESULT_CANCELED && data != null) {
                Uri fileUri = data.getData();

                Filepicker.uploadLocalFile(fileUri, this);
            }
        }
    }
}


