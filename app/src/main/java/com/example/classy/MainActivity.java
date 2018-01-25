package com.example.classy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Runs the overall program
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView timeLeft;
    private BlockCalculator  bc;
    private Timer timer;
    private LinearLayout schedule;
    private int curDay;
    private ArrayDeque<Integer> colors;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * Called whenever one of the navigation buttons are pressed. Manages changing what is displayed on the screen
         * @param item a MenuItem, the navigation buttons that was pressed
         * @return a boolean, true if item successfully maps to an Id and the screen changes, or false if no matching Id was found
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setVisibility(View.VISIBLE);
                    timeLeft.setVisibility(View.VISIBLE);
                    schedule.setVisibility(View.INVISIBLE);
                    startTimer();
                    return true;
                case R.id.navigation_dashboard:
                    populateDaySchedule();
                    mTextMessage.setVisibility(View.GONE);
                    timeLeft.setVisibility(View.GONE);
                    schedule.setVisibility(View.VISIBLE);
                    timer.cancel();
                    return true;
                case R.id.navigation_notifications:
                    populateWeekSchedule();
                    mTextMessage.setVisibility(View.GONE);
                    timeLeft.setVisibility(View.GONE);
                    schedule.setVisibility(View.VISIBLE);
                    timer.cancel();
                    return true;
            }
            return false;
        }

    };

    /**
     * Runs the immediately once the app is started. Handles all the initial setup work, like a Main method
     * @param savedInstanceState a Bundle, the data from the database
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        timeLeft = (TextView) findViewById(R.id.timeLeft);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        colors = new ArrayDeque<Integer>();
        colors.addLast(Color.CYAN);
        colors.addLast(Color.GREEN);
        colors.addLast(Color.MAGENTA);
        colors.addLast(Color.YELLOW);
        colors.addLast(Color.RED);

        bc = new BlockCalculator();
        schedule = (LinearLayout) findViewById(R.id.schedule);
        schedule.setVisibility(View.INVISIBLE);
        startTimer();
    }

    //Fills the LinearLayout schedule with the Blocks for the week
    private void populateWeekSchedule(){
        schedule.removeAllViews();
        for(int i = 2; i < 7; i++){
            LinearLayout day = new LinearLayout(this);
            day.setOrientation(LinearLayout.VERTICAL);
            LinkedList<Block> blocks = bc.getBlocksInDay(i);
            for(Block b : blocks){
                BlockButton button = new BlockButton(this, b);
                button.setText(b.getName());
                int color = colors.remove();
                button.setBackgroundColor(color);
                colors.add(color);
                button.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Block block = ((BlockButton) v).getBlock();
                        Toast.makeText(getApplicationContext(), block.getName() + " " + block.getDuration() + " minutes long", Toast.LENGTH_LONG).show();
                    }
                });
                day.addView(button);
            }
            schedule.addView(day);
        }
    }

    //Fills the LinearLayout schedule with curDay's Blocks
    private void populateDaySchedule(){
        schedule.removeAllViews();

        LinearLayout day = new LinearLayout(this);
        day.setOrientation(LinearLayout.VERTICAL);
        LinkedList<Block> blocks = bc.getBlocksInDay(curDay);

        for(Block b : blocks){
            BlockButton button = new BlockButton(this, b);
            button.setText(b.getName());
            int color = colors.remove();
            button.setBackgroundColor(color);
            colors.add(color);
            button.setOnClickListener(new View.OnClickListener(){
                /**
                 * Runs any time one of the schedule BlockButtons are clicked. Causes the appropriate info about the Block associated with
                 * the BlockButton to appear
                 * @param v a View, the the BlockButton that was clicked
                 */
                public void onClick(View v){
                    Block block = ((BlockButton) v).getBlock();
                    Toast.makeText(getApplicationContext(), block.getName() + " " + block.getDuration() + " minutes long", Toast.LENGTH_LONG).show();

                }
            });
            day.addView(button);
        }
        schedule.addView(day);

    }

    //Starts the block and time remaining update timer
    private void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                begin();
            }
        }, 0, 1000);
    }

    //Updates the time remaining and current block
    private void begin(){

        Block curBlock = bc.currentBlock(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60*60+Calendar.getInstance().get(Calendar.MINUTE)*60+Calendar.getInstance().get(Calendar.SECOND), Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        curDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        if(curBlock != null) {
            mTextMessage.setText(curBlock.getName());
            timeLeft.setText(curBlock.getTimeLeft());
        } else {
            mTextMessage.setText("Error in finding block");
        }
    }

}
