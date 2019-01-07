package com.amana.MPESTPestoff.comments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.model.comments.CommetsResponse;
import com.amana.MPESTPestoff.model.comments.Datum;
import com.amana.MPESTPestoff.restApi.ApiClient;
import com.amana.MPESTPestoff.restApi.ApiInterface;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.MasterDbLists;
import com.amana.MPESTPestoff.utils.Utils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;
    CommentsAdapter mListAdapter;

    ArrayList<Datum> arr_commnetList = new ArrayList<>();

    String mRequestID = "",mServiceID ="";

    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab_clear)
    FloatingActionButton fab_sync;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
         tv = new TextView(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
         int dp = (int) (mContext.getResources().getDimension(R.dimen.title_size) / mContext.getResources().getDisplayMetrics().density);
        tv.setTextSize(dp);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Avenir-Heavy.otf");
        tv.setTypeface(tf);
        ((AppCompatActivity) mContext).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) mContext).getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        if(Utils.isNetConnected(mContext)) {
            GetAllCommentsList();
        } else {
            Utils.noNetPopup(mContext);
        }



    }

    public void init() {

        mRequestID = getIntent().getStringExtra("RequestID");
        mServiceID = getIntent().getStringExtra("ServiceID");
        tv.setText(""+mServiceID);
        fab_sync.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


        fab_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popup_AddComment();
            }
        });
    }

    /**
     * Pull n push Recycler view Reloader
     */
    void refreshItems() {
        // Load items
        //gets data from local db
        UpdateCommentsList();
        // Load complete
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Fetching task list from Save Db and Populates to recyclerView
     */
    void UpdateCommentsList() {

        if (arr_commnetList.size() == 0) { // if no data no data text is set
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else { // if data available
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            mListAdapter = new CommentsAdapter(mContext, arr_commnetList);
            mRecyclerView.setAdapter(mListAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // ((MainActivity) a).performQuoteListClick();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // Get Comments List
    public void GetAllCommentsList() {

        Utils.showCustomDialog(mContext);


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CommetsResponse> call = apiService.GetallComments(mRequestID);
        call.enqueue(new Callback<CommetsResponse>() {
            @Override
            public void onResponse(Call<CommetsResponse> call, Response<CommetsResponse> response) {

                try {

                    int mStatusCode = response.body().getStatusCode();

                    if (mStatusCode == 200) {
                        arr_commnetList = response.body().getData();
                        UpdateCommentsList();
                    }

                    Utils.dismissDialog();
                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<CommetsResponse> call, Throwable t) {
                Utils.dismissDialog();
            }

        });

    }


    // Add Comment
    public void AddCommentsService(String mComment) {

        Utils.showCustomDialog(mContext);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.AddComments(mRequestID,_appPrefs.getUserID(),mComment,_appPrefs.getUserID(),"true");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(response.body()));

                    int mStatusCode = object.getInt("statusCode");
                    String  message= object.optString("message");
                    if (mStatusCode == 200) {
                        Utils.dismissDialog();
                        GetAllCommentsList();
                    }else{

                    }

                    Utils.dismissDialog();
                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Utils.dismissDialog();
            }

        });

    }


    /**
     * Add Comments
     */
    private void Popup_AddComment() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_comments, null);
        alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close = (CircleImageView) promptsView
                .findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        final EditText edt_qty = (EditText) promptsView.findViewById(R.id.input_comment);
        final TextInputLayout err_commm = (TextInputLayout) promptsView.findViewById(R.id.err_comments);
        Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                String mcomment = edt_qty.getText().toString().trim();

                if(mcomment.isEmpty()){

                }else{
                    alertDialog.dismiss();
                    AddCommentsService(mcomment);
                }

            }
        });
        alertDialog.show();

    }


}
