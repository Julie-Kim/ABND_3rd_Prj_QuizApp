package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int TOTAL_QUESTION_NUMBERS = 4;
    private static final String QUESTION_NUMBER_2_ANSWER = "15";

    private SparseBooleanArray mQuizResult = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomActionBar();
    }

    private void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);
    }

    private boolean isQuestionNumberOneCorrect() {
        RadioButton radioButton = findViewById(R.id.q1_answer2);
        return radioButton.isChecked();
    }

    private boolean isQuestionNumberTwoCorrect() {
        EditText editText = findViewById(R.id.q2_answer_text);
        return QUESTION_NUMBER_2_ANSWER.equals(editText.getText().toString());
    }

    private boolean isQuestionNumberThreeCorrect() {
        CheckBox checkBoxAnswer01 = findViewById(R.id.q3_answer1);
        CheckBox checkBoxAnswer02 = findViewById(R.id.q3_answer2);
        CheckBox checkBoxAnswer03 = findViewById(R.id.q3_answer3);
        CheckBox checkBoxAnswer04 = findViewById(R.id.q3_answer4);
        CheckBox checkBoxAnswer05 = findViewById(R.id.q3_answer5);
        return checkBoxAnswer01.isChecked() && checkBoxAnswer03.isChecked()
                && !checkBoxAnswer02.isChecked() && !checkBoxAnswer04.isChecked() && !checkBoxAnswer05.isChecked();
    }

    private boolean isQuestionNumberFourCorrect() {
        RadioButton radioButton = findViewById(R.id.q4_answer1);
        return radioButton.isChecked();
    }

    public void onSubmitButtonClick(View view) {
        mQuizResult.append(getArrayIndex(1), isQuestionNumberOneCorrect());
        mQuizResult.append(getArrayIndex(2), isQuestionNumberTwoCorrect());
        mQuizResult.append(getArrayIndex(3), isQuestionNumberThreeCorrect());
        mQuizResult.append(getArrayIndex(4), isQuestionNumberFourCorrect());

        showResultMessage(getNumberOfCorrectAnswer());
        showResultTable();
    }

    private int getArrayIndex(int questionNumber) {
        return questionNumber - 1;
    }

    private int getNumberOfCorrectAnswer() {
        int correctNumbers = 0;
        for (int i = 0; i < mQuizResult.size(); i++) {
            if (mQuizResult.valueAt(i)) {
                correctNumbers++;
            }
        }

        return correctNumbers;
    }

    private void showResultMessage(int correctNumbers) {
        String message = "";
        if (TOTAL_QUESTION_NUMBERS == correctNumbers) {
            message = getString(R.string.perfect);
        }
        message += getString(R.string.correct_number, correctNumbers);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showResultTable() {
        TextView resultNumberOne = findViewById(R.id.result_01);
        resultNumberOne.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(1))));

        TextView resultNumberTwo = findViewById(R.id.result_02);
        resultNumberTwo.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(2))));

        TextView resultNumberThree = findViewById(R.id.result_03);
        resultNumberThree.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(3))));

        TextView resultNumberFour = findViewById(R.id.result_04);
        resultNumberFour.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(4))));

        findViewById(R.id.result_table).setVisibility(View.VISIBLE);

        scrollToBottom();
    }

    private int getResultTextResId(boolean correct) {
        return correct ? R.string.result_o : R.string.result_x;
    }

    private void scrollToBottom() {
        final ScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, scrollView.getBottom());
            }
        });
    }
}