package com.smartdroidesign.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";

    // Lifecycle variables
    private static final String TAG = "MainActivity";
    private final String TEXT_CONTENTS = "TextContent";
    private final String STATE_PENDING_OPERATION = "PendingOperation";
    private final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonNeg = findViewById(R.id.btnNeg);
        Button buttonCanc = findViewById(R.id.btnCanc);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(((Button) v).getText().toString());

            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        // Neg button functionality
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if (value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        // Catch and clear if newNumber is either a "." or a "-"
                        newNumber.setText("");
                    }
                }
            }
        });
        Log.d(TAG, "onCreate: out");

        // Button clear all out functionality
        buttonCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if(value.length() !=0 || operand1 != null){
                    operand1 = null;
                    newNumber.setText("");
                    result.setText("");
                    displayOperation.setText("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: in");
        super.onStart();
        Log.d(TAG, "onStart: out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        displayOperation.setText(savedInstanceState.getString(TEXT_CONTENTS));
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(TEXT_CONTENTS, displayOperation.getText().toString());
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    private void performOperation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }
}
