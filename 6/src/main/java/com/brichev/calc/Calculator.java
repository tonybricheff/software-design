package com.brichev.calc;

import com.brichev.calc.tokenizer.Tokenizer;
import com.brichev.calc.visitor.CalcVisitor;
import com.brichev.calc.visitor.ParseVisitor;
import com.brichev.calc.visitor.PrintVisitor;

public class Calculator {
    public void calculate(String expression) {
        var tokens = new Tokenizer().tokenize(expression);
        var rpnTokens = new ParseVisitor().visit(tokens);
        PrintVisitor.visit(tokens);
        PrintVisitor.visit(rpnTokens);
        var result = new CalcVisitor().visit(rpnTokens);
        System.out.println(expression + " = " + result);
    }
}
