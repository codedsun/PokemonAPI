package com.example.suneet.lecture11hw;

import android.app.Activity;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    LinearLayout linearLayout;
    ImageView pokeball;
    EditText number;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    TextView name;
    TextView id;
    TextView base;
    TextView order;
    TextView abilites;
    TextView weight;
    TextView locationarea;
    TextView height;
    TextView types;
    ImageButton backButton;
    ImageButton forwardButon;
    ImageButton clickMe;
    Integer back=0;
    Integer front=0;
    LinearLayout linearLayout2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
        linearLayout= (LinearLayout) findViewById(R.id.linear_Layout1);
        linearLayout2= (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout2.setVisibility(View.INVISIBLE);
        pokeball= (ImageView) findViewById(R.id.hit);
        number= (EditText) findViewById(R.id.edit_text);
        imageView1= (ImageView) findViewById(R.id.image1);
        imageView2= (ImageView) findViewById(R.id.image2);
        imageView3= (ImageView) findViewById(R.id.image3);
        imageView4= (ImageView) findViewById(R.id.image4);
        name= (TextView) findViewById(R.id.name);
        id= (TextView) findViewById(R.id.id);
        base= (TextView) findViewById(R.id.base_experience);
        order= (TextView) findViewById(R.id.order);
        abilites= (TextView) findViewById(R.id.abilities);
        weight= (TextView) findViewById(R.id.weight);
        locationarea= (TextView) findViewById(R.id.location_area);
        height= (TextView) findViewById(R.id.height);
        types= (TextView) findViewById(R.id.types);
        backButton= (ImageButton) findViewById(R.id.backButton);
        forwardButon= (ImageButton) findViewById(R.id.forwardButton);
        clickMe= (ImageButton) findViewById(R.id.clickMe);


        pokeball.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String s=number.getText().toString();
                Integer s1=Integer.parseInt(s);

                if(s1>0 && s1<723)
                {
                    back=s1-1;
                    front=s1+1;




                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("http://pokeapi.co/api/v2/pokemon/"+s).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String mess=e.getLocalizedMessage();
                            Toast.makeText(MainActivity.this,"Kindly Check the URL"+mess,Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            
                            Gson gson=new Gson();
                            final Pokemon pokemon=gson.fromJson(response.body().string(),Pokemon.class);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    linearLayout.setVisibility(View.GONE);
                                    linearLayout2.setVisibility(View.VISIBLE);
                                    Picasso.with(MainActivity.this).load(pokemon.sprites.getBack_default()).into(imageView1);
                                    Picasso.with(MainActivity.this).load(pokemon.sprites.getBack_shiny()).into(imageView2);
                                    Picasso.with(MainActivity.this).load(pokemon.sprites.getFront_default()).into(imageView3);
                                    Picasso.with(MainActivity.this).load(pokemon.sprites.getFront_shiny()).into(imageView4);
                                    name.setText(pokemon.getName());

                                    id.setText(pokemon.getId());
                                    base.setText(pokemon.getBase_experience());
                                    order.setText(pokemon.getOrder());
                                    abilites.setText(pokemon.getAbilities().get(0).getAbility().getName());
                                    weight.setText(pokemon.getWeight());
                                    locationarea.setText(pokemon.getLocation_area_encounters());
                                    height.setText(pokemon.getHeight());
                                    types.setText(pokemon.getTypes().get(0).getType().getName());
                                }
                            });





                        }
                    });






                }
                else
                {
                    Toast.makeText(MainActivity.this,"Pokemon not in Range ! Try Once More",Toast.LENGTH_SHORT).show();

                }



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((back>0)&&(back<720))
                {
                    number.setText(back.toString());
                    pokeball.callOnClick();
                }
                else
                    Toast.makeText(MainActivity.this,"Pokemon Doesn't Exist",Toast.LENGTH_SHORT).show();
            }
        });
        forwardButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(front<721)
                {
                    number.setText(front.toString());
                    pokeball.callOnClick();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Pokemon Doesn't Exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility()==View.GONE) {
                    Toast.makeText(MainActivity.this, "You Loved Me !! Hoohh", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"First Search Me!",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
