package com.example.morsecodeapp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MorseCode {
    private static Map<Character, String> alphabet;
    private static String input;
    private static ArrayList<String> morseList;

    public MorseCode(String i) {
        alphabet = new HashMap<Character, String>() {{
            put('A', ".-");
            put('B', "-...");
            put('C', "-.-.");
            put('D', "-..");
            put('E', ".");
            put('F', "..-.");
            put('G', "--.");
            put('H', "....");
            put('I', "..");
            put('J', ".---");
            put('K', "-.-");
            put('L', ".-..");
            put('M', "--");
            put('N', "-.");
            put('O', "---");
            put('P', ".--.");
            put('Q', "--.-");
            put('R', ".-.");
            put('S', "...");
            put('T', "-");
            put('U', "..-");
            put('V', "...-");
            put('W', ".--");
            put('X', "-..-");
            put('Y', "-.--");
            put('Z', "--..");
        }};
        input = i;
    }

    public static ArrayList<String> convertedString(){
        morseList = new ArrayList<String>();
        input = input.toUpperCase();
        for (char c : input.toCharArray()) {
                morseList.add(alphabet.get(c));
        }
        return morseList;
    }


    public static void main(String[] args) {
        MorseCode m = new MorseCode(" ");
        ArrayList<String> morse = MorseCode.convertedString();
        for (int i = 0; i < morse.size(); i++){
            System.out.println(morse.get(i));
        }
    }
}
