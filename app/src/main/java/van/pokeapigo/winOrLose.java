package van.pokeapigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class winOrLose extends AppCompatActivity {

    String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_or_lose);

        NetworkImageView iv1 = (NetworkImageView) findViewById(R.id.img);
        TextView result = (TextView) findViewById(R.id.wOL);

        Bundle b = getIntent().getExtras();
        int random1 = 0, winner = 0;
        if(b != null) {
            random1 = b.getInt("random1");
            winner = b.getInt("winner");
        }
        if(winner == 0){
            result.setText("Perdiste");
        } else{
            result.setText("¡GANASTE!");
        }

        url = url + random1 + ".png";

        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(iv1,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        iv1.setImageUrl(url, imageLoader);
    }

    public void newGame(View v){
        Intent startNewActivity = new Intent(this, fight.class);
        startActivity(startNewActivity);
    }
}
