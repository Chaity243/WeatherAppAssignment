package com.example.cricone.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cricone.LandingPage.HomeActivity;
import com.example.cricone.R;
import com.example.cricone.Responses.LoginResponse;
import com.example.cricone.SessionManager;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edusername)
    EditText edusername;

    @BindView(R.id.edemail)
    EditText edemail;

    @BindView(R.id.edpassword)
    EditText edpassword;

    @BindView(R.id.edconfirmpassword)
    EditText edconfirmpassword;

    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.registerBtn)
    Button registerBtn;

    @BindView(R.id.loginText)
    TextView loginText;

    @BindView(R.id.registerText)
    TextView registerText;

    @BindView(R.id.spgender)
    Spinner spgender;

    LoginViewModel loginViewModel;
    SessionManager session;
    rx.Observable<Boolean> combinedObservables;
    rx.Observable<Boolean> combinedObservablesRegister;
    Boolean isRegister = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session =new SessionManager(getApplicationContext());
        ButterKnife.bind(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.initialize();

        setupView();
        createRegistrationObservable();



    }



    private void createRegistrationObservable() {
        rx.Observable<CharSequence> usernameChangeObservable =RxTextView.textChanges(edusername);
        usernameChangeObservable
                .map(this::isValidUserName)
                .subscribe(isValid -> edusername.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, null));

        rx.Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(edemail);
        emailChangeObservable
                .map(this::isValidEmail)
                .subscribe(isValid -> edemail.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, null));


        rx.Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(edpassword);
        passwordChangeObservable
                .map(this::isValidPassword)
                .subscribe(isValid -> edpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, null));

        rx.Observable<CharSequence> confirmPasswordChangeObservable = RxTextView.textChanges(edconfirmpassword);
        passwordChangeObservable
                .map(this::isCnfPasswordEqual)
                .subscribe(isValid -> edpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, null));

         combinedObservablesRegister = Observable.combineLatest(usernameChangeObservable,
                emailChangeObservable,passwordChangeObservable,confirmPasswordChangeObservable, (o1, o2,o3,o4) -> isValidUserName(o1) && isValidEmail(o2) && isValidPassword(o3) && isCnfPasswordEqual(o4) && isRegister);

         combinedObservables = Observable.combineLatest(
                emailChangeObservable,passwordChangeObservable, (o2,o3) -> isValidEmail(o2) && isValidPassword(o3) && !isRegister);

        combinedObservables.subscribe(isVisible -> loginBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE ));
        combinedObservablesRegister.subscribe( isVisible -> registerBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE ));



        subscribeObservable(usernameChangeObservable,emailChangeObservable, passwordChangeObservable);
    }




    private void subscribeObservable(Observable<CharSequence> usernameChangeObservable, Observable<CharSequence> emailChangeObservable,
                                     Observable<CharSequence> passwordChangeObservable) {
    }



    private boolean isValidUserName(CharSequence value){
        return value.toString().matches("^[a-z0-9_-]{3,15}$");

    }
    private boolean isValidEmail(CharSequence value){
        return value.toString().matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    private boolean isValidPassword(CharSequence value){
        return value.toString().matches("^(?=.*\\d).{4,8}$");
    }
    private boolean isCnfPasswordEqual(CharSequence value){
        return value.toString().equals(edpassword.getText().toString());
    }

    private void setupView() {
        registerText.setOnClickListener(v -> {
            edusername.setVisibility(View.VISIBLE);
            edconfirmpassword.setVisibility(View.VISIBLE);
            edusername.setText("");
            edconfirmpassword.setText("");
            edpassword.setText("");
            edemail.setText("");
            isRegister = true;
        });

        loginText.setOnClickListener(v -> {
            edusername.setVisibility(View.GONE);
            edconfirmpassword.setVisibility(View.GONE);
            edusername.setText("");
            edconfirmpassword.setText("");
            edpassword.setText("");
            edemail.setText("");
            isRegister = false;
        });

        loginBtn.setOnClickListener(v -> {
            String email = edemail.getText().toString();
            String password = edpassword.getText().toString();
            loginViewModel.performLogin(email,password).observe(this, new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                   // Log.e("MESSEGE CODE",loginResponse.getMessages().getRespondMessage1());
                    openHomeActivity(email,password);
                }
            });
        });

        registerBtn.setOnClickListener(v->{
            String email = edemail.getText().toString();
            String password = edpassword.getText().toString();
            String user = edusername.getText().toString();
            loginViewModel.performRegister(email,password,user).observe(this, new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                     Log.e("MESSEGE CODE",loginResponse.getMessages().getRespondMessage1());
                    //openHomeActivity(email,password);
                }
            });
        });
    }

    private void openHomeActivity(String username ,String password){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();

    }


}
