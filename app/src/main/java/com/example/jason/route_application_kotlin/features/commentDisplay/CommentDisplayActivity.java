package com.example.jason.route_application_kotlin.features.commentDisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.database.CommentInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDisplayActivity extends AppCompatActivity {

    @BindView(R.id.employeeNameTextView)
    TextView employeeNameTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.commentTextView)
    TextView commentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_display);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        CommentInformation commentInformation = getIntent().getParcelableExtra("commentInformation");
        employeeNameTextView.setText(commentInformation.getEmployeeId());
        dateTextView.setText(commentInformation.getDate());
        commentTextView.setText(commentInformation.getComment());
    }
}
