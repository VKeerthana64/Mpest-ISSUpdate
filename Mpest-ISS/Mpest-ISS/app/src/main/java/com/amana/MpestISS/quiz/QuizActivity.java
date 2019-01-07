package com.amana.MpestISS.quiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.DecimalDigitsInputFilter;
import com.amana.MpestISS.logs.LogsAdapter;
import com.amana.MpestISS.model.pesttype.PesttypeResponse;
import com.amana.MpestISS.model.quiz.Datum;
import com.amana.MpestISS.model.quiz.QuizOption;
import com.amana.MpestISS.model.quiz.QuizResponse;
import com.amana.MpestISS.model.realm.LogsTable;
import com.amana.MpestISS.model.realm.Materials;
import com.amana.MpestISS.myjob.UploadBgService;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.google.gson.JsonObject;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amana.MpestISS.utils.MasterDbLists.GetQtyUnitForMaterials;


public class QuizActivity extends AppCompatActivity implements QuizAdapter.ItemClickListener {

    AppPreferences _appPrefs;
    Context mContext;
    ArrayList<Datum> mData = new ArrayList<>();

    QuizAdapter mQuizAdapter;

    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;

    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    String Quiz_Id = "", Answer_Id = "", CreatedBy = "";
    int ClickId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        // getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Quiz Compilation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        getPestTypeList();
    }

    public void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


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

    // Service Calls

    /**
     * gets Pest Type
     */
    public void getPestTypeList() {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<QuizResponse> call = apiService.getQuiz(_appPrefs.getCLIENTID());
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {

                try {
                    int mStatusCode = response.body().getStatusCode();
                    if (mStatusCode == 200) {


                        mData = (ArrayList<Datum>) response.body().getData();

                        if (mData.size() == 0) { // if no data no data text is set
                            mRecyclerView.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else { // if data available
                            mRecyclerView.setVisibility(View.VISIBLE);
                            tv_nodata.setVisibility(View.GONE);
                            mQuizAdapter = new QuizAdapter(mContext, mData);
                            mRecyclerView.setAdapter(mQuizAdapter);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mQuizAdapter.setClickListener(QuizActivity.this);
                        }

                        Utils.dismissDialog();

                    }


                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("Quiz", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("Quiz", "unable to get details");

            }

        });
    }


    private void quizanswerAPI(JSONObject jobject) {

        Utils.showCustomDialog(mContext);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.quizanswer(getRequestBody(jobject));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                handleQuizResponse(rawResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Utils.dismissDialog();
                Toast.makeText(QuizActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * @param rawResponse
     */
    private void handleQuizResponse(Response<ResponseBody> rawResponse) {
        try {
            String response = rawResponse.body().string();
           // Toast.makeText(QuizActivity.this, "response -->" + response, Toast.LENGTH_SHORT).show();

            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();

            try {
               int mStatusCode = object.optInt("statusCode");

                if(mStatusCode == 200){

                    Toast.makeText(QuizActivity.this, ""+object.optString("message"), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuizActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utils.dismissDialog();
        }
    }


    /**
     * Gets request body.
     *
     * @return the request body
     */
    public static RequestBody getRequestBody(JSONObject objt) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), objt.toString());
    }

    @Override
    public void onItemClick(View view, int position) {

        AppLogger.info("Quiz Details", "PestType " + mQuizAdapter.getItem(position) + ", position " + position);


        ArrayList<QuizOption> quizOption = mData.get(position).getQuizOptions();

        popup_QuantityUnit(position, quizOption);


    }

    private void popup_QuantityUnit(final int position, final ArrayList<QuizOption> arr_quizOptions) {
        ClickId = -1;
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_quiz, null);
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
        RadioGroup rgAnswer = (RadioGroup) promptsView.findViewById(R.id.rg_answers);
        RadioGroup.LayoutParams rprms;

        for (int i = 0; i < arr_quizOptions.size(); i++) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(arr_quizOptions.get(i).getOption());
            radioButton.setId(i);
            rprms = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rgAnswer.addView(radioButton, rprms);
        }


        rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ClickId = checkedId;

            }
        });


        Button btn_submit = (Button) promptsView.findViewById(R.id.Submit_btn);

        btn_submit.setEnabled(true);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ClickId == -1) {
                    Toast.makeText(mContext, "Please select options..", Toast.LENGTH_SHORT).show();
                } else {
                 //   Toast.makeText(mContext, "options selected..", Toast.LENGTH_SHORT).show();

                    try {
                        String Quiz_Id = mData.get(position).getId();
                        String Answer_Id = arr_quizOptions.get(ClickId).getId();
                        String CreatedBy = mData.get(position).getCreatedBy();
                        JSONObject Jobject = new JSONObject();

                        Jobject.put("Quiz_Id", Quiz_Id);
                        Jobject.put("Answer_Id", Answer_Id);
                        Jobject.put("CreatedBy", CreatedBy);
                        alertDialog.dismiss();
                        quizanswerAPI(Jobject); // Api call to update answers
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(QuizActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });
        alertDialog.show();

    }

}
