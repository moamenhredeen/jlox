package org.levelup.jlox.vm;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tokenizer {
    public List<Token> scan(String source) {
        List<Token> tokens = new ArrayList<>();
        int line = 1;
        int start = 0;
        int current = 0;

        while(current < source.length()){
            start = current;
            var token = switch (source.charAt(current)) {
                case '(' -> new Token("(", line, TokenType.LEFT_PAREN);
                case ')' -> new Token(")", line, TokenType.RIGHT_PAREN);
                case '{' -> new Token("{", line, TokenType.LEFT_BRACE);
                case '}' -> new Token("}", line, TokenType.RIGHT_BRACE);
                case ',' -> new Token(",", line, TokenType.COMMA);
                case ';' -> new Token(";", line, TokenType.SIMICOLON);
                case '-' -> new Token("-", line, TokenType.MINUS);
                case '+' -> new Token("+", line, TokenType.PLUS);
                case '*' -> new Token("*", line, TokenType.STAR);
                case '.' -> new Token(".", line, TokenType.DOT);
                case '!'  -> {
                    if (current + 1 < source.length() && source.charAt(current + 1) == '=') {
                        current++;
                        yield  new Token("!=", line, TokenType.BANG_EQUAL);
                    }else {
                        yield  new Token("!", line, TokenType.BANG);
                    }
                }
                case '=' -> {
                    if (current + 1 < source.length() && source.charAt(current + 1) == '=') {
                        current++;
                        yield  new Token("==", line, TokenType.EQUAL_EQUAL);
                    } else {
                        yield  new Token("=", line, TokenType.EQUAL);
                    }
                }
                case '<' -> {
                    if (current + 1 < source.length() && source.charAt(current + 1) == '=') {
                        current++;
                        yield  new Token("<=", line, TokenType.LESS_EQUAL);
                    } else {
                        yield  new Token("<", line, TokenType.LESS);
                    }
                }
                case '>' -> {
                    if (current + 1 < source.length() && source.charAt(current + 1) == '=') {
                        current++;
                        yield  new Token(">=", line, TokenType.GREATER_EQUAL);
                    } else {
                        yield  new Token(">", line, TokenType.GREATER);
                    }
                }
                case '/' -> {
                    if (current + 1 < source.length() && source.charAt(current + 1) == '/') {
                        do {
                            current++;
                        } while (current + 1 < source.length() && source.charAt(current + 1) != '\n');
                        yield  null;
                    } else {
                        yield  new Token("/", line, TokenType.SLASH);
                    }
                }
                case '"' -> {
                    while (true) {
                        if (current + 1 >= source.length()) yield null;
                        if (source.charAt(current + 1) == '\n') {
                            yield null;
                        }
                        if (source.charAt(current + 1) == '"') {
                            current++;
                            // trim the surrounding quotes
                            yield new Token(source.substring(start + 1, current), line, TokenType.STRING);
                        }
                        current++;
                    }
                }
                case ' ', '\t', '\r' -> null;
                case '\n' -> {
                    line++;
                    yield null;
                }
                default -> new Token("", line, TokenType.UNKNOWN);
            };

            if (token != null) {
                tokens.add(token);
            }
            current++;
        }

        tokens.add(new Token("", line, TokenType.EOF));
        return tokens;
    }
}
