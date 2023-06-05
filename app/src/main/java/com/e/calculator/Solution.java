package com.e.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Solution class επιλύει τις πράξεις και τροποποιεί το TvResult
 * Δεν έχει layout, χρησιμοποιείται μόνο από την MainActivity
 */
public class Solution
{
    /**
     * Επιλύση των πράξεων
     *
     * Σπάει το textview TvPrakseis σε array τύπου String το οποίο περιλαμβάνει κάθε χαρακτήρα εκτός παρενθέσεων
     * Καλεί την doOps και παίρνει το αποτέλεσμα το οποίο τυπώνει
     */
    public static void solve()
    {
        String eq = MainActivity.TvPrakseis.getText().toString();
        if (eq.isEmpty())
        {
            return;
        }
        // Αφαιρεί τις παρενθέσεις
        eq = eq.replaceAll("[()]", "");
        // Βάζει σε ένα αλφαριθμητικό array τους χαρακτήρες
        // ?=[] κοιτάει τους χαρακτήρες που έπονται
        // ?<=[] κοιτάει τους προηγούμενους χαρακτήρες
        String[] parts = eq.split("(?=[/*+-])|(?<=[/*+-])");

        // Αν η εξίσωση ξεκινάει με μείον το πρώτο στοιχείο είναι κενό ΣΕ ANDROID 9 MONO #NO_LOGIC
        // Το κάνουμε 0 αφού -12 == 0-12
        if (parts[0].equals(""))
        {
            parts[0] = "0";
        }
        // ΣΕ ANDROID 10 ΚΑΙ ΑΝΩ το πρώτο στοιχείο είναι μείον
        // Βάζουμε 0 στην αρχή  και κάνουμε shift τα στοιχεία δεξιά
        if (parts[0].equals("-"))
        {
            String[] tmp = new String[parts.length + 1];
            tmp[0] = "0";
            System.arraycopy(parts, 0, tmp, 1, parts.length);
            parts = tmp.clone();
        }
        // Για debug
//        System.out.println(Arrays.toString(parts));

        double result;
        if (endsWithOp(parts))
        {
            return;
        }
        else if (parts.length == 1)
        {
            result = Double.parseDouble(parts[0]);
        }
        else
        {
            result = doOps(parts);
        }
        // Για debug
//        System.out.println(result);

        displaySolution(String.valueOf(result));
    }

    /**
     * Δέχεται ένα array τύπου String το οποίο περιέχει αριθμούς και operators
     * Το μετατρέπει σε arraylist τύπου String και κάνει τις πράξεις
     * Πρώτο σκαν αριστερά προς τα δεξιά πολλαπλασιασμοί και διαιρέσεις
     * Δεύτερο σκαν (αριστερά προς τα δεξιά πάλι) προσθέσεις και αφαιρέσεις
     * @param parts: το array τύπου String
     * @return το αποτέλεσμα των πράξεων double ακρίβειας
     */
    private static double doOps(String[] parts)
    {
        ArrayList<String> arParts = new ArrayList<>(Arrays.asList(parts));

        // Αν το μείον προηγείται από άλλο σύμβολο το συγχωνεύει με τον επόμενο αριθμό
        // πχ [12, *, -, 9] ->  [12, *, -9.0]
        int i = 0;
        while (i < arParts.size())
        {
            String e = arParts.get(i);
            boolean isOp = e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/");
            if (isOp)
            {
                if (arParts.get(i + 1).equals("-"))
                {
                    arParts.set(i + 1, String.valueOf(Double.parseDouble(arParts.get(i + 2)) * (-1)));
                    arParts.remove(i + 2);
                }
            }
            i++;
        }
        // Debug
//        System.out.println(arParts);

        // * /
        i = 1;
        while (i < arParts.size())
        {
            double num1 = Double.parseDouble(arParts.get(i - 1));
            String op = arParts.get(i);
            double num2 = Double.parseDouble(arParts.get(i + 1));
            switch (op)
            {
                case "*" :
                    arParts.set(i - 1, String.valueOf(num1 * num2));
                    arParts.remove(i);
                    arParts.remove(i);
                    break;
                case "/" :
                    arParts.set(i - 1, String.valueOf(num1 / num2));
                    arParts.remove(i);
                    arParts.remove(i);
                    break;
                case "+":
                case "-":
                    i+=2;
                    break;
            }
        }

        // + -
        i = 1;
        while (i < arParts.size())
        {
            double num1 = Double.parseDouble(arParts.get(i - 1));
            String op = arParts.get(i);
            double num2 = Double.parseDouble(arParts.get(i + 1));
            switch (op)
            {
                case "+":
                    arParts.set(i - 1, String.valueOf(num1 + num2));
                    arParts.remove(i);
                    arParts.remove(i);
                    break;
                case "-":
                    arParts.set(i - 1, String.valueOf(num1 - num2));
                    arParts.remove(i);
                    arParts.remove(i);
                    break;
            }
        }
        // Debug
//        System.out.println(arParts);

        return Double.parseDouble(arParts.get(0));
    }

    /**
     * Δέχεται ένα array τύπου String και ελέγχει αν το τελευταίο στοιχείο είναι σύμβολο +, -, *, /
     * @param parts: array τύπου String
     * @return true αν ναι, false αλλιώς
     */
    private static boolean endsWithOp(String[] parts)
    {
        return parts[parts.length - 1].equals("+") || parts[parts.length - 1].equals("-")
                || parts[parts.length - 1].equals("*") || parts[parts.length - 1].equals("/");
    }

    /**
     * Τροποποιεί το TvResult
     * @param res: το αποτέλεσμα
     */
    private static void displaySolution(String res)
    {
        // BigDecimal για να εξαλειφθεί το E notation
        try
        {
            MainActivity.TvResult.setText(new BigDecimal(res).toPlainString());
        }
        // Περιπτώσεις 0/0 -> NaN και n/0 -> +-Infinity
        catch(NumberFormatException e)
        {
            MainActivity.TvResult.setText(res);
        }
    }
}
