package com.example.hp.graphqltesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String s="";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ExampleItem> list=new ArrayList<>();
        apollo_function(list);

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new ExampleAdapter(list);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void apollo_function(final ArrayList<ExampleItem> list) {
        ApolloClient apolloClient=ApolloConnector.setupApollo();
        apolloClient.query(MyQuery.builder().build()).enqueue(new ApolloCall.Callback<MyQuery.Data>() {
            String str="";
            @Override
            public void onResponse(@NotNull Response<MyQuery.Data> response) {
                for (final MyQuery.User user : response.getData().User) {
                    str+="user_id: " + user.user_id + ", name: " + user.name+"\n";
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.add(new ExampleItem(R.drawable.user_img,user.name));
                        }
                    });
                }
                Log.d(getApplicationContext().toString(), "onResponse: \n" + str);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView=(TextView)findViewById(R.id.txt);
                        textView.setText(str);
                        s=str;
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}