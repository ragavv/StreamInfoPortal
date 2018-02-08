package com.hpm.sp.streaminfoportal.Articles;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.Models.Article;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

public class ArticleDetailsActivity extends AppCompatActivity {

    public static final String TAG = ArticleDetailsActivity.class.getSimpleName();
    private Article article;
    private Utils utils;

    @BindView(R.id.description)
    TextView mDescription;

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.subtitle)
    TextView mSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        utils = new Utils();
        article = getIntent().getParcelableExtra(Constants.ARTICLE);
        if (article != null) {
            getSupportActionBar().setTitle(article.getTitle());
            mDescription.setText(article.getDetails());
            Picasso.with(this)
                    .load(article.getImageUrl())
                    .transform(new ColorFilterTransformation(Color.parseColor("#99242424")))
                    .fit()
                    .into(mImageView);

            CharSequence ago = utils.getRelativeDateSeq(article.getPublishedDate());
            mSubtitle.setText(ago);
            mTitle.setText(article.getTitle());

        } else {
            Log.d(TAG, "onCreate() returned: Article is null ");
            finish();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
