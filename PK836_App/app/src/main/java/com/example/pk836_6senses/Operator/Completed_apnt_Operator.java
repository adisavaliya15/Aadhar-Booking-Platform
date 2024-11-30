package com.example.pk836_6senses.Operator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pk836_6senses.Model.Appt_Model;
import com.example.pk836_6senses.Model.User;
import com.example.pk836_6senses.No_Internet.InternetReceiver;
import com.example.pk836_6senses.R;
import com.example.pk836_6senses.RequestHandler;
import com.example.pk836_6senses.SharedPrefManager;
import com.example.pk836_6senses.URLs;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Completed_apnt_Operator extends MyBaseActivity_Operator {

    List<Appt_Model> list;
    RecyclerView recyclerView;
    RecyclerAdapter radapter;
    User user;
    String uid;

    SwipeRefreshLayout mSwipeRefreshLayoutcmpt;
    //For No internet
    BroadcastReceiver broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.updateResource(sharedPrefManager.getLang());

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_completed_apnt_operator, null, false);
        drawer.addView(v, 0);

        recyclerView = findViewById(R.id.rv_op_get_comp_rqst);

        mSwipeRefreshLayoutcmpt = findViewById(R.id.op_comp_swipeTorefresh);
        user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getUSERID();

        //new item to top
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        LayoutManager.setReverseLayout(true);
        LayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(LayoutManager);

        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        radapter = new RecyclerAdapter(getAllData(), this);
        recyclerView.setAdapter(radapter);


        mSwipeRefreshLayoutcmpt.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                radapter = new RecyclerAdapter(getAllData(), Completed_apnt_Operator.this);
                recyclerView.setAdapter(radapter);
            }
        });

        //For No Internet
        broadcastReceiver = new InternetReceiver();
        InternetStatus();
    }

    //For No Internet
    public void InternetStatus() {
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } catch (Exception e) {
            // already registered
        }
    }

    private List<Appt_Model> getAllData() {

        final List<Appt_Model> data = new ArrayList<>();

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");

                        JSONArray jsonArray = obj.getJSONArray("Appointments");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String S_APNT_ID = jsonObject1.getString("APNT_ID");
                            String S_USERID = jsonObject1.getString("USERID");
                            String S_OP_ID = jsonObject1.getString("OP_ID");
                            String S_F_NAME = jsonObject1.getString("F_NAME");
                            String S_L_NAME = jsonObject1.getString("L_NAME");
                            String S_U_PHONE_NO = jsonObject1.getString("U_PHONE_NO");
                            String S_U_ADDRESS = jsonObject1.getString("U_ADDRESS");
                            String S_U_CITY = jsonObject1.getString("U_CITY");
                            String S_U_STATE = jsonObject1.getString("U_STATE");
                            String S_U_PINCODE = jsonObject1.getString("U_PINCODE");
                            String S_U_LATITUDE = jsonObject1.getString("U_LATITUDE");
                            String S_U_LONGITUDE = jsonObject1.getString("U_LONGITUDE");
                            String S_APNT_DETAIL = jsonObject1.getString("APNT_DETAIL");
                            String S_APNT_STATUS = jsonObject1.getString("APNT_STATUS");
                            String APNT_PEND_DT = jsonObject1.getString("APNT_PEND_DT");
                            String APNT_ACPT_DT = jsonObject1.getString("APNT_ACPT_DT");
                            String APNT_CMPT_DT = jsonObject1.getString("APNT_CMPT_DT");

                            String S_APNT_PEND_DT = APNT_PEND_DT.split("\\.")[0];
                            String S_APNT_ACPT_DT = APNT_ACPT_DT.split("\\.")[0];
                            String S_APNT_CMPT_DT = APNT_CMPT_DT.split("\\.")[0];

                            Appt_Model current2 = new Appt_Model();
                            current2.APNT_ID = S_APNT_ID;
                            current2.USERID = S_USERID;
                            current2.OP_ID = S_OP_ID;
                            current2.F_NAME = S_F_NAME;
                            current2.L_NAME = S_L_NAME;
                            current2.U_PHONE_NO = S_U_PHONE_NO;
                            current2.U_ADDRESS = S_U_ADDRESS;
                            current2.U_CITY = S_U_CITY;
                            current2.U_STATE = S_U_STATE;
                            current2.U_PINCODE = S_U_PINCODE;
                            current2.U_LATITUDE = S_U_LATITUDE;
                            current2.U_LONGITUDE = S_U_LONGITUDE;
                            current2.APNT_DETAIL = S_APNT_DETAIL;
                            current2.APNT_STATUS = S_APNT_STATUS;
                            current2.APNT_PEND_DT = S_APNT_PEND_DT;
                            current2.APNT_ACPT_DT = S_APNT_ACPT_DT;
                            current2.APNT_CMPT_DT = S_APNT_CMPT_DT;
                            data.add(current2);
                        }

                        recyclerView.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayoutcmpt.setRefreshing(false);
                        radapter.notifyDataSetChanged();
                        

                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayoutcmpt.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("OP_ID", uid);
                Log.e("Sa", uid);
                return requestHandler.sendPostRequest(URLs.URL_GET_OP_APNT_CMPT_HISTORY, params);

                //returing the response


            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
        return data;
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {


        List<Appt_Model> list;
        Context context;

        public RecyclerAdapter(List<Appt_Model> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_request_apnt, parent, false);
            return new RecyclerAdapter.MyviewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.MyviewHolder holder, @SuppressLint("RecyclerView") final int position) {

            Appt_Model current2 = list.get(position);


            holder.name.setText(current2.getF_NAME() + " " + current2.getL_NAME());
            holder.address.setText("Address: " + current2.getU_ADDRESS());
            holder.pincode.setText("Pincode: " + current2.getU_PINCODE());
            holder.datentime.setText("Date: " + current2.getAPNT_PEND_DT());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView name, address, pincode, datentime;
            private CardView cardView;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.txt_rv_rqst_name);
                address = itemView.findViewById(R.id.txt_rv_rqst_address);
                pincode = itemView.findViewById(R.id.txt_rv_rqst_pincode);
                datentime = itemView.findViewById(R.id.txt_rv_rqst_date);
                cardView = itemView.findViewById(R.id.cardid);

            }
        }
    }
}