package com.example.jason.route_application.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jason.route_application.R;
import android.widget.TextView;


import java.util.ArrayList;

public class CommentDisplayActivity extends AppCompatActivity {

    private ArrayList<String > commentInformation;

    private TextView employedNameTextView;
    private TextView dateTextView;
    private TextView commentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_display);

        commentInformation = getIntent().getStringArrayListExtra("commentInformation");

        employedNameTextView = findViewById(R.id.employeeNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        commentTextView = findViewById(R.id.commentTextView);

        employedNameTextView.setText(commentInformation.get(0));
        dateTextView.setText(commentInformation.get(1));
        commentTextView.setText(commentInformation.get(2));

    }
}
