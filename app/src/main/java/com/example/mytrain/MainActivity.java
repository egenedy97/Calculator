package com.example.mytrain;

import androidx.appcompat.app.AppCompatActivity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Stack<EquationPart> stack;
    TextView tvEquation;
    TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        stack = new Stack<>();
        stack.push(new Number());
        tvEquation = findViewById(R.id.tv_equation);
        tvResults = findViewById(R.id.tv_result);
    }

    private Number getCurrentNumber() {
        Number n;
        if (stack.size() > 0 && stack.lastElement() instanceof Number)
            n = (Number)stack.lastElement();
        else {
            n = new Number();
            stack.push(n);
        }
        return n;
    }

    private void displayNumbers() {
        if (stack.isEmpty()) {
            tvResults.setText("");
            tvEquation.setText("");
            return;
        }

        if (stack.lastElement() instanceof Number && ((Number)stack.lastElement()).isEmpty()) {
            stack.pop();
        }

        if (stack.isEmpty()) {
            tvResults.setText("");
            tvEquation.setText("");
            return;
        }

        String str = "";
        Stack<EquationPart> multiplicationStack = new Stack<>();
        for (EquationPart part : stack) {
            str += part.toString();

            EquationPart lastElement = multiplicationStack.size()>0? multiplicationStack.lastElement():null;
            if (part instanceof Number && ((Number) part).isReady() && lastElement instanceof Operator && ((Operator) lastElement).isMultiplyOrDivision()) {
                Operator divisionOrMultiplyOp = (Operator) multiplicationStack.pop();
                Number leftNumber = (Number) multiplicationStack.pop();
                multiplicationStack.push(divisionOrMultiplyOp.applyOperator(leftNumber, (Number)part));
            } else {
                multiplicationStack.push(part);
            }
        }
        tvEquation.setText(str);

        Number lastNumber = null;
        Operator lastOperator = null;
        for (EquationPart part : multiplicationStack) {
            if (lastNumber == null)
                lastNumber = (Number) part;
            else if (lastOperator == null)
                lastOperator = (Operator) part;
            else if (((Number) part).isReady()) {
                lastNumber = lastOperator.applyOperator(lastNumber, (Number)part);
                lastOperator = null;
            }
        }
        tvResults.setText(lastNumber.toString());

        if (str.length() <= 45)
            tvEquation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        else if (str.length() < 70)
            tvEquation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        else
            tvEquation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
    }

    public void onDigitClicked(View view) {
        Number n = getCurrentNumber();
        if (n.length() >= 15)
            Toast.makeText(this, "You reached the maximum length of one number", Toast.LENGTH_SHORT).show();
        n.addPart(((TextView)view).getText().toString());
        displayNumbers();
    }

    public void onDotClicked(View view) {
        Number n = getCurrentNumber();
        n.addPoint();
        displayNumbers();
    }

    public void onOperatorClicked(View view) {
        if (stack.size() == 0)
            return;

        try {
            EquationPart lastPart = stack.lastElement();
            Operator op = new Operator(((TextView) view).getText().toString());

            if (lastPart instanceof Number) {
                Number lastNo = (Number) lastPart;
                if (!lastNo.isReady())
                    return;
                stack.push(op);
            } else if (lastPart instanceof Operator) {
                ((Operator) lastPart).setType(op.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        displayNumbers();
    }

    public void onEqualsClicked(View view) {
        try {
            if (tvResults.getText().length() == 0)
                return;
            Number n = new Number(tvResults.getText().toString());
            stack.clear();

            if (Double.isInfinite(n.toDouble()) || Double.isNaN(n.toDouble()))
                stack.push(new Number(0));
            else
                stack.push(n);
        } catch (Exception e) {
            Log.e("Value Error", e.getMessage());
        }

        displayNumbers();
    }

    public void onClearClicked(View view) {
        stack.clear();
        displayNumbers();
    }

    public void onDeleteClicked(View view) {
        if (stack.isEmpty())
            return;

        EquationPart lastPart = stack.lastElement();
        if (lastPart instanceof Number)
            ((Number) lastPart).deleteOne();
        else
            stack.pop();
        displayNumbers();
    }
    public void onMemoryClear(View view)
    {

    }
    public void OnMemoryRead(View view)
    {

    }
    public void onMemoryPlus(View view)
    {

    }
    public void OnMemoryMinus(View view)
    {

    }
}