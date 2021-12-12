package com.brichev.calc.token;

import com.brichev.calc.visitor.TokenVisitor;

public class Brace implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public static class RightBrace extends Brace {
        @Override
        public String toString() {
            return "RIGHT";
        }
    }

    public static class LeftBrace extends Brace {
        @Override
        public String toString() {
            return "LEFT";
        }
    }
}