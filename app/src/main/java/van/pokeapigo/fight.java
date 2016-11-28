package van.pokeapigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class fight extends AppCompatActivity {

    int random1 = 0, random2 = 0;
    private NetworkImageView iv1;
    private NetworkImageView iv2;
    ImageLoader imageLoader;
    int pv1 = 100, pv2 = 100;
    boolean turno = false;
    String URL_NAME = "https://pokeapi.co/api/v2/pokemon/";
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        iv1 = (NetworkImageView) findViewById(R.id.img1);
        iv2 = (NetworkImageView) findViewById(R.id.img2);

        TextView name1 = (TextView) findViewById(R.id.name1);
        TextView name2 = (TextView) findViewById(R.id.name2);

        final TextView pv_1 = (TextView) findViewById(R.id.pv1);
        final TextView pv_2 = (TextView) findViewById(R.id.pv2);

        random1 = getCompetitor1();
        random2 = getCompetitor2();
        while (random2 == random1){
            random2 = getCompetitor2();
        }
        loadImage(random1, random2);

        final Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pv1 <= 0 || pv2 <= 0){
                                timer.cancel();
                                changeActv();
                            }
                            if (!turno) {
                                pv1 = puntosV(pv1, pv_1);

                            }
                            if (turno) {
                                pv2 = puntosV(pv2, pv_2);
                            }
                            turno = !turno;
                        }
                    });
                }
            }, 4000, 1800);

        setNames(name1, random1);
        setNames(name2, random2);



    }

    public void changeActv(){
        Intent startNewActivity = new Intent(this, winOrLose.class);
        startActivity(startNewActivity);
    }


    private int getCompetitor1() {
        Random rand = new Random();
        random1 = rand.nextInt(721) + 1;           //Genera número aleatorio del 1 al 721
        return random1;
    }

    private int getCompetitor2() {
        Random rand = new Random();
        random2 = rand.nextInt(721) + 1;           //Genera número aleatorio del 1 al 721
        return random2;
    }

    private void loadImage(int random1, int random2){
        String urlbase = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        String url = urlbase + random1 + ".png";
        String url2 = urlbase + random2 + ".png";

        setImage(url, iv1);
        setImage(url2, iv2);
    }

    private void setImage(String url, NetworkImageView iv){
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(iv,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        iv.setImageUrl(url, imageLoader);
    }


    private int puntosV(int pv, TextView tv){
        Random rand = new Random();
        int random = rand.nextInt(100);
        pv = pv - random;
        if (pv >= 0){
            tv.setText("Puntos de Vida: " + pv);
        } else {
            if (pv < 0){
                tv.setText("Puntos de Vida: 0");
            }
        }


        return pv;
    }

    private void setNames(final TextView tv, int random){
        String url = URL_NAME + random + "/";

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arr = response.getJSONArray("forms");
                            JSONObject jo = arr.getJSONObject(0);
                            String name = jo.getString("name");

                            tv.setText(name);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        queue.add(obreq);
    }
}