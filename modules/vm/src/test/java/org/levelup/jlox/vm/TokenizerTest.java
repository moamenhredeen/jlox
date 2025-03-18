package org.levelup.jlox.vm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    private Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenizer = new Tokenizer();
    }

    @Test
    void tokenizeEmptyScript(){
        var res = tokenizer.scan("");
        assertEquals(1, res.size());
        assertEquals(new Token("", 1, TokenType.EOF), res.get(0));
    }


    @Test
    void tokenizeTabWhitespaceAndNewLine(){
        var res = tokenizer.scan("""
                    
                    
                    
                """);
        assertEquals(1, res.size());
        assertEquals(new Token("", 4, TokenType.EOF), res.get(0));
    }

    @Test
    void tokenizeSingleCharacterOperators(){
        var tokens = tokenizer.scan("() {} , ; + - * / .");
        assertEquals(List.of(
                new Token("(", 1, TokenType.LEFT_PAREN),
                new Token(")", 1, TokenType.RIGHT_PAREN),
                new Token("{", 1, TokenType.LEFT_BRACE),
                new Token("}", 1, TokenType.RIGHT_BRACE),
                new Token(",", 1, TokenType.COMMA),
                new Token(";", 1, TokenType.SIMICOLON),
                new Token("+", 1, TokenType.PLUS),
                new Token("-", 1, TokenType.MINUS),
                new Token("*", 1, TokenType.STAR),
                new Token("/", 1, TokenType.SLASH),
                new Token(".", 1, TokenType.DOT),
                new Token("", 1, TokenType.EOF)
        ), tokens);
    }

    @Test
    void tokenizeMultiCharacterOperators(){
        var tokens = tokenizer.scan("! != = == < <= > >=");
        assertEquals(List.of(
                new Token("!", 1, TokenType.BANG),
                new Token("!=", 1, TokenType.BANG_EQUAL),
                new Token("=", 1, TokenType.EQUAL),
                new Token("==", 1, TokenType.EQUAL_EQUAL),
                new Token("<", 1, TokenType.LESS),
                new Token("<=", 1, TokenType.LESS_EQUAL),
                new Token(">", 1, TokenType.GREATER),
                new Token(">=", 1, TokenType.GREATER_EQUAL),
                new Token("", 1, TokenType.EOF)
        ), tokens);
    }

    @Test
    void ignoreComments() {
        var tokens = tokenizer.scan("// comment");
        assertEquals(List.of(
                new Token("", 1, TokenType.EOF)
        ), tokens);


        tokens = tokenizer.scan("""
                        // this will be ignored () {}
                        // and this {} + - / *
                        """);
        assertEquals(List.of(
                new Token("", 3, TokenType.EOF)
        ), tokens);
    }

    @Test
    void tokenizeStrings(){
        var tokens = tokenizer.scan("\"hello world\"");
        assertEquals(List.of(
                new Token("hello world", 1, TokenType.STRING),
                new Token("", 1, TokenType.EOF)
        ), tokens);
    }
}