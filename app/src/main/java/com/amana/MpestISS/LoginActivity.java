package com.amana.MpestISS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amana.MpestISS.dashboard.DashboardActivity;
import com.amana.MpestISS.joblist.JobDetailsActivity;
import com.amana.MpestISS.model.login.LoginApiRequest;
import com.amana.MpestISS.model.login.LoginApiResponse;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_signin)
    Button btn_Signin;
    @BindView(R.id.errorMsg_txt)
    TextView txt_errorMsg;
    private AppPreferences _appPrefs;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        _appPrefs = new AppPreferences(getApplicationContext());
        btn_Signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin:
                loginValidation();
                break;
        }
    }

    public void loginValidation() {
        Utils.hideKeyBoard(mContext, _passwordText);
        if (!validate()) {
            return;
        } else {
            if (Utils.isNetConnected(mContext)) {
                GetSignInDetails();
            } else {

                Utils.noNetPopup(mContext);
            }
        }
    }


    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            txt_errorMsg.setText("Please enter username");
            txt_errorMsg.setVisibility(View.VISIBLE);
            valid = false;
        } else if (password.isEmpty()) {
            txt_errorMsg.setText("Please enter password");
            txt_errorMsg.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_errorMsg.setVisibility(View.GONE);
        }

        return valid;
    }

    /**
     * Sign In method
     */
    public void GetSignInDetails() {

        Utils.showCustomDialog(mContext,"Authenticating...");
        LoginApiRequest loginReq = new LoginApiRequest();
        loginReq.setUserID(_emailText.getText().toString().trim());
        loginReq.setPassword(_passwordText.getText().toString().trim());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<LoginApiResponse> call = apiService.signIN(loginReq);
        call.enqueue(new Callback<LoginApiResponse>() {
            @Override
            public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {

                try {

                    if (response.body().getStatusCode() == 200) {

                        txt_errorMsg.setVisibility(View.GONE);
                        _appPrefs.saveUserID(_emailText.getText().toString());
                        _appPrefs.saveUserName(response.body().getData().getUserName());
                        _appPrefs.saveCLIENTID(response.body().getData().getClientID());
                        _appPrefs.saveEMPLOYEEID(response.body().getData().getEmployeeID());
                        _appPrefs.saveUserEmail(response.body().getData().getEmail());

                        MasterDbLists.getMaterialList(mContext,response.body().getData().getClientID().toString()); // gets Master details from service and Save it in DB

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    } else {
                        String Msg = response.body().getMessage().toString();
                        if(Msg.equalsIgnoreCase("Password Not Matched")){
                            Msg = "Incorrect Password";
                        }else if(Msg.equalsIgnoreCase("userID Not Found")){
                            Msg = "Login ID Not Found";
                        }
                        txt_errorMsg.setVisibility(View.VISIBLE);
                        txt_errorMsg.setText(""+Msg);
                        _appPrefs.saveUserID("");

                    }
                    Utils.dismissDialog();
                } catch (Exception e) {
                    Utils.dismissDialog();
                    txt_errorMsg.setVisibility(View.VISIBLE);
                    txt_errorMsg.setText("Unable to connect.");
                    e.printStackTrace();

                }

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onFailure(Call<LoginApiResponse> call, Throwable t) {

                Utils.dismissDialog();
                txt_errorMsg.setVisibility(View.VISIBLE);
                txt_errorMsg.setText("Can't connect to server. Please try again.");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

        });
    }
}

