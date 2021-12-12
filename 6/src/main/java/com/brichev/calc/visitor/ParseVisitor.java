package com.brichev.calc.visitor;

import com.brichev.calc.token.Brace;
import com.brichev.calc.token.NumberToken;
import com.brichev.calc.token.Operation;
import com.brichev.calc.token.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParseVisitor implements TokenVisitor {
    private final List<Token> tokens = new ArrayList<>();
    private final Deque<Token> stack = new ArrayDeque<>();

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token instanceof Brace.LeftBrace) {
            stack.add(token);
        } else if (token instanceof Brace.RightBrace) {
            while (!stack.isEmpty() && !(stack.getLast() instanceof Brace.LeftBrace)) {
                tokens.add(stack.removeLast());
            }
            if (stack.isEmpty()) {
                throw new IllegalArgumentException("Left brace expected");
            } else {
                stack.removeLast();
            }
        }
    }

    @Override
    public void visit(Operation token) {
        if (token instanceof Operation.Plus || token instanceof Operation.Minus) {
            if (!stack.isEmpty() && !(stack.getLast() instanceof Brace.LeftBrace)) {
                tokens.add(stack.removeLast());
            }
            stack.add(token);
        } else if (token instanceof Operation.Mul || token instanceof Operation.Div) {
            if (!stack.isEmpty() && (stack.getLast() instanceof Operation.Mul || stack.getLast() instanceof Operation.Div)) {
                tokens.add(stack.removeLast());
            }
            stack.add(token);
        }
    }

    public List<Token> visit(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        while (!stack.isEmpty()) {
            this.tokens.add(stack.removeLast());
        }
        return this.tokens;
    }
}
