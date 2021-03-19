package network;

import android.util.Log;
import android.widget.ListView;

import com.example.atividade3.MainActivity;
import com.example.atividade3.model.Produto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CadastrarProduto extends Thread{

    MainActivity activity;

    Produto produto;
    boolean isEdit = false;
    boolean isDelete = false;
    public CadastrarProduto( Produto produto, boolean isEdit, boolean isDelete ){
        this.produto = produto;
        this.isEdit = isEdit;
        this.isDelete = isDelete;
    }

    public void run(){

        HttpURLConnection urlConnection = null;

        String stringURL;
        if(isEdit || isDelete){
            stringURL = "http://192.168.1.10:8080/" + produto.getId();
        }else{
            stringURL = "http://192.168.1.10:8080/";
        }
        Gson gson = new Gson();
        JSONObject json = null;
        try {
            json = new JSONObject(gson.toJson(produto));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            URL urlObj = new URL(stringURL);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(true);


            if(isDelete){
                conn.setRequestMethod("DELETE");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();


            }else{
                if(isEdit){
                    conn.setRequestMethod("PUT");
                }else {
                    conn.setRequestMethod("POST");
                }
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(json.toString());
                writer.close();


            }

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
