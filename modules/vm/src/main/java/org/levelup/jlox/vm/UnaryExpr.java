package org.levelup.jlox.vm;

public record UnaryExpr(Token operator, Expr expr) implements Expr {
}
