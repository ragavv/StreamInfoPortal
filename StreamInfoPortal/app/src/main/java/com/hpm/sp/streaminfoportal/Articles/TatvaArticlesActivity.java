package com.hpm.sp.streaminfoportal.Articles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.Article;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;
import com.hpm.sp.streaminfoportal.WebView.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TatvaArticlesActivity extends AppCompatActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TatvaArticlesActivity.class.getSimpleName();
    private TatvaRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected long downloadReference = 0;
    private Utils utils;
    private List<Article> articles;

    @BindView(R.id.articles_recycler_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatva_articles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        utils = new Utils();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        articles = new ArrayList<>();
        mRefreshLayout.setOnRefreshListener(this);

        refreshList();

    }

    protected void refreshList() {
        if (utils.isConnectedToNetwork(this)) {
            mRefreshLayout.setRefreshing(true);
            NetworkHelper.getAllArticles(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    mRefreshLayout.setRefreshing(false);
                    if (e == null) {
                        articles = (List<Article>) objects;
                        mAdapter = new TatvaRecyclerViewAdapter(TatvaArticlesActivity.this, articles);
                        mAdapter.setOnItemClickListener(TatvaArticlesActivity.this);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(TatvaArticlesActivity.this, DividerItemDecoration.VERTICAL));
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.e(TAG, "onResponseFromServer: Error : ", e);
                    }
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemClick(int position, Object data) {
        Article article = (Article) data;
        Log.d(TAG, "onItemClick: " + article.getPdfLink());
        if (article.getDetails() != null) {
            if (article.getPdfLink() != null) {
                openPDFPage(article);
            } else {
                Intent intent = new Intent(this, ArticleDetailsActivity.class);
                intent.putExtra(Constants.ARTICLE, article);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (article.getPdfLink() != null) {
            openPDFPage(article);
        }

    }

    private void openPDFPage(Article article) {
        Intent viewArticleIntent = new Intent(this, WebViewActivity.class);
        viewArticleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        viewArticleIntent.putExtra(Constants.PDF_URL, article.getPdfLink());
        viewArticleIntent.putExtra(Constants.TITLE, article.getTitle());
        try {
            startActivity(viewArticleIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        refreshList();
    }
}
