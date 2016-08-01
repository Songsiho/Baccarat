package com.example.songsiho.baccarat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int capital, bet, base, win, winnings = 0;
    private boolean result = false;
    private boolean end = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baccaratAlgorithm();
    }

    public void baccaratAlgorithm(){
        //capital 입력받기
        capitalMessagebox();

        //betting 돌리기
        betting();

        //win
        if(isResult()){
            winAlgorithm();
        }
        //lose
        else {
            loseAlgorithm();
        }
    }

    public void loseAlgorithm(){
        setBet(getBet()*2);

        if(getBet()>=256*getBase()){
            //All-in
            finalbet();
        }
    }

    public void winAlgorithm(){
        setWin(getWin()+1);
        setBet(getBase());
        setResult(false);

        if(getWin()==5){
            fivebetting();
            //2배 이상 땄을 경우
            if(getCapital()>=1000*getBase()){
                setEnd(false);
            }
        }
        setWin(0);
    }

    public void finalbet(){
        displayTextBet(00000);
        Toast.makeText(MainActivity.this, "All-in!, good luck", Toast.LENGTH_SHORT).show();
        Button btnwin = (Button)findViewById(R.id.button_win);
        btnwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBet(getBase());
                setResult(false);
            }
        });

        Button btnlose = (Button)findViewById(R.id.button_lose);
        btnlose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Go home!", Toast.LENGTH_SHORT).show();
                setEnd(false);
            }
        });
    }

    //5번째 이겼을때
    public void fivebetting(){
        displayTextBet(getBase()*5);
        Button btnwin = (Button)findViewById(R.id.button_win);
        btnwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCapital(getCapital()+(10*getBase()));
                setWinnings(getWinnings()+(10*getBase()));
                displayTextCapital(getCapital());
                displayTextWinnings(getWinnings());
                setResult(false);
            }
        });

        Button btnlose = (Button)findViewById(R.id.button_lose);
        btnlose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void betting(){
        displayTextBet(getBet());
        Button btnwin = (Button)findViewById(R.id.button_win);
        btnwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이겼을때
                setResult(true);
            }
        });

        Button btnlose = (Button)findViewById(R.id.button_lose);
        btnlose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //졌을때
            }
        });
    }

    public void capitalMessagebox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("How much money do you have?").setTitle("RICH");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                setCapital(Integer.parseInt(input.getText().toString()));
                setBase(getCapital() / 500);
                setBet(getBase());
                displayTextCapital(getCapital());
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    //Display Capital
    public void displayTextCapital(int capital) {
        TextView capitalView = (TextView) findViewById(R.id.textview_capital);
        capitalView.setText(String.valueOf(capital));
    }

    //Display Winnings
    public void displayTextWinnings(int winnings) {
        TextView winningsView = (TextView) findViewById(R.id.textview_winnings);
        winningsView.setText(String.valueOf(winnings));
    }

    //Display Bet
    public void displayTextBet(int bet) {
        TextView winningsView = (TextView) findViewById(R.id.textview_bet);
        winningsView.setText(String.valueOf(bet));
    }

    public int getCapital() {return capital;}

    public void setCapital(int capital) {this.capital = capital;}

    public int getBet() {return bet;}

    public void setBet(int bet) {this.bet = bet;}

    public int getBase() {return base;}

    public void setBase(int base) {this.base = base;}

    public int getWin() {return win;}

    public void setWin(int win) {this.win = win;}

    public boolean isResult() {return result;}

    public void setResult(boolean result) {this.result = result;}

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }
}
