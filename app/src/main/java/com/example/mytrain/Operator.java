package com.example.mytrain;
import androidx.annotation.NonNull;


public class Operator extends EquationPart {
    private OperatorType type;

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }

    public Operator(OperatorType type) {
        this.type = type;
    }

    public Operator(String s) throws Exception {
        switch (s) {
            case "+":
                this.type = OperatorType.plus;
                break;
            case "-":
                this.type = OperatorType.minus;
                break;
            case "×":
                this.type = OperatorType.multiply;
                break;
            case "/":
                this.type = OperatorType.division;
                break;
            default:
                throw new Exception("Invalid Operator");
        }
    }

    public Number applyOperator(Number leftNo, Number rightNo) {
        switch (type) {
            case plus:
                return new Number(leftNo.toDouble() + rightNo.toDouble());
            case minus:
                return new Number(leftNo.toDouble() - rightNo.toDouble());
            case division:
                return new Number(leftNo.toDouble() / rightNo.toDouble());
            default:
                return new Number(leftNo.toDouble() * rightNo.toDouble());
        }
    }

    boolean isMultiplyOrDivision() {
        return (type == OperatorType.multiply || type == OperatorType.division);
    }

    @NonNull
    @Override
    public String toString() {
        switch (type) {
            case plus:
                return "+";
            case minus:
                return "-";
            case division:
                return "/";
            default:
                return "×";
        }
    }
}

enum OperatorType {plus, minus, multiply, division}