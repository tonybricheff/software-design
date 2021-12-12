package com.brichev.calc.token;

import com.brichev.calc.visitor.TokenVisitor;

public class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public static class Plus  extends Operation {
        @Override
        public String toString() {
            return "PLUS";
        }
    }

    public static class Minus extends Operation {
        @Override
        public String toString() {
            return "MINUS";
        }
    }

    public static class Mul  extends Operation {
        @Override
        public String toString() {
            return "MUL";
        }
    }

    public static class Div extends Operation {
        @Override
        public String toString() {
            return "Div";
        }
    }
}