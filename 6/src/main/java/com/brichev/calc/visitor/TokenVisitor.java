package com.brichev.calc.visitor;

import com.brichev.calc.token.Brace;
import com.brichev.calc.token.NumberToken;
import com.brichev.calc.token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
