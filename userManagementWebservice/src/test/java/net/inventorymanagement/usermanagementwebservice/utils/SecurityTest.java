package net.inventorymanagement.usermanagementwebservice.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityTest {

    Security security = new Security();

    @Test
    void shouldEncryptAndDecryptValidToken() {
        Date date = new Date();
        Security.TokenEntity toBeEncrypted = new Security.TokenEntity(7, date);
        String result = security.encrypt(toBeEncrypted);
        Security.TokenEntity decrypted = security.decrypt(result);
        assertEquals(toBeEncrypted.getUserId(), decrypted.getUserId());
        assertEquals(toBeEncrypted.getExpirationDate(), decrypted.getExpirationDate());
    }

    @Test
    void encryptShouldReturnNullWhenTokenIsInvalid() {
        // userId is null
        Security.TokenEntity toBeEncrypted = new Security.TokenEntity(null, new Date());
        String result = security.encrypt(toBeEncrypted);
        assertNull(result);

        // expirationDate is null
        toBeEncrypted = new Security.TokenEntity(1, null);
        result = security.encrypt(toBeEncrypted);
        assertNull(result);

        // userId and expirationDate is null
        toBeEncrypted = new Security.TokenEntity(null, null);
        result = security.encrypt(toBeEncrypted);
        assertNull(result);
    }

    @Test
    public void decryptShouldReturnNullWhenTokenCanNotBeDecrypted() {
        Security.TokenEntity decrypted = security.decrypt("fvfdklbgl");
        assertNull(decrypted);
    }

    @Test
    public void encryptShouldReturnNullWhenTokenIsNull() {
        Security.TokenEntity decrypted = security.decrypt(null);
        assertNull(decrypted);
    }

    @Test
    public void decryptValidToken() {
        Security.TokenEntity decrypted = security.decrypt("vr4hp1SE2UdFF2BXYVwCIY5mQDCUAreKfSnazFVg5WJe7vcMuxJfXg==");
        assertNotNull(decrypted);
        System.out.println(decrypted);
    }
}