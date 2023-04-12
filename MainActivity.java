package com.example.morsecodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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
    private MediaPlayer dotmediaPlayer;
    private MediaPlayer dashmediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        dotmediaPlayer = MediaPlayer.create(this, R.raw.dot);
        dashmediaPlayer = MediaPlayer.create(this, R.raw.dash);

        UIString = "";
        threadTextView = (TextView) findViewById(R.id.outputString);
        isConverting = false;

        mHandler = new Handler(getApplicationContext().getMainLooper());
    }

    public void convertWork (View view) {
        if (view.getId() == R.id.startButton) {
            isConverting = true;
            UIString = "";
            threadTextView.setText(UIString);
            //TASK 3: CREATE A THREAD CONTAINING A RUNNABLE OBJECT
            Thread backgroundThread = new Thread(new Runnable() {

                //ABSTRACT METHOD RUN USED TO DO WORK ON THE THREAD
                @Override
                public void run() {
                    while (isConverting) {
                        try {
                            inputTextView = (TextView) findViewById(R.id.inputString);
                            String input = inputTextView.getText().toString();
                            MorseCode morse = new MorseCode(input);
                            ArrayList<String> morseList = MorseCode.convertedString();
                            UIString = "";
                            //NESTED FOR LOOP TO ITERATE THROUGH EACH ARRAY ELEMENT AND EACH CHARACTER IN THE ELEMENT
                            for (int i = 0; i < morseList.size(); i++){
                                if(!isConverting){
                                    break;
                                }
                                for (int j = 0; j < morseList.get(i).length(); j++){
                                    if(!isConverting){
                                        break;
                                    }
                                    UIString = UIString + Character.toString(morseList.get(i).charAt(j));
                                    if (morseList.get(i).charAt(j) == '-') {
                                        dashmediaPlayer.start();
                                    }
                                    else if (morseList.get(i).charAt(j) == '.'){
                                        dotmediaPlayer.start();
                                    }
                                    else {
                                        Thread.sleep(250);
                                    }
                                    //RUNNABLE OBJECT TO UPDATE UI
                                    Runnable uiRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            threadTextView.setText(UIString);
                                        }
                                    };
                                    //POST UI UPDATE TO MAIN THREAD WITH HANDLER
                                    mHandler.post(uiRunnable);
                                    Thread.sleep(600);
                                }
                                Thread.sleep(250);
                            }
                            isConverting = false;
                        } catch (Exception e) {
                            Log.i("ERROR", e.getMessage());
                        }
                    }
                }
            });

            //TASK 6: START THE BACKGROUND THREAD
            backgroundThread.start();
        } else {
            isConverting = false;
        }
    }
}