package com.example.morsecodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Boolean isConverting;
    public Handler mHandler;
    private TextView threadTextView;
    private TextView inputTextView;
    private String UIString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        UIString = "";
        threadTextView = (TextView) findViewById(R.id.outputString);
        isConverting = false;
//
        //TASK 3: INSTANTIATE THE HANDLER AND PROVIDE A REFERENCE TO THE
        //        MESSAGE QUEUE FOR THE UI THREAD (the main looper)
        mHandler = new Handler(getApplicationContext().getMainLooper());
    }

    public void convertWork (View view) {
        if (view.getId() == R.id.startButton) {
            isConverting = true;
            //TASK 3: CREATE A THREAD CONTAINING A RUNNABLE OBJEC
            Thread backgroundThread = new Thread(new Runnable() {

                //TASK 4: WHAT WORK DOES THE THREAD DO?
                //        IMPLEMENT THE ABSTRACT METHOD run() TO PERFORM THE WORK.
                @Override
                public void run() {
                    while (isConverting) {
                        try {
                            inputTextView = (TextView) findViewById(R.id.inputString);
                            String input = inputTextView.getText().toString();
                            MorseCode morse = new MorseCode(input);
                            ArrayList<String> morseList = MorseCode.convertedString();
                            UIString = "";
                            for (int i = 0; i < morseList.size(); i++){
                                for (int j = 0; j < morseList.get(i).length(); j++){
                                    UIString += morseList.get(i).charAt(j);
                                }
                                Thread.sleep(1000);
                            }
                        } catch (Exception e) {
                            Log.i("ERROR", e.getMessage());
                        }
                        //TASK 5: USE THE HANDLER -
                        //            a. CONSTRUCT A RUNNABLE OBJECT
                        //            b. POST A RUNNABLE TASK TO MESSAGEQUEUE
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                threadTextView.setText(UIString);
                            }
                        });
                    }
                }
            });

            //TASK 6: START THE BACKGROUND THREAD
            backgroundThread.start();
        } else {
            isConverting = false;
        }
        inputTextView = (TextView) findViewById(R.id.inputString);
        String input = inputTextView.getText().toString();
        MorseCode morse = new MorseCode(input);
        ArrayList<String> morseList = MorseCode.convertedString();
        // threadTextView.setText(morseList.toString());
        Log.i("text", morseList.toString());

    }
}