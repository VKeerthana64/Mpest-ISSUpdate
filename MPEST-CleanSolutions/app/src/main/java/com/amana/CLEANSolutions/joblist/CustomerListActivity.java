package com.amana.CLEANSolutions.joblist;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amana.CLEANSolutions.R;
import com.amana.CLEANSolutions.model.customerlist.CustomersListResponse;
import com.amana.CLEANSolutions.model.customerlist.Datum;
import com.amana.CLEANSolutions.restApi.ApiClient;
import com.amana.CLEANSolutions.restApi.ApiInterface;
import com.amana.CLEANSolutions.utils.AppPreferences;
import com.amana.CLEANSolutions.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerListActivity extends AppCompatActivity implements CustomerListAdapter.ItemClickListener{

    AppPreferences _appPrefs;
    Context mContext;
    CustomerListAdapter mListAdapter;

    String mCustomerId="";

    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Customer Job History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (Utils.isNetConnected(mContext)) {
                // get
                mCustomerId = extras.getString("CustomerId");
                GetCustomerList(extras.getString("CustomerId"));

            } else {
                Utils.noNetPopup(mContext);
            }

        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    /**
     * Pull n push Recycler view Reloader
     */
    void refreshItems() {
        // Load items
        if (Utils.isNetConnected(mContext) && mCustomerId.length() >0) {
            // get
            GetCustomerList(mCustomerId);

        } else {
            Utils.noNetPopup(mContext);
        }
        // Load complete
        mSwipeRefreshLayout.setRefreshing(false);
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


    /**
     * GET Job Update
     */
    public void GetCustomerList(String _id) {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CustomersListResponse> call = apiService.CustomerList(_id);
        call.enqueue(new Callback<CustomersListResponse>() {
            @Override
            public void onResponse(Call<CustomersListResponse> call, Response<CustomersListResponse> response) {

                try {
                    CustomersListResponse customersListResponse = new CustomersListResponse();

                    customersListResponse = response.body();

                    int mStatusCode = customersListResponse.getStatusCode();

                    if (mStatusCode == 200) {

                        ArrayList<Datum> mList = (ArrayList<Datum>) customersListResponse.getData();

                        if (mList.size() == 0) { // if no data no data text is set
                            mSwipeRefreshLayout.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else { // if data available
                            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                            tv_nodata.setVisibility(View.GONE);
                            mListAdapter = new CustomerListAdapter(mContext, mList);
                            mRecyclerView.setAdapter(mListAdapter);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }

                    }else{
                        Utils.CommanPopup(mContext,"StatusCode --"+mStatusCode);
                    }

                    Utils.dismissDialog();
                } catch (Exception e) {

                    e.printStackTrace();
                    Utils.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<CustomersListResponse> call, Throwable t) {
                Utils.dismissDialog();
            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
