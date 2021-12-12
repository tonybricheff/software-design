package com.brichev.calc.visitor;

import com.brichev.calc.token.Brace;
import com.brichev.calc.token.NumberToken;
import com.brichev.calc.token.Operation;
import com.brichev.calc.token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    @Override
    public void visit(NumberToken token) {
        System.out.println(token);
    }

    @Override
    public void visit(Brace token) {
        System.out.println(token);
    }

    @Override
    public void visit(Operation token) {
        System.out.println(token);
    }

    public static void visit(List<Token> tokens) {
       System.out.println(tokens.toString());
    }
}
