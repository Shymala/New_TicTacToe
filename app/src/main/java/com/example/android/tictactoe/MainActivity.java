package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private int[] TTTBoard = new int[9];
    private boolean userWon = false;
    private boolean systemWon = false;
    private boolean gameDraw = false;
    TextView mstatus_tv;
    Button mYes_button;
    Button mNo_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.ttt_gridView);
        gridview.setAdapter(new ImageAdapter(this));

        mstatus_tv = (TextView) findViewById(R.id.status_tv);
        mYes_button = (Button) findViewById(R.id.yes_button);
        mNo_button = (Button) findViewById(R.id.no_button);

        // Initialize all the elements in TTTBoard to -1
        initBoard();

        //Listener for the grid item click
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int new_pos = 0;
                ImageView img;
                ImageView userImg;
                boolean gameStatus = false;

                if (TTTBoard[position] == -1) {
                    // Mark the user clicked position in the TTTBoard
                    markUserSelection(position);

                    userImg = (ImageView) v;
                    userImg.setImageResource(R.drawable.sun_symbol);
                    //gerResult sets the systemWon , userWon and gameDraw global variables
                    getResult();

                    //Display result to the user returns true if anyone Win
                    gameStatus = displayResult();

                    if (!gameStatus) {
                        // get the next available free slot for system move
                        new_pos = getFreeSlot();

                        // If there is free space then make a move
                        if (new_pos != 100) {

                            markSystemSelection(new_pos);
                            img = (ImageView) parent.getChildAt(new_pos);
                            img.setImageResource(R.drawable.moon_symbol);

                            getResult();

                            gameStatus = displayResult();

                        }

                    }
                } else //If user clicks the already clicked item then display the below message

                {
                    Toast.makeText(MainActivity.this, "" + "Please try empty location",
                            Toast.LENGTH_SHORT).show();

                }

            }

        });
    }

    // Initializes TTTBoard global array
    private void initBoard() {
        for (int i = 0; i < TTTBoard.length; i++) {
            TTTBoard[i] = -1;
        }
    }

    //When the user clicks an item in the grid this function marks the corresponding location in the TTBoard array
    private void markUserSelection(int pos) {

        TTTBoard[pos] = 0;

    }

    //When the system selects an item in the grid this function marks the corresponding location in the TTBoard array
    private void markSystemSelection(int pos) {

        TTTBoard[pos] = 1;

    }

    // Returns the next random free space available for system move
    private int getFreeSlot() {
        int position = 100;

        if (!checkIfGameOver()) {
            Random rand = new Random();
            position = rand.nextInt(9);
            while (TTTBoard[position] != -1) {
                position = rand.nextInt(9);
            }

        }

        return position;

    }

    //Checks if all the items in the grid are selected.
    //Returns true if all the items are selected else returns false.
    private boolean checkIfGameOver() {
        boolean status = true;
        for (int i = 0; i < TTTBoard.length; i++) {
            if (TTTBoard[i] == -1) {
                status = false;
                break;
            }
        }
        return status;
    }

    //Checks whether all the three items in a row or column or diagonal match.
    // Depending on the value present in the particular location of the TTBoard  either userWon
    // or systemWon or gameDraw variables are set
    private void getResult() {

        if (TTTBoard[0] == TTTBoard[1] && TTTBoard[1] == TTTBoard[2] && TTTBoard[2] == TTTBoard[0] && TTTBoard[0] != -1) {
            if (TTTBoard[0] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[3] == TTTBoard[4] && TTTBoard[4] == TTTBoard[5] && TTTBoard[3] == TTTBoard[5] && TTTBoard[3] != -1) {
            if (TTTBoard[3] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[6] == TTTBoard[7] && TTTBoard[7] == TTTBoard[8] && TTTBoard[6] == TTTBoard[8] && TTTBoard[6] != -1) {
            if (TTTBoard[6] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[0] == TTTBoard[3] && TTTBoard[3] == TTTBoard[6] && TTTBoard[0] == TTTBoard[6] && TTTBoard[0] != -1) {
            if (TTTBoard[0] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[1] == TTTBoard[4] && TTTBoard[4] == TTTBoard[7] && TTTBoard[1] == TTTBoard[7] && TTTBoard[1] != -1) {
            if (TTTBoard[1] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[2] == TTTBoard[5] && TTTBoard[5] == TTTBoard[8] && TTTBoard[2] == TTTBoard[8] && TTTBoard[2] != -1) {
            if (TTTBoard[2] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[0] == TTTBoard[4] && TTTBoard[4] == TTTBoard[8] && TTTBoard[8] == TTTBoard[0] && TTTBoard[0] != -1) {
            if (TTTBoard[0] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (TTTBoard[2] == TTTBoard[4] && TTTBoard[6] == TTTBoard[4] && TTTBoard[2] == TTTBoard[6] && TTTBoard[2] != -1) {
            if (TTTBoard[2] == 0) {
                userWon = true;
            } else
                systemWon = true;

        } else if (checkIfGameOver() && (!userWon || !systemWon)) {

            gameDraw = true;
        }
    }


    //If the user wants to continue the game and if he clicks YES button then this function is called .
    //It resets all the global variables as well as the grid.
    private void resetGame() {
        initBoard();
        systemWon = false;
        userWon = false;
        gameDraw = false;
        gridview.setAdapter(new ImageAdapter(this));

    }


    // Displays result to user.
    private boolean displayResult() {

        boolean gameStatus = false;

        if (userWon) {

            makeVisible();
            mstatus_tv.setText("Congratulations!! You Won \n Do you want to continue");
           gameStatus = true;

        } else if (systemWon) {

           makeVisible();
            mstatus_tv.setText("Sorry, You Lost \n Do you want to continue");
            gameStatus = true;

        } else if (gameDraw) {
            makeVisible();
            mstatus_tv.setText("Game Draw \n Do you want to continue");
            gameStatus = true;
        }
        return gameStatus;
    }

    private void makeVisible() {

        mstatus_tv.setVisibility(View.VISIBLE);
        mYes_button.setVisibility(View.VISIBLE);
        mNo_button.setVisibility(View.VISIBLE);

    }

    private void makeInvisible() {
        mstatus_tv.setVisibility(View.INVISIBLE);
        mYes_button.setVisibility(View.INVISIBLE);
        mNo_button.setVisibility(View.INVISIBLE);

    }

    // Called when the YES button is clicked.
    public void continueGame(View v) {
        resetGame();
        makeInvisible();


    }

    // Called when the NO button is clicked.
    public void exitGame(View v) {
        resetGame();
        makeInvisible();
        finish();

    }

}
