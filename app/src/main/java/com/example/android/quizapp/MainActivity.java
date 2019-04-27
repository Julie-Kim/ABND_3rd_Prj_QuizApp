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

    private static final int TOTAL_QUESTION_NUMBERS = 10;
    private static final String QUESTION_NUMBER_2_ANSWER = "15";
    private static final String QUESTION_NUMBER_9_ANSWER = "12";

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

    private boolean isCorrectAnswer(int questionNumber) {
        RadioButton radioButton;
        EditText editText;

        switch (questionNumber) {
            case 1:
                radioButton = findViewById(R.id.q1_answer2);
                return ((RadioButton) findViewById(R.id.q1_answer2)).isChecked();

            case 2:
                editText = findViewById(R.id.q2_answer_text);
                return QUESTION_NUMBER_2_ANSWER.equals(editText.getText().toString());

            case 3:
                return ((CheckBox) findViewById(R.id.q3_answer1)).isChecked()
                        && ((CheckBox) findViewById(R.id.q3_answer3)).isChecked()
                        && !((CheckBox) findViewById(R.id.q3_answer2)).isChecked()
                        && !((CheckBox) findViewById(R.id.q3_answer4)).isChecked()
                        && !((CheckBox) findViewById(R.id.q3_answer5)).isChecked();

            case 4:
                radioButton = findViewById(R.id.q4_answer1);
                return radioButton.isChecked();

            case 5:
                radioButton = findViewById(R.id.q5_answer2);
                return radioButton.isChecked();

            case 6:
                return ((CheckBox) findViewById(R.id.q6_answer1)).isChecked()
                        && ((CheckBox) findViewById(R.id.q6_answer4)).isChecked()
                        && !((CheckBox) findViewById(R.id.q6_answer2)).isChecked()
                        && !((CheckBox) findViewById(R.id.q6_answer3)).isChecked();

            case 7:
                radioButton = findViewById(R.id.q7_answer2);
                return radioButton.isChecked();

            case 8:
                radioButton = findViewById(R.id.q8_answer2);
                return radioButton.isChecked();

            case 9:
                editText = findViewById(R.id.q9_answer_text);
                return QUESTION_NUMBER_9_ANSWER.equals(editText.getText().toString());

            case 10:

            default:
                return false;
        }
    }

    public void onSubmitButtonClick(View view) {
        mQuizResult.append(getArrayIndex(1), isCorrectAnswer(1));
        mQuizResult.append(getArrayIndex(2), isCorrectAnswer(2));
        mQuizResult.append(getArrayIndex(3), isCorrectAnswer(3));
        mQuizResult.append(getArrayIndex(4), isCorrectAnswer(4));
        mQuizResult.append(getArrayIndex(5), isCorrectAnswer(5));
        mQuizResult.append(getArrayIndex(6), isCorrectAnswer(6));
        mQuizResult.append(getArrayIndex(7), isCorrectAnswer(7));
        mQuizResult.append(getArrayIndex(8), isCorrectAnswer(8));
        mQuizResult.append(getArrayIndex(9), isCorrectAnswer(9));
        mQuizResult.append(getArrayIndex(10), isCorrectAnswer(10));

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

        TextView resultNumberFive = findViewById(R.id.result_05);
        resultNumberFive.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(5))));

        TextView resultNumberSix = findViewById(R.id.result_06);
        resultNumberSix.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(6))));

        TextView resultNumberSeven = findViewById(R.id.result_07);
        resultNumberSeven.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(7))));

        TextView resultNumberEight = findViewById(R.id.result_08);
        resultNumberEight.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(8))));

        TextView resultNumberNine = findViewById(R.id.result_09);
        resultNumberNine.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(9))));

        TextView resultNumberTen = findViewById(R.id.result_10);
        resultNumberTen.setText(getResultTextResId(mQuizResult.valueAt(getArrayIndex(10))));

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
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}