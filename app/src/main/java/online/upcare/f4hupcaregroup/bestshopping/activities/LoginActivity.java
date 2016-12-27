package online.upcare.f4hupcaregroup.bestshopping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.upcare.f4hupcaregroup.bestshopping.R;
import online.upcare.f4hupcaregroup.bestshopping.services.AccountServices;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.activity_login_linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.activity_login_registerButton)
    Button registerButton;

    @BindView(R.id.activity_login_loginButton)
    Button loginButton;

    @BindView(R.id.activity_login_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_login_userPassword)
    EditText userPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        linearLayout.setBackgroundResource(R.drawable.background_screen_two);
        FirebaseAuth.getInstance().signOut();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.setMessage("Attempting to Logging Account");
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.activity_login_registerButton)
    public void setRegisterButton(){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.activity_login_loginButton)
    public void setLoginButton(){
        bus.post(new AccountServices.LogUserInRequest(userEmail.getText().toString(), userPassword.getText().toString(), progressDialog));
    }

    @Subscribe
    public void LogUserIn(AccountServices.LogUserInResponse response){
        if(!response.didSucceed()){
            userEmail.setError(response.getPropertyError("email"));
            userPassword.setError(response.getPropertyError("password"));
        }
    }
}
