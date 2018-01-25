package com.example.classy;

import java.util.LinkedList;

/**
 * Created by Nico on 12/1/17.
 */

/**
 * Represents a block, which can appear on multiple days
 */
public class Block {

    //Days are measured 1-7, 1 is Saturday, 7 is Sunday
    private int[] days;
    private String name;
    private boolean brk;
    private int duration;
    private String timeLeft;

    /**
     * The Block constructor
     * @param duration an int, the amount of time in the block. Measured in minutes
     * @param days an int[], full of the days the block takes place
     * @param name a String, the name of the block
     * @param brk a boolean, true if the block is a break, meaning that there is no 5 minutes passing period before it starts. False if not
     */
    public Block(int duration, int[] days, String name, boolean brk){
        //this.start = start;
        //this.end = end;
        this.days = days;
        //this.id = id;
        this.name = name;
        this.brk = brk;
        this.duration = duration;
    }

    /**
     * gets the days the block takes place
     * @return an int[] of the days the block takes place. Days are measured 1-7, 1 is Saturday, 7 is Sunday
     */
    public int[] getDays(){
        return days;
    }

    /**
     * gets whether the block is a break
     * @return a boolean, true if the block is a break, false if not
     */
    public boolean isBrk(){
        return brk;
    }

    /**
     * gets the duration of the block
     * @return an int wihch represents the length of the block, measures time in minutes.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * gets the name of the block
     * @return a String, the name of the block
     */
    public String getName(){
        return name;
    }

    /**
     * gets a copy of this block
     * @return a Block, with the same values as this block
     */
    public Block getCopy(){
        return new Block(duration, days, name, brk);
    }

    /**
     * Sets the time left in the block
     * @param time a String, represents time in a HOURS:MINUTES:SECONDS manner, e.g. 05:20:11
     */
    public void setTimeLeft(String time){
        timeLeft = time;
    }

    /**
     * gets the time left in the block
     * @return a String, represents time in a HOURS:MINUTES:SECONDS manner, e.g. 05:20:11
     */
    public String getTimeLeft(){
        return timeLeft;
    }

}
