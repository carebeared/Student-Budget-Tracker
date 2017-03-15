package com.magic.akash.magicbox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Feedback extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RatingBar ratingBar= (RatingBar) findViewById(R.id.ratingBar);
        assert ratingBar != null;

        // Set ChangeListener to Rating Bar
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromTouch) {
                Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_LONG).show();
                 float z=rating;

                final int p=ratingBar.getNumStars();
                final float q=z;
                Button btn= (Button) findViewById(R.id.button);
                assert btn != null;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "newmagicbox2017@gmail.com",""));
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"You have Given magicBox : "+q+"/"+p+ " Stars ");
                        startActivity(intent);
                    }
                });

            }
        });




    }
}