package com.adc.gads2020leaderboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.adc.gads2020leaderboard.main.UserClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitActivity extends AppCompatActivity {
    //create Retrofit instance
    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit sRetrofit = sBuilder.build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        findViewById(R.id.constraint_submit_view).setVisibility(View.VISIBLE);
        final EditText editTextFirstName = findViewById(R.id.editText_firstName);
        final EditText editTextLastName = findViewById(R.id.editText_lastName);
        final EditText editTextEmailAddress = findViewById(R.id.editText_emailAddress);
        final EditText editTextGithubLink = findViewById(R.id.editText_gitHubLink);
        findViewById(R.id.btnExecuteSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!editTextFirstName.getText().toString().equals("")) && !editTextLastName.getText().toString().equals("")&&
                        !editTextEmailAddress .getText().toString().equals("") && !editTextGithubLink.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView =
                            LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
                    builder.setView(dialogView)
                            .setCancelable(false);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.show();

                    Button btnYes = alertDialog.findViewById(R.id.button_yes);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            executeSendFeedbackForm(
                                    editTextFirstName.getText().toString(),
                                    editTextLastName.getText().toString(),
                                    editTextEmailAddress.getText().toString(),
                                    editTextGithubLink.getText().toString(),
                                    view
                            );
                            alertDialog.dismiss();
                        }
                    });
                    ImageButton btnNo = alertDialog.findViewById(R.id.button_close);
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void executeSendFeedbackForm(String firstName, String lastName, String emailAddress, String githubLink, final View view) {
        UserClient userClient = sRetrofit.create(UserClient.class);
        Call<ResponseBody> call = userClient.SendUserFeedBack(
                firstName,
                lastName,
                emailAddress,
                githubLink
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                createAlertDialog(R.layout.custom_successful_dialog, view);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                createAlertDialog(R.layout.custom_failure_dialog, view);
            }
        });
    }

    private void createAlertDialog(int layout, View view) {
        findViewById(R.id.constraint_submit_view).setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView =
                LayoutInflater.from(view.getContext()).inflate(layout, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
    }
}