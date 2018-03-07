package com.example.jason.route_application_kotlin.features.commentInput;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class CommentInputActivity extends DaggerAppCompatActivity implements MvpCommentInput.View{

    @Inject CommentInputPresenter presenter;

    @BindView(R.id.employeeNameTextView)
    TextView employeeNameTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.commentEditText)
    EditText commentEditText;
    @BindView(R.id.addCommentBtn)
    Button addCommentBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_input);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        FormattedAddress formattedAddress = getIntent().getParcelableExtra("formattedAddress");
        String employeeId = "1";
        presenter.setUpInfo(employeeId, formattedAddress);
    }

    @Override
    public void updateTextViews(String employeeId, String date) {
        employeeNameTextView.setText(employeeId);
        dateTextView.setText(date);
    }

    @OnClick(R.id.addCommentBtn)
    @Override
    public void onAddCommentBtnClick() {
        String comment = commentEditText.getText().toString();
        presenter.onAddCommentBtnClick(comment);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void onStartNetworkOperation() {
        addCommentBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        messageToUserTextView.setVisibility(View.VISIBLE);
        messageToUserTextView.setText("Adding comment...");
    }

    @Override
    public void onFinishNetworkOperation() {
        addCommentBtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        messageToUserTextView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}