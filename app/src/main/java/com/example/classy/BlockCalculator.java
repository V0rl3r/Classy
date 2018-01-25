package com.example.classy;

import java.util.LinkedList;

/**
 * Created by Nico on 12/1/17.
 */

/**
 * Performs all the calculations related to blocks, including storing the schedule
 */
public class BlockCalculator {

    LinkedList<Block> schedule;

    /**
     * Constructs the BlockCalculator
     * Currently the schedule is hardcoded below. To add to the schedule, add a new block to schedule. The order they are add
     * to the linked list is the order they will be worked through. E.G., if B is added before A for tuesday, when finding
     * the current block it will consider B first.
     */
    public BlockCalculator(){
        schedule = new LinkedList<Block>();

        schedule.add(new Block(500, new int[]{2, 3, 4, 5, 6}, "Out of School", true));

        //Normal Blocks
        //LinkedList<Block> monTue = new LinkedList<Block>();
        schedule.add(new Block(45, new int[]{2}, "A Block", false));
        schedule.add(new Block(45, new int[]{2, 3}, "B Block", false));
        schedule.add(new Block(15, new int[]{2}, "Morning Meeting", false));
        schedule.add(new Block(15, new int[]{3}, "Break", true));
        schedule.add(new Block(45, new int[]{2, 3}, "C Block", false));
        schedule.add(new Block(45, new int[]{2, 3}, "D Block", false));
        schedule.add(new Block(50, new int[]{2}, "Lunch", true));
        schedule.add(new Block(35, new int[]{3}, "Lunch", true));
        schedule.add(new Block(20, new int[]{3}, "Advising", true));
        schedule.add(new Block(45, new int[]{2, 3}, "E Block", false));
        schedule.add(new Block(45, new int[]{2, 3}, "F Block", false));
        schedule.add(new Block(45, new int[]{2, 3}, "G Block", false));
        schedule.add(new Block(45, new int[]{3}, "A Block", false));

        //Long Blocks
        //LinkedList<Block> wed = new LinkedList<Block>();
        schedule.add(new Block(90, new int[]{4}, "A Block", false));
        schedule.add(new Block(10, new int[]{4}, "Break", true));
        schedule.add(new Block(90, new int[]{4}, "B Block", false));
        schedule.add(new Block(30, new int[]{4}, "Lunch", true));
        schedule.add(new Block(90, new int[]{4}, "C Block", false));
        schedule.add(new Block(90, new int[]{4}, "D Block", false));


        //LinkedList<Block> thur = new LinkedList<Block>();
        schedule.add(new Block(90, new int[]{5}, "E Block", false));
        schedule.add(new Block(20, new int[]{5}, "Break", false));
        schedule.add(new Block(90, new int[]{5}, "F Block", false));
        schedule.add(new Block(45, new int[]{5}, "Lunch", true));
        schedule.add(new Block(90, new int[]{5}, "G Block", false));

        //H-Blocks
        schedule.add(new Block(90, new int[]{5}, "H-Block", false));


        //Friday Blocks
        schedule.add(new Block(40, new int[]{6}, "D Block", false));
        schedule.add(new Block(40, new int[]{6}, "E Block", false));
        schedule.add(new Block(15, new int[]{6}, "Break", true));
        schedule.add(new Block(40, new int[]{6}, "F Block", false));
        schedule.add(new Block(40, new int[]{6}, "Assembly", false));
        schedule.add(new Block(40, new int[]{6}, "Lunch", true));
        schedule.add(new Block(40, new int[]{6}, "G Block", false));
        schedule.add(new Block(40, new int[]{6}, "A Block", false));
        schedule.add(new Block(40, new int[]{6}, "B Block", false));
        schedule.add(new Block(40, new int[]{6}, "C Block", false));


        schedule.add(new Block(520, new int[]{2, 3, 4, 5, 6}, "Out of School", true));

    }

    /**
     * Finds the current block. The school day starts at 8:20, and time is counted in seconds
     * @param time is in Integer, and measures time in seconds
     * @param day is an Integer, and is the current day. 1 is Saturday, 7 is Sunday
     * @return a Block, the current Block
     */
    //The day starts at 8:20, and has a passing period first thing
    public Block currentBlock(Integer time, Integer day){
        Integer prevTime = 0;
        Integer curTime = 0;
        for(Block b : schedule){
            int[] days = b.getDays();

            if(prevTime <= time && time <= curTime) {

                Block ret = b.getCopy();
                findTimeLeft(b, time, prevTime);

                findTimeLeft(ret, time, prevTime);


                return ret;

            }

            for(int i = 0; i < days.length; i++){

                if(days[i] == day){
                    if(!b.isBrk()){
                        prevTime = curTime;
                        curTime += 5*60;

                        if(prevTime <= time && time <= curTime){
                            Block ret = new Block(5, new int[]{0, 1, 2, 3, 4}, "Passing Period", false);

                            findTimeLeft(ret, time, prevTime);

                            return ret;
                        }
                    }

                    prevTime = curTime;
                    curTime += b.getDuration()*60;

                    if(prevTime <= time && time <= curTime) {
                        Block ret = b.getCopy();

                        findTimeLeft(ret, time, prevTime);

                        return ret;
                    }

                    break;
                }

            }

        }
        return null;
    }

    //Finds the time left in the given block. begTime is the start time of the block
    private void findTimeLeft(Block block, Integer curTime, Integer begTime){
        int duration = block.getDuration()*60;
        duration -= curTime - begTime;
        String ret = "";

        int hours = duration/3600;
        if(hours < 10){
            ret += "0";
        }
        ret += hours;
        if(ret.length() < 2){
            ret += "0";
        }
        duration -= 3600*hours;

        ret += ":";

        int minutes = duration/60;
        if(minutes < 10){
            ret += "0";
        }
        ret += minutes;
        if(ret.length() < 5){
            ret += "0";
        }
        duration -= minutes*60;

        ret += ":";

        int seconds = duration;
        if(seconds < 10){
            ret+= "0";
        }
        ret += seconds;

        block.setTimeLeft(ret);
    }

    /**
     * Returns all the blocks in a day in a LinkedList
     * @param day an int that signifies the current day. 1 is Saturday, 7 is Sunday
     * @return a LinkedList of type Block, containing all the blocks in a given day
     */
    public LinkedList<Block> getBlocksInDay(int day){
        LinkedList<Block> blocks = new LinkedList<Block>();
        for(Block b : schedule){
            int[] days = b.getDays();
            for(int i = 0; i < days.length; i++){
                if(days[i] == day){
                    blocks.add(b);
                    break;
                }
            }
        }
        return blocks;
    }

}
