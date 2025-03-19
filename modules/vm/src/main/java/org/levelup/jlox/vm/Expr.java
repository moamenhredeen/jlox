package org.levelup.jlox.vm;

public sealed interface Expr
        permits LiteralExpr, BinaryExpr, UnaryExpr, GroupExpr{
}
