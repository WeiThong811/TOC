/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.computation;
import com.mycompany.computation.Home;
/**
 *
 * @author Hussnain
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Computation {
    
    // Function to convert a FA into a regular grammar
    public static Map<String, Set<String>> convertFAToRegularGrammar(Map<String, Map<Character, String>> transitions, String startState, Set<String> finalStates) {
        Map<String, Set<String>> productions = new HashMap<>();

        for (String state : transitions.keySet()) {
            Map<Character, String> stateTransitions = transitions.get(state);
            Set<String> stateProductions = new HashSet<>();

            for (Character symbol : stateTransitions.keySet()) {
                String nextState = stateTransitions.get(symbol);
                stateProductions.add(symbol + nextState);
            }

            productions.put(state, stateProductions);
        }

        for (String finalState : finalStates) {
            if (!productions.containsKey(finalState)) {
                productions.put(finalState, new HashSet<>());
            }
            productions.get(finalState).add("ε");
        }

        productions.put(startState, new HashSet<>());
        return productions;
    }
    
    // Function to test strings and determine acceptance/rejection
    public static void testStrings(Map<String, Set<String>> productions, String inputString) {
        Set<String> visited = new HashSet<>();
        String startSymbol = productions.keySet().iterator().next();

        if (isAccepted(productions, startSymbol, inputString, visited)) {
            System.out.println(inputString + " is accepted by the FA.");
        } else {
            System.out.println(inputString + " is rejected by the FA.");
        }
    }

    // Helper function to determine if a string is accepted by the FA
    private static boolean isAccepted(Map<String, Set<String>> productions, String currentSymbol, String remainingString, Set<String> visited) {
        if (remainingString.isEmpty()) {
            return productions.get(currentSymbol).contains("ε");
        }

        if (visited.contains(currentSymbol)) {
            return false; // Avoid infinite loops in cyclic grammars
        }

        visited.add(currentSymbol);
        for (String production : productions.get(currentSymbol)) {
            if (!production.equals("ε")) {
                char symbol = production.charAt(0);
                String nextState = production.substring(1);
                if (remainingString.charAt(0) == symbol) {
                    if (isAccepted(productions, nextState, remainingString.substring(1), visited)) {
                        return true;
                    }
                }
            }
        }

        visited.remove(currentSymbol);
        return false;
    }

    public static void main(String[] args) {
        // Define the FA transitions
        Map<String, Map<Character, String>> transitions = new HashMap<>();
        Map<Character, String> q0Transitions = new HashMap<>();
        q0Transitions.put('a', "q1");
        transitions.put("q0", q0Transitions);

        Map<Character, String> q1Transitions = new HashMap<>();
        q1Transitions.put('b', "q2");
        transitions.put("q1", q1Transitions);

        Map<Character, String> q2Transitions = new HashMap<>();
        q2Transitions.put('c', "q3");
        transitions.put("q2", q2Transitions);

        Map<Character, String> q3Transitions = new HashMap<>();
        q3Transitions.put('d', "q4");
        transitions.put("q3", q3Transitions);

Map<Character, String> q4Transitions = new HashMap<>();
        q4Transitions.put('e', "q5");
        transitions.put("q4", q4Transitions);

        // Define the final states of the FA
        Set<String> finalStates = new HashSet<>();
        finalStates.add("q5");

        // Convert FA into regular grammar
        Map<String, Set<String>> productions = convertFAToRegularGrammar(transitions, "q0", finalStates);

        // Test strings
        testStrings(productions, "abcde");
        testStrings(productions, "abbde");
        testStrings(productions, "abcdeee");
        testStrings(productions, "abcdef");
        testStrings(productions, "acde");
        Home home = new Home(); // Create an instance of the Home class
        
        // Customize or initialize the Home frame as needed
        // For example: home.setTitle("My Home");
        
        home.setVisible(true); // Show the Home frame
                }
}