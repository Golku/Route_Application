package com.example.jason.route_application.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application.R;
import com.example.jason.route_application.controller.CommentInputActivityController;
import com.example.jason.route_application.model.pojos.DatabaseResponse;
import com.example.jason.route_application.model.pojos.SingleComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommentInputActivity extends AppCompatActivity {

    public boolean active = false;

    private final String log_tag = "AddressDetailsLog";

    private CommentInputActivityController controller;

    private TextView employeeNameTextView;
    private TextView dateTextView;
    private EditText commentText;

    private ProgressBar loadingBar;
    private SimpleDateFormat sdf;
    private String date;
    private String employeeId;

    private ArrayList<String> addressInformation;

    private Button addCommentBtn;

    private SingleComment singleComment;

    private InputMethodManager imm;

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_input);

        active = true;

        controller = new CommentInputActivityController(this);

        addressInformation = getIntent().getStringArrayListExtra("addressInformation");

        employeeId = String.valueOf(1);

        sdf = new SimpleDateFormat("dd.MM.yyyy");
        date = sdf.format(System.currentTimeMillis());

        employeeNameTextView = findViewById(R.id.employeeNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        commentText = findViewById(R.id.commentText);
        loadingBar = findViewById(R.id.progressBar);
        addCommentBtn = findViewById(R.id.addCommentBtn);
        addCommentBtn.setOnClickListener(onClickListener);

        employeeNameTextView.setText(employeeId);
        dateTextView.setText(date);

        singleComment = new SingleComment();

        singleComment.setStreet(addressInformation.get(0));
        singleComment.setPostCode(addressInformation.get(1));
        singleComment.setCity(addressInformation.get(2));
        singleComment.setEmployedId(employeeId);
        singleComment.setDate(date);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void showCommentInputStatus(DatabaseResponse databaseResponse){

        controller.displayMessage("loadingBar", false, "");

        if(databaseResponse.getError()){
            controller.displayMessage("toast", true, databaseResponse.getErrorMessage());
        }else{
            controller.displayMessage("toast", true, "Comment added!");
        }

    }

    public void showLoadingBar(){
        loadingBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingBar(){
        loadingBar.setVisibility(View.GONE);
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {

            if (v == addCommentBtn) {

                if(commentText.getText().toString().isEmpty()){
                    controller.displayMessage("toast", true, "The comment was empty.");
                }else{
                    controller.displayMessage("loadingBar", true,"");
                    singleComment.setComment(commentText.getText().toString());
                    commentText.setText("");
                    controller.addCommentToAddress(singleComment);
                }

            }

        }
    };

}
