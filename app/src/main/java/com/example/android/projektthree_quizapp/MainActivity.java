package com.example.android.projektthree_quizapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private int finalScore = 0;

    //This part with keyboard I get from stackoverflow topic
    private static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    //This part with keyboard I also get from stackoverflow topic
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int key[] = new int[2];
            v.getLocationOnScreen(key);
            float x = ev.getRawX() + v.getLeft() - key[0];
            float y = ev.getRawY() + v.getTop() - key[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);


    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.question6);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.choose_your);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);
    }

    // Method for result calculation
    private int scoreCalculation() {
        finalScore = 0;

        RadioButton questionNumber1b = (RadioButton) findViewById(R.id.radio_button_1b);
        if (questionNumber1b.isChecked()) {
            finalScore = finalScore + 2;
        }
        RadioButton questionNumber1c = (RadioButton) findViewById(R.id.radio_button_1c);
        if (questionNumber1c.isChecked()) {
            finalScore = finalScore + 1;
        }
        RadioButton questionNumber2 = (RadioButton) findViewById(R.id.radio_button_2a);
        if (questionNumber2.isChecked()) {
            finalScore = finalScore + 2;
        }
        RadioButton questionNumber3 = (RadioButton) findViewById(R.id.radio_button_3b);
        if (questionNumber3.isChecked()) {
            finalScore = finalScore + 1;
        }
        RadioButton questionNumber4 = (RadioButton) findViewById(R.id.radio_button_4b);
        if (questionNumber4.isChecked()) {
            finalScore = finalScore + 1;
        }
        RadioButton questionNumber5 = (RadioButton) findViewById(R.id.radio_button_5a);
        if (questionNumber5.isChecked()) {
            finalScore = finalScore + 2;
        }
        EditText answer6 = (EditText) findViewById(R.id.question6);
        String choiceInputText = answer6.getText().toString();
        if (choiceInputText.equals("Wild")) {
            finalScore = finalScore + 2;
        }

        RadioButton questionNumber7a = (RadioButton) findViewById(R.id.radio_button_7a);
        if (questionNumber7a.isChecked()) {
            finalScore = finalScore + 1;
        }
        RadioButton questionNumber7b = (RadioButton) findViewById(R.id.radio_button_7b);
        if (questionNumber7b.isChecked()) {
            finalScore = finalScore + 2;
        }
        CheckBox questionNumber8a = (CheckBox) findViewById(R.id.checkbox_8a);
        if (questionNumber8a.isChecked()) {
            finalScore = finalScore + 3;
        }
        CheckBox questionNumber8c = (CheckBox) findViewById(R.id.checkbox_8c);
        if (questionNumber8c.isChecked()) {
            finalScore = finalScore + 1;
        }
        return finalScore;

    }

    private void displayResults(int finalScore) {
        //Getting User name
        TextView userName = (TextView) findViewById(R.id.player_EditText);
        String nameOfUser = userName.getText().toString();
        String totalScoreDisplay;
        if (finalScore > 9) {
            // Message is: Congratulations, @nameOfUser! You are a cat person! You scored @finalScore out of 16.
            totalScoreDisplay = getString(R.string.ResultsA1) + nameOfUser + getString(R.string.ResultsA2) + "\n" + getString(R.string.ResultsA3) + finalScore + getString(R.string.ResultsA4);
        } else {
            // Message is: You are probably a dog person! You scored @finalScore out of 16.
            totalScoreDisplay = getString(R.string.ResultsB1) + "\n" + getString(R.string.ResultsA3) + finalScore + getString(R.string.ResultsA4);
        }

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, totalScoreDisplay, duration);
        LinearLayout layout = (LinearLayout) toast.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }
        toast.show();
    }

    private void errorMessage() {
        //Error message for missing answers
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.errorMessageText);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void errorMessage2() {
        //Error message for missing pet :)
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.errorMessageText2);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        scoreCalculation();
        displayResults(finalScore);

    }

    private void errorMessage3() {
        //Error message for missing name
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.errorMessageText3);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    // Method for calculating + displaying the Results after pressing Submit button

    public void submitResults(View view) {
        TextView userName = (TextView) findViewById(R.id.player_EditText);
        if (userName.getText().length() < 1) {
            errorMessage3();
        } else {
            checkQ1();
        }


    }

    private void checkQ1() {
        RadioButton questionNumber1a = (RadioButton) findViewById(R.id.radio_button_1a);
        RadioButton questionNumber1b = (RadioButton) findViewById(R.id.radio_button_1b);
        RadioButton questionNumber1c = (RadioButton) findViewById(R.id.radio_button_1c);
        RadioButton questionNumber1d = (RadioButton) findViewById(R.id.radio_button_1d);

        if (questionNumber1a.isChecked() || questionNumber1b.isChecked() || questionNumber1c.isChecked() || questionNumber1d.isChecked()) {
            checkQ2();
        } else {
            errorMessage();
        }

    }

    private void checkQ2() {
        RadioButton questionNumber2a = (RadioButton) findViewById(R.id.radio_button_2a);
        RadioButton questionNumber2b = (RadioButton) findViewById(R.id.radio_button_2b);
        if (questionNumber2a.isChecked() || questionNumber2b.isChecked()) {
            checkQ3();
        } else {
            errorMessage();
        }
    }

    private void checkQ3() {
        RadioButton questionNumber3a = (RadioButton) findViewById(R.id.radio_button_3a);
        RadioButton questionNumber3b = (RadioButton) findViewById(R.id.radio_button_3b);
        if (questionNumber3a.isChecked() || questionNumber3b.isChecked()) {
            checkQ4();
        } else {
            errorMessage();
        }
    }

    private void checkQ4() {
        RadioButton questionNumber4a = (RadioButton) findViewById(R.id.radio_button_4a);
        RadioButton questionNumber4b = (RadioButton) findViewById(R.id.radio_button_4b);
        RadioButton questionNumber4c = (RadioButton) findViewById(R.id.radio_button_4c);

        if (questionNumber4a.isChecked() || questionNumber4b.isChecked() || questionNumber4c.isChecked()) {
            checkQ5();
        } else {
            errorMessage();
        }
    }

    private void checkQ5() {
        RadioButton questionNumber5a = (RadioButton) findViewById(R.id.radio_button_5a);
        RadioButton questionNumber5b = (RadioButton) findViewById(R.id.radio_button_5b);

        if (questionNumber5a.isChecked() || questionNumber5b.isChecked()) {
            checkQ6();
        } else {
            errorMessage();
        }
    }

    private void checkQ6() {
        EditText answer6 = (EditText) findViewById(R.id.question6);
        String choiceInputText = answer6.getText().toString();
        if (choiceInputText.equals("Wild") || choiceInputText.equals("Calm")) {
            checkQ7();
        } else {
            errorMessage();
        }

    }

    private void checkQ7() {
        RadioButton questionNumber7a = (RadioButton) findViewById(R.id.radio_button_7a);
        RadioButton questionNumber7b = (RadioButton) findViewById(R.id.radio_button_7b);
        if (questionNumber7a.isChecked() || questionNumber7b.isChecked()) {
            checkQ8();
        } else {
            errorMessage();
        }

    }

    private void checkQ8() {
        CheckBox questionNumber8a = (CheckBox) findViewById(R.id.checkbox_8a);
        CheckBox questionNumber8b = (CheckBox) findViewById(R.id.checkbox_8b);
        CheckBox questionNumber8c = (CheckBox) findViewById(R.id.checkbox_8c);
        CheckBox questionNumber8d = (CheckBox) findViewById(R.id.checkbox_8d);
        CheckBox questionNumber8e = (CheckBox) findViewById(R.id.checkbox_8e);

        if (questionNumber8a.isChecked() || questionNumber8b.isChecked() || questionNumber8c.isChecked() || questionNumber8d.isChecked() || questionNumber8e.isChecked()) {
            scoreCalculation();
            displayResults(finalScore);
        } else {
            errorMessage2();
        }

    }


    // Method for reset
    public void resetQuiz(View view) {
        RadioGroup question1RadioGroup = (RadioGroup) findViewById(R.id.Question1_radio_group);
        question1RadioGroup.clearCheck();

        RadioGroup question2RadioGroup = (RadioGroup) findViewById(R.id.Question2_radio_group);
        question2RadioGroup.clearCheck();

        RadioGroup question3RadioGroup = (RadioGroup) findViewById(R.id.Question3_radio_group);
        question3RadioGroup.clearCheck();

        RadioGroup question4RadioGroup = (RadioGroup) findViewById(R.id.Question4_radio_group);
        question4RadioGroup.clearCheck();

        RadioGroup question5RadioGroup = (RadioGroup) findViewById(R.id.Question5_radio_group);
        question5RadioGroup.clearCheck();

        EditText questionNumber6EditText = (EditText) findViewById(R.id.question6);
        questionNumber6EditText.getText().clear();

        RadioGroup question7RadioGroup = (RadioGroup) findViewById(R.id.Question7_radio_group);
        question7RadioGroup.clearCheck();

        CheckBox questionNumber8a = (CheckBox) findViewById(R.id.checkbox_8a);
        if (questionNumber8a.isChecked()) {
            questionNumber8a.setChecked(false);
        }
        CheckBox questionNumber8b = (CheckBox) findViewById(R.id.checkbox_8b);
        if (questionNumber8b.isChecked()) {
            questionNumber8b.setChecked(false);
        }
        CheckBox questionNumber8c = (CheckBox) findViewById(R.id.checkbox_8c);
        if (questionNumber8c.isChecked()) {
            questionNumber8c.setChecked(false);
        }
        CheckBox questionNumber8d = (CheckBox) findViewById(R.id.checkbox_8d);
        if (questionNumber8d.isChecked()) {
            questionNumber8d.setChecked(false);
        }
        CheckBox questionNumber8e = (CheckBox) findViewById(R.id.checkbox_8e);
        if (questionNumber8e.isChecked()) {
            questionNumber8e.setChecked(false);
        }

        EditText userName = (EditText) findViewById(R.id.player_EditText);
        userName.getText().clear();

        // Move up
        ScrollView scrollView = (ScrollView) findViewById(R.id.topOfHeaven);
        scrollView.requestFocus();


        Toast.makeText(this, getString(R.string.resetDoneMessage), Toast.LENGTH_SHORT).show();

    }

}