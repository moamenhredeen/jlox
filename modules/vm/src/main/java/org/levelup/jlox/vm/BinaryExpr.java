package org.levelup.jlox.vm;

public record BinaryExpr(Token operator, Expr left, Expr right)  implements Expr {
}
