package com.brichev.calc.visitor;

import com.brichev.calc.token.Brace;
import com.brichev.calc.token.NumberToken;
import com.brichev.calc.token.Operation;
import com.brichev.calc.token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private final Deque<Integer> stack = new ArrayDeque<>();

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getNumber());
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalArgumentException("Invalid notation");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2)
            throw new IllegalArgumentException("Invalid notation");
        var y = stack.removeLast();
        var x = stack.removeLast();
        if (token instanceof Operation.Plus) {
            stack.add(x + y);
        } else if (token instanceof Operation.Minus) {
            stack.add(x - y);
        } else if (token instanceof Operation.Mul) {
            stack.add(x * y);
        } else if (token instanceof Operation.Div) {
            stack.add(x / y);
        }
    }

    public Integer visit(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid notation");
        }
        return stack.getLast();
    }
}
