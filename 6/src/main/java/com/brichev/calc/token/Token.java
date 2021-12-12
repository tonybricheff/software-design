package com.brichev.calc.token;

import com.brichev.calc.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
