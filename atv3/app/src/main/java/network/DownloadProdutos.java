package network;

import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.atividade3.MainActivity;

import com.example.atividade3.model.Produto;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadProdutos extends Thread{

    MainActivity activity;

    ListView view;

    public DownloadProdutos( MainActivity activity, ListView view ){
        this.activity = activity;
        this.view = view;
    }

    public void run(){

        HttpURLConnection urlConnection = null;

        String stringURL = "http://192.168.1.10:8080/";
        StringBuilder resposta = new StringBuilder();

        try {

            URL url = new URL(stringURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);


            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            Log.d( "AndroidJSON", resposta.toString());
            String res = resposta.toString();
            JSONArray jsonArr = new JSONArray(res);
            Gson gson = new Gson();
            ArrayList<Produto> cardapio =  new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++)
            {
                cardapio.add(gson.fromJson( jsonArr.getJSONObject(i).toString(), Produto.class ));


            }

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    activity.updateCardapio( cardapio );
                }
            };

            view.post( runnable );

        } catch( MalformedURLException ex ){
            ex.printStackTrace();
        } catch( IOException ex ){
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
