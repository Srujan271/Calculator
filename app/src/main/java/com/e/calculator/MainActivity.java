package com.e.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main class υλοποιεί τις βασικές λειτουργίες
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    /**
     * Ανάθεση κουμπιών και textview
     */
    private Button Bt0, Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9;
    private Button BtPlus, BtMinus, BtMult, BtDiv, BtChSign, BtDec, BtEq;
    private Button BtCE, BtC;

    public static TextView TvPrakseis, TvResult;
    private TextView TvHistory;

    /**
     * Αρχικοποίηση οθόνης calculator
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
        setListeners();
        try
        {
            playMusic();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Εκκίνηση αναπαραγωγής μουσικής
     */
    private void playMusic()
    {
        try
        {
            MediaPlayerClass.initPlayer(this.getApplicationContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * onStop: σταματάει την αναπαραγωγή μουσικής όταν η εφαρμογή πάει στο background
     */
    @Override
    protected void onStop()
    {
        super.onStop();
        MediaPlayerClass.PausePlayer();
    }

    /**
     * onRestart: συνεχίζει την αναπαραγωγή μουσικής όταν η εφαρμογή ξαναβρεθεί στο foreground
     */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        MediaPlayerClass.ResumePlayer();
    }

    /**
     * onDestroy: τερματίζει την εφαρμογή και σταματάει την αναπαραγωγή μουσικής
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        MediaPlayerClass.ReleasePlayer();
    }

    /**
     * Πραγματοποιεί ενέργειες στο πάτημα των κουμπιών
     * @param v: view
     */
    public void onClick(View v)
    {
        if (v == Bt0)
        {
            updateToCalc("0");
            Solution.solve();
        }
        if (v == Bt1)
        {
            updateToCalc("1");
            Solution.solve();
        }
        if (v == Bt2)
        {
            updateToCalc("2");
            Solution.solve();
        }
        if (v == Bt3)
        {
            updateToCalc("3");
            Solution.solve();
        }
        if (v == Bt4)
        {
            updateToCalc("4");
            Solution.solve();
        }
        if (v == Bt5)
        {
            updateToCalc("5");
            Solution.solve();
        }
        if (v == Bt6)
        {
            updateToCalc("6");
            Solution.solve();
        }
        if (v == Bt7)
        {
            updateToCalc("7");
            Solution.solve();
        }
        if (v == Bt8)
        {
            updateToCalc("8");
            Solution.solve();
        }
        if (v == Bt9)
        {
            updateToCalc("9");
            Solution.solve();
        }

        if (v == BtPlus)
        {
            updateToCalc("+");
        }
        if (v == BtMinus)
        {
            updateToCalc("-");
        }
        if (v == BtMult)
        {
            updateToCalc("*");
        }
        if (v == BtDiv)
        {
            updateToCalc("/");
        }
        if (v == BtDec)
        {
            addDotToCalc();
            Solution.solve();
        }

        if (v == BtEq)
        {
            Solution.solve();
            switchViews();
        }
        if (v == BtChSign)
        {
            changeSign();
            Solution.solve();
        }

        if (v == BtC)
        {
            clearAll();
        }
        if (v == BtCE)
        {
            clearLastEntry();
            Solution.solve();
        }
    }

    /**
     * Εναλλάσσει τα views δλδ
     * TvPrakseis -> TvHistory
     * TvResult -> TvPrakseis
     */
    private void switchViews()
    {
        if ((!TvResult.getText().toString().endsWith("Infinity")) && (!TvResult.getText().toString().equals("NaN")))
        {
            if (!lastEntrySign())
            {
                TvHistory.setText(TvPrakseis.getText().toString());
                TvPrakseis.setText(TvResult.getText().toString());
            }
        }
    }
    /**
     * Προσθήκη δεκαδικής τελείας "."
     * Κάνει τους απαραίτητους ελέγχους
     * Αν προηγουμένως δεν υπάρχει αριθμός, γράφει "0."
     */
    private void addDotToCalc()
    {
        String input = TvPrakseis.getText().toString();

        if (lastEntrySign() || input.equals(""))
        {
            updateToCalc("0.");
        }
        else if (input.endsWith(".") || alreadyDecimal())
        {
            return;
        }
        else
        {
            updateToCalc(".");
        }
    }

    /**
     * Ελέγχει αν ο τελευταίος αριθμός είναι ήδη δεκαδικός
     * @return true αν είναι, false αλλιώς
     */
    private boolean alreadyDecimal()
    {
        String input = TvPrakseis.getText().toString();
        int indexOfSmb = lastIndexOfSymbol();

        if (indexOfSmb != -1)
        {
            String lastNum =  input.substring(indexOfSmb + 1);
            if (lastNum.contains("."))
            {
                return true;
            }
        }
        else
        {
            if (input.contains("."))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Ελέγχει αν ο τελευταίος χαρακτήρας είναι σύμβολο + = * /
     * @return true αν ναι, false αλλιώς
     */
    private boolean lastEntrySign()
    {
        String[] symbols = {"+","-","*","/"};
        for (String symbol : symbols)
        {
            if (TvPrakseis.getText().toString().endsWith(symbol))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Ελέγχει αν ο τελευταίος χαρακτήρας είναι παρένθεση )
     * @return true αν είναι, false αλλιώς
     */
    private boolean lastEntryParenth()
    {
        if (TvPrakseis.getText().toString().endsWith(")"))
        {
            return true;
        }
        return false;
    }

    /**
     * Ανανεώνει το textview με τα προς εκτέλεση στοιχεία
     * * @param strToAdd
     */
    private void updateToCalc(String strToAdd)
    {
        String oldStr = TvPrakseis.getText().toString();
        String newStr;
        final boolean smbToAdd = (strToAdd.equals("+")) || (strToAdd.equals("-")) || (strToAdd.equals("*")) || (strToAdd.equals("/"));

        if ((lastEntryParenth() && !smbToAdd) || (smbToAdd && oldStr.equals("")))
        {
            return;
        }

        if (lastEntrySign())
        {
            if (smbToAdd)
            {
                newStr = oldStr.substring(0, oldStr.length() - 1) + strToAdd;
            }
            else
            {
                newStr = oldStr + strToAdd;
            }
        }
        else
        {
            newStr = oldStr + strToAdd;
        }
        TvPrakseis.setText(newStr);
    }

    /**
     * Μετατρέπει τον τελευταίο αριθμό από θετικό σε αρνητικό και αντιστρόφως
     */
    private void changeSign()
    {
        if (lastEntrySign() || TvPrakseis.getText().toString().equals(""))
        {
            return;
        }
        String input  = TvPrakseis.getText().toString();
        String chInput;

        // Αν δεν τελειώνει με παρένθεση είναι θετικός πχ ...13
        // Θα μετατραπεί σε αρνητικό
        if (!input.endsWith(")"))
        {
            int startingPoint = lastIndexOfSymbol();

            if (startingPoint != 0)
            {
                chInput = input.substring(0, startingPoint + 1);

                chInput += "(-" + input.substring(startingPoint + 1);
                chInput += ")";
            }
            // Αν στην οθόνη υπάρχει μόνο ένας αριθμός και τίποτα άλλο
            else
            {
                chInput = "(-" + input + ")";
            }
        }
        // Αν τελειώνει με παρένθεση είναι αρνητικός  πχ ...(-96)
        // Θα μετατραπεί σε θετικό
        else
        {
            int startingPoint = input.lastIndexOf("(");
            int endingPoint = input.lastIndexOf(")");
            chInput = input.substring(0, startingPoint);
            chInput += input.substring(startingPoint + 2, endingPoint);
        }

        TvPrakseis.setText(chInput);
    }

    /**
     * @return τη θέση του τελευταίου συμβόλου του textview TvPrakseis
     */
    private int lastIndexOfSymbol()
    {
        String input  = TvPrakseis.getText().toString();
        String[] symbols = {"+","-","*","/"};
        int lastIndex = 0;
        for (String symbol : symbols)
        {
            if (lastIndex < input.lastIndexOf(symbol))
            {
                lastIndex = input.lastIndexOf(symbol);
            }
        }
        return lastIndex;
    }

    /**
     * Καλείται όταν πατηθεί το κουμπί C
     * Διαγράφει τα πάντα
     */
    private void clearAll()
    {
        TvHistory.setText("");
        TvPrakseis.setText("");
        TvResult.setText("0");
    }
    /**
     * Καλείται όταν πατηθεί το κουμπί CE
     * Διαγράφει τον τελευταίο χαρακτήρα (είτε ψηφίο είτε σύμβολο)
     */
    private void clearLastEntry()
    {
        if (TvPrakseis.getText().toString().equals(""))
        {
            return;
        }
        else if (TvPrakseis.getText().toString().equals("Infinity") || TvPrakseis.getText().toString().equals("-Infinity"))
        {
            clearAll();
            return;
        }
        String oldStr = TvPrakseis.getText().toString();
        String newStr;

        if (lastEntryParenth())
        {
            int openParenth = oldStr.lastIndexOf("(");
            String justTheNum = oldStr.substring(openParenth + 2, oldStr.length() - 1);
            newStr = oldStr.substring(0, openParenth) + justTheNum;
        }
        else
        {
            newStr = oldStr.substring(0, oldStr.length() - 1);
        }

        TvPrakseis.setText(newStr);
        if (newStr.isEmpty())
        {
            TvResult.setText("0");
        }
    }

    /**
     * Σύνδεση των κουμπιών και των textview του κώδικα με τα αντίστοιχα του layout
     */
    private void initializeButtons()
    {
        Bt0 = findViewById(R.id.Bt0);
        Bt1 = findViewById(R.id.Bt1);
        Bt2 = findViewById(R.id.Bt2);
        Bt3 = findViewById(R.id.Bt3);
        Bt4 = findViewById(R.id.Bt4);
        Bt5 = findViewById(R.id.Bt5);
        Bt6 = findViewById(R.id.Bt6);
        Bt7 = findViewById(R.id.Bt7);
        Bt8 = findViewById(R.id.Bt8);
        Bt9 = findViewById(R.id.Bt9);
        BtPlus = findViewById(R.id.BtProsthesh);
        BtMinus = findViewById(R.id.BtAfairesh);
        BtMult = findViewById(R.id.BtPollaplasiasmos);
        BtDiv = findViewById(R.id.BtDiairesh);
        BtChSign = findViewById(R.id.BtSynPlin);
        BtDec = findViewById(R.id.BtTelia);
        BtEq = findViewById(R.id.BtEqual);
        BtC = findViewById(R.id.BtC);
        BtCE = findViewById(R.id.BtCE);

        TvPrakseis = findViewById(R.id.TvPrakseis);
        TvResult = findViewById(R.id.TvResult);
        TvHistory = findViewById(R.id.TvHistory);
    }

    /**
     * set listeners στα buttons
     */
    private void setListeners()
    {
        Bt0.setOnClickListener(this);
        Bt1.setOnClickListener(this);
        Bt2.setOnClickListener(this);
        Bt3.setOnClickListener(this);
        Bt4.setOnClickListener(this);
        Bt5.setOnClickListener(this);
        Bt6.setOnClickListener(this);
        Bt7.setOnClickListener(this);
        Bt8.setOnClickListener(this);
        Bt9.setOnClickListener(this);
        BtPlus.setOnClickListener(this);
        BtMinus.setOnClickListener(this);
        BtMult.setOnClickListener(this);
        BtDiv.setOnClickListener(this);
        BtChSign.setOnClickListener(this);
        BtDec.setOnClickListener(this);
        BtEq.setOnClickListener(this);
        BtC.setOnClickListener(this);
        BtCE.setOnClickListener(this);
    }
}