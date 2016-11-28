package van.pokeapigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class fight extends AppCompatActivity {

    int random1 = 1, random2 = 1;
    private NetworkImageView iv1;
    private NetworkImageView iv2;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        iv1 = (NetworkImageView) findViewById(R.id.img1);
        iv2 = (NetworkImageView) findViewById(R.id.img2);

        getCompetitors();
        loadImage(random1, random2);
    }

    private void getCompetitors() {
        Random rand1 = new Random();
        random1 = rand1.nextInt(722 - 1) + 1;           //Genera número aleatorio del 1 al 721
        Random rand2 = new Random();
        random2 = rand2.nextInt(722 - 1) + 1;
        while (random1 == random2) {
            rand2 = new Random();
            random2 = rand2.nextInt(722 - 1) + 1;
        }
    }

    private void loadImage(int random1, int random2){
        String urlbase = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        String url = urlbase + random1 + ".png";
        String url2 = urlbase + random2 + ".png";

        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(iv1,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        iv1.setImageUrl(url, imageLoader);

        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(iv2,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        iv2.setImageUrl(url2, imageLoader);
    }
}