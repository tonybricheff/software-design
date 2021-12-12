package com.brichev.calc.tokenizer;

import com.brichev.calc.token.Brace;
import com.brichev.calc.token.NumberToken;
import com.brichev.calc.token.Operation;
import com.brichev.calc.token.Token;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class Tokenizer {
    private final State ARITHMETIC_STATE = new ArithmeticState();
    private final State EOF_STATE = new EofState();
    private State currentState = ARITHMETIC_STATE;
    private final List<Token> tokens = new ArrayList<>();

    public List<Token> tokenize(String expression) {
        tokens.clear();
        currentState = ARITHMETIC_STATE;
        expression.chars()
                .mapToObj(item -> (char) item)
                .forEach(
                        ch -> currentState.processChar(ch)
                );
        currentState.processEof();
        return tokens;
    }

    private abstract class State {
        abstract void processChar(Character ch);

        void processEof() {
            currentState = EOF_STATE;
        }
    }

    private class ArithmeticState extends State {

        @Override
        void processChar(Character ch) {
            if (ch.equals('+')) {
                tokens.add(new Operation.Plus());
            } else if (ch.equals('-')) {
                tokens.add(new Operation.Minus());
            } else if (ch.equals('*')) {
                tokens.add(new Operation.Mul());
            } else if (ch.equals('/')) {
                tokens.add(new Operation.Div());
            } else if (ch.equals('(')) {
                tokens.add(new Brace.LeftBrace());
            } else if (ch.equals(')')) {
                tokens.add(new Brace.RightBrace());
            } else if (isDigit(ch)) {
                currentState = new NumberState();
                currentState.processChar(ch);
            } else if (!isWhitespace(ch)) {
                throw new IllegalArgumentException("Invalid char: " + ch);
            }
        }
    }

    private class NumberState extends State {
        private Integer number = 0;

        @Override
        void processChar(Character ch) {
            if (isDigit(ch)) {
                number = number * 10 + Integer.parseInt(ch.toString());
            } else {
                tokens.add(new NumberToken(number));
                currentState = ARITHMETIC_STATE;
                currentState.processChar(ch);
            }
        }

        @Override
        void processEof() {
            tokens.add(new NumberToken(number));
        }
    }

    private class EofState extends State {
        @Override
        void processChar(Character ch) {
        }
    }
}
