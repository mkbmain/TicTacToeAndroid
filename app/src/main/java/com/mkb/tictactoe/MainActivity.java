package com.mkb.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button[] Buttons;
    private static WinPattern[] WinPatterns;
    private boolean XTurn = true;
    private AlertDialog.Builder dlgAlert;

    private static GameOverDetails CheckWin(Button[] buttons) {
        GameOverDetails win = new GameOverDetails();
        for (WinPattern pattern : WinPatterns) {
            int[] pat = pattern.GetWinPattern();
            if (
                    buttons[pat[0] - 1].getText().toString().isEmpty() == false &&   // make sure its not empty
                            buttons[pat[0] - 1].getText().toString().equals(buttons[pat[1] - 1].getText().toString()) &&   // make sure both element 1 and 2 in array match
                            buttons[pat[1] - 1].getText().toString().equals(buttons[pat[2] - 1].getText().toString())) {  // and make sure both element 2 and 3 match
                win.GameOver = true;
                win.WinCharacter = buttons[pat[0] - 1].getText().toString();
                break;
            }
        }

        return win;
    }

    static {
        try {
            WinPatterns = new WinPattern[]{
                    new WinPattern(new int[]{1, 2, 3}),
                    new WinPattern(new int[]{4, 5, 6}),
                    new WinPattern(new int[]{7, 8, 9}),
                    new WinPattern(new int[]{1, 5, 9}),
                    new WinPattern(new int[]{3, 5, 7}),
                    new WinPattern(new int[]{1, 4, 7}),
                    new WinPattern(new int[]{2, 5, 8}),
                    new WinPattern(new int[]{3, 6, 9}),
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlgAlert = new AlertDialog.Builder(this);
        Buttons = new Button[]{
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
        };

        for (Button b : Buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String b = ((Button) v).getText().toString();

                    if (b.isEmpty()) {
                        ((Button) v).setText(XTurn ? "X" : "O");
                        XTurn = !XTurn;
                        GameOverDetails gameDetails = CheckWin(Buttons);
                        if (gameDetails.GameOver) {
                            dlgAlert.setMessage(String.format("Game Over. %s wins", gameDetails.WinCharacter));
                            dlgAlert.setTitle("Tic Tac toe");
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                            XTurn = true;
                            for (Button button : Buttons) {
                                button.setText("");
                            }
                        }
                    }
                }
            });
        }
    }
}

class GameOverDetails {
    public boolean GameOver;
    public String WinCharacter;
}

class WinPattern {

    private int[] Pattern;

    public WinPattern(int[] pattern) throws Exception {
        if (pattern.length != 3) {
            throw new Exception("Win Patten in Tic Tac Toe need to be 3 in length");
        }
        Pattern = pattern;
    }

    public int[] GetWinPattern() {
        return Pattern;
    }
}