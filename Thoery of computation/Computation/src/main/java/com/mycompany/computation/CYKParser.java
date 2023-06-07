/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.computation;

/**
 *
 * @author Hussnain
 */
import java.util.*;

public class CYKParser {
    
    private Map<String, List<String>> grammar;
    
    public CYKParser(Map<String, List<String>> grammar) {
        this.grammar = grammar;
    }
    
//    public String[][] parse(String input) {
//    int length = input.length();
//    String[][] table = new String[length][length];
       public String[][] parse(String input) {
    int length = input.length();
    String[][] table = new String[length][length];

    // Fill all cells with an empty string
    for (String[] row : table) {
        Arrays.fill(row, "");
    }
    for (int i = 0; i < length; i++) {
        List<String> rules = grammar.get(Character.toString(input.charAt(i)));
        if (rules != null) {
            for (String rule : rules) {
                // Check for empty string instead of null
                if (table[i][i].isEmpty()) {
                    table[i][i] = rule;
                } else {
                    table[i][i] += ", " + rule;
                }
            }
        }
    }

    for (int span = 2; span <= length; span++) {
        for (int begin = 0; begin <= length - span; begin++) {
            int end = begin + span - 1;
            for (int mid = begin; mid < end; mid++) {
                for (String lhs : grammar.keySet()) {
                    List<String> rhs = grammar.get(lhs);
                    if (rhs != null) {
                        for (String production : rhs) {
                            if (production.length() == 2 && contains(table, begin, mid, end, production)) {
                                // Check for empty string instead of null
                                if (table[begin][end].isEmpty()) {
                                    table[begin][end] = lhs;
                                } else if (!table[begin][end].contains(lhs)){
                                    table[begin][end] += ", " + lhs;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    return table;
}


    
    private boolean contains(String[][] table, int begin, int mid, int end, String production) {
    String a = Character.toString(production.charAt(0));
    String b = Character.toString(production.charAt(1));
    if (!table[begin][mid].isEmpty() && !table[mid+1][end].isEmpty()) {
        return table[begin][mid].contains(a) && table[mid+1][end].contains(b);
    }
    return false;
}

    
    public static void main(String[] args) {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("A", Arrays.asList("a"));
        grammar.put("B", Arrays.asList("b"));
        grammar.put("S", Arrays.asList("AB", "BB"));
        grammar.put("C", Arrays.asList("AB", "a"));
        
        CYKParser parser = new CYKParser(grammar);
        String[][] table = parser.parse("baaba");
        
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == null) {
                    System.out.print(" - ");
                } else {
                    System.out.print(" " + table[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

