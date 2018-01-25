package com.example.classy;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Nico on 1/4/18.
 */

/**
 * BlockButton is an extension of Button, solely created so that a block can be assosciated with said button
 */
public class BlockButton extends AppCompatButton {

    private Block block;

    /**
     * The constructor for a BlockButton
     * @param context is the Context for the button
     * @param b is the Block to be associated with the button
     */
    public BlockButton(Context context, Block b) {
        super(context);
        block = b;
    }

    /**
     * returns the block associated with button
     * @return a Block
     */
    public Block getBlock(){
        return block;
    }

}
