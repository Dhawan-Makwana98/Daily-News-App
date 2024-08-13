package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,description,content,imageURL, url;
    private TextView idTVTitle, idTVSubDescription, idTVContent;
    ImageView idIVNews;
    Button btnReadNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");

        idTVTitle=findViewById(R.id.idTVTitle);
        idTVSubDescription=findViewById(R.id.idTVSubDescription);
        idTVContent=findViewById(R.id.idTVContent);
        btnReadNews=findViewById(R.id.btnReadNews);
        idIVNews=findViewById(R.id.idIVNews);

        idTVTitle.setText(title);
        idTVSubDescription.setText(description);
        idTVContent.setText(content);
        Picasso.get().load(imageURL).into(idIVNews);
        btnReadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}