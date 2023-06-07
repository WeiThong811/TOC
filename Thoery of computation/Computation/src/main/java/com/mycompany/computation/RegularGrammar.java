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
import java.util.Set;
import java.util.HashSet;


public class RegularGrammar {
    private Set<Character> terminals;
    private Set<String> nonTerminals;
    private String startSymbol;
    private Map<String, Set<String>> productionRules;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Append the terminals
        sb.append("Terminals: ").append(terminals).append("\n");
        
        // Append the non-terminals
        sb.append("Non-terminals: ").append(nonTerminals).append("\n");
        
        // Append the start symbol
        sb.append("Start Symbol: ").append(startSymbol).append("\n");
        
        // Append the production rules
        sb.append("Production Rules:\n");
        for (Map.Entry<String, Set<String>> entry : productionRules.entrySet()) {
            String nonTerminal = entry.getKey();
            Set<String> productions = entry.getValue();
            for (String production : productions) {
                sb.append(nonTerminal).append(" -> ").append(production).append("\n");
            }
        }
        
        return sb.toString();
    }
    public RegularGrammar() {
    this.terminals = new HashSet<>();
    this.nonTerminals = new HashSet<>();
    this.startSymbol = "";
    this.productionRules = new HashMap<>();

}


    public RegularGrammar(Set<Character> terminals, Set<String> nonTerminals, String startSymbol) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
        this.productionRules = new HashMap<>();
        for(String nonTerminal: nonTerminals) {
            productionRules.put(nonTerminal, new HashSet<>());
        }
    }
     public void addNonTerminal(String nonTerminal) {
        this.nonTerminals.add(nonTerminal);
        this.productionRules.put(nonTerminal, new HashSet<>());
    }
    public void addProductionRule(String nonTerminal, String production) {
        if(!nonTerminals.contains(nonTerminal) || !production.matches("[a-zA-Z0-9]*")) {
            throw new IllegalArgumentException("Invalid nonTerminal or production");
        }
        productionRules.get(nonTerminal).add(production);
    }
    public boolean isAccepted(String input) {
        Set<String> currentProductions = new HashSet<>();
        currentProductions.add(startSymbol);

        for (char symbol : input.toCharArray()) {
            Set<String> nextProductions = new HashSet<>();
            for (String current : currentProductions) {
                for (String production : getProductions(current)) {
                    if (production.length() > 0 && production.charAt(0) == symbol) {
                        if (production.length() > 1) {
                            nextProductions.add(production.substring(1));
                        } else {
                            nextProductions.add("");
                        }
                    }
                }
            }
            currentProductions = nextProductions;
        }

        return currentProductions.contains("");
    }
    public Set<String> getProductions(String nonTerminal) {
    return productionRules.getOrDefault(nonTerminal, Collections.emptySet());
}

}
