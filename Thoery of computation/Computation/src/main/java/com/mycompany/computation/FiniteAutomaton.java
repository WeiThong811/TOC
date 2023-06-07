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

public class FiniteAutomaton {
    private Set<String> states;
    private Set<Character> alphabet;
    private String initialState;
    private Set<String> finalStates;
    private Map<String, Map<Character, Set<String>>> transitionFunction;
    private static final char EPSILON = 'ε';  // Represents epsilon transitions
    
    public FiniteAutomaton() {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitionFunction = new HashMap<>();
    }


    public FiniteAutomaton(Set<String> states, Set<Character> alphabet, String initialState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitionFunction = new HashMap<>();
        for(String state: states) {
            transitionFunction.put(state, new HashMap<>());
        }
    }
    
    public void addState(String state) {
        this.states.add(state);
        transitionFunction.put(state, new HashMap<>());
    }

    public void addAlphabetSymbol(char symbol) {
        this.alphabet.add(symbol);
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public void addFinalState(String finalState) {
        this.finalStates.add(finalState);
    }
    
    public void addTransition(String fromState, char symbol, String toState) {
        if(!states.contains(fromState) || !states.contains(toState) || (!alphabet.contains(symbol) && symbol != EPSILON)) {
            throw new IllegalArgumentException("Invalid state or symbol");
        }
        transitionFunction.get(fromState).computeIfAbsent(symbol, k -> new HashSet<>()).add(toState);
    }

    public Set<String> getTransition(String fromState, char symbol) {
        return transitionFunction.get(fromState).getOrDefault(symbol, new HashSet<>());
    }

    public Set<String> getStates() {
        return this.states;
    }

    public Set<Character> getAlphabet() {
        return this.alphabet;
    }

    public Set<String> getFinalStates() {
        return this.finalStates;
    }
    
    // Function to perform epsilon closure
    private Set<String> epsilonClosure(String state) {
        Set<String> closure = new HashSet<>();
        Stack<String> stack = new Stack<>();
        closure.add(state);
        stack.push(state);

        while (!stack.isEmpty()) {
            String currentState = stack.pop();
            for (String nextState : getTransition(currentState, EPSILON)) {
                if (!closure.contains(nextState)) {
                    closure.add(nextState);
                    stack.push(nextState);
                }
            }
        }

        return closure;
    }

    public boolean accepts(String input) {
        Set<String> currentStates = epsilonClosure(initialState);
        for(char symbol: input.toCharArray()) {
            Set<String> nextStates = new HashSet<>();
            for (String state : currentStates) {
                nextStates.addAll(getTransition(state, symbol));
            }
            currentStates.clear();
            for (String state : nextStates) {
                currentStates.addAll(epsilonClosure(state));
            }
        }

        // Check if any of the current states is a final state
        currentStates.retainAll(finalStates);
        return !currentStates.isEmpty();
    }
}
