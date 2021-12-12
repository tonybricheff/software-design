package com.brichev.calc.token;

import com.brichev.calc.visitor.TokenVisitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberToken implements Token {
    private Integer number;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("NUMBER(%d)", number);
    }
}
