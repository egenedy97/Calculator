package com.example.mytrain;
import androidx.annotation.NonNull;
public class Number extends EquationPart {
    String noStr = "";

    Number(String str) {
        noStr = str;
    }

    Number(long n) {
        noStr = String.valueOf(n);
    }

    Number(double n) {
        if (n%1 == 0 && n < Long.MAX_VALUE)
            noStr = String.valueOf((new Double(n)).longValue());
        else
            noStr = String.valueOf(n);
    }

    public Number() {

    }

    boolean addPart(String s) {
        if ((noStr.length() + s.length()) > 15)
            return false;
        if (noStr.isEmpty() && s.startsWith("E"))
            return false;

        boolean containsPoint = noStr.contains(".");
        boolean containsE = noStr.contains("E");
        for (char c : s.toCharArray()) {
            if ((c < '0' || c > '9') && !(c == '.' && !containsPoint && containsE) && !(c == 'E' && !containsE))
                return false;
        }

        if (noStr.endsWith(".") && s.startsWith("E"))
            return false;
        noStr += s;

        if (noStr.length() > 0 && noStr.startsWith("0")) {
            int i = 0;
            while (i < noStr.length() && noStr.charAt(i) == '0')
                ++i;

            if (i == noStr.length())
                noStr = "0";
            else
                noStr = noStr.substring(i);
        }
        return true;
    }

    boolean addPoint() {
        if (noStr.contains(".") || noStr.contains("E"))
            return false;
        noStr += ".";
        return true;
    }

    boolean isReady() {
        return (!noStr.equals(".") && !noStr.isEmpty() && !noStr.endsWith("E"));
    }

    int length() {
        return noStr.length();
    }

    boolean deleteOne() {
        if (noStr.length() == 0)
            return false;
        noStr = noStr.substring(0, noStr.length() - 1);
        return true;
    }

    boolean isEmpty() {
        return noStr.isEmpty();
    }

    boolean isFloat() {
        return noStr.contains(".");
    }

    double toDouble() {
        return Double.parseDouble(noStr);
    }

    @NonNull
    @Override
    public String toString() {
        return noStr;
    }
}