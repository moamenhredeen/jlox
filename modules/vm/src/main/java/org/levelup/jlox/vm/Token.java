package org.levelup.jlox.vm;

public record Token(String lexeme, int line, TokenType type) {
}
