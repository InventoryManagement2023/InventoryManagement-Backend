package net.inventorymanagement.usermanagementwebservice.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityTokenEntityTest {
    @Test
    public void shouldParseValidPlainTokenString() throws Exception {
        String plainTokenString = "21@2023-05-16T16:59:23.188+0000";
        Security.TokenEntity result = new Security.TokenEntity(plainTokenString);
        assertEquals(21, result.getUserId());
        assertEquals(1684256363188L, result.getExpirationDate().getTime());
    }

    @Test
    public void shouldThrowExceptionWhenParsingAnWrongPlainTokenString() throws Exception {
        String plainTokenString = "HomerSimpson";
        try {
            Security.TokenEntity result = new Security.TokenEntity(plainTokenString);
            fail("Should throw Exception when parsing a wrong PlainTokenString");
        } catch(Exception ex) {
            // Exception is expected
        }
    }

    @Test
    public void shouldThrowExceptionWhenParsingAnInvalidPlainTokenString() throws Exception {
        String plainTokenString = "1@fghfghj";
        try {
            new Security.TokenEntity(plainTokenString);
            fail("Should throw Exception when parsing an invalid PlainTokenString");
        } catch(Exception ex) {
            // Exception is expected
        }

        plainTokenString = "fghfghj";
        try {
            new Security.TokenEntity(plainTokenString);
            fail("Should throw Exception when parsing an invalid PlainTokenString");
        } catch(Exception ex) {
            // Exception is expected
        }

        plainTokenString = null;
        try {
            new Security.TokenEntity(plainTokenString);
            fail("Should throw Exception when parsing an invalid PlainTokenString");
        } catch(Exception ex) {
            // Exception is expected
        }
        plainTokenString = "@2023-05-16T16:59:23.188+0000";
        try {
            new Security.TokenEntity(plainTokenString);
            fail("Should throw Exception when parsing an invalid PlainTokenString");
        } catch(Exception ex) {
            // Exception is expected
        }
    }

    @Test
    public void whenToStringShouldConvertToPlainTokenString() throws Exception {
        Security.TokenEntity result = new Security.TokenEntity(21, new Date(1684256363188L));
        assertEquals("21@2023-05-16T16:59:23.188+0000", result.toString());
    }
}
