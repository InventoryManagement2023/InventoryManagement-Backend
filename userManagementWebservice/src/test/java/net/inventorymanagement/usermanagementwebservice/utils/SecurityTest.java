package net.inventorymanagement.usermanagementwebservice.utils;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityTest {

    Security security = new Security();

    @Test
    void shouldEncryptAndDecryptValidToken() {
        String tokenSalt = UUID.randomUUID().toString();
        Date date = new Date();
        Security.TokenEntity toBeEncrypted = new Security.TokenEntity(7, date);
        String result = security.encrypt(toBeEncrypted,tokenSalt);
        Security.TokenEntity decrypted = security.decrypt(result,tokenSalt);
        assertEquals(toBeEncrypted.getUserId(), decrypted.getUserId());
        assertEquals(toBeEncrypted.getExpirationDate(), decrypted.getExpirationDate());
    }

    @Test
    void encryptShouldReturnNullWhenTokenIsInvalid() {
        String tokenSalt = UUID.randomUUID().toString();
        // userId is null
        Security.TokenEntity toBeEncrypted = new Security.TokenEntity(null, new Date());
        String result = security.encrypt(toBeEncrypted,tokenSalt);
        assertNull(result);

        // expirationDate is null
        toBeEncrypted = new Security.TokenEntity(1, null);
        result = security.encrypt(toBeEncrypted,tokenSalt);
        assertNull(result);

        // userId and expirationDate is null
        toBeEncrypted = new Security.TokenEntity(null, null);
        result = security.encrypt(toBeEncrypted,tokenSalt);
        assertNull(result);
    }

    @Test
    public void decryptShouldReturnNullWhenTokenCanNotBeDecrypted() {
        String tokenSalt = UUID.randomUUID().toString();
        Security.TokenEntity decrypted = security.decrypt("fvfdklbgl",tokenSalt);
        assertNull(decrypted);
    }

    @Test
    public void decryptShouldReturnNullWhenTokenIsNull() {
        String tokenSalt = UUID.randomUUID().toString();
        Security.TokenEntity decrypted = security.decrypt(null,tokenSalt);
        assertNull(decrypted);
    }

    @Test
    public void decryptValidToken() {
        String tokenSalt = "f2fded5c-bd3d-47d5-8149-5ba19d12aaf3";
        Security.TokenEntity decrypted = security.decrypt("c26qgzj3jORApkhM8cnjswBhS5YKwRhofRptWTdiZfpPnMMFNpubwmxtu8QzxRUCqDZyWhNgf0pG/6WAUCF5ww==",tokenSalt);
        assertNotNull(decrypted);
    }

    @Test
    public void encryptShouldReturnNullWhenTokenSaltIsNull() {
        String decrypted = security.encrypt(new Security.TokenEntity(1, new Date()), null);
        assertNull(decrypted);
    }

    @Test
    public void decryptShouldReturnNullWhenTokenSaltIsNull() {
        Security.TokenEntity encrypted = security.decrypt("32423452345234", null);
        assertNull(encrypted);
    }

    @Test
    public void whenDecryptingWithDifferentTokenSaltThanDuringEncryptionShouldReturnNull() {
        String tokenSaltEncryption = UUID.randomUUID().toString();
        Date date = new Date();
        Security.TokenEntity toBeEncrypted = new Security.TokenEntity(7, date);
        String result = security.encrypt(toBeEncrypted,tokenSaltEncryption);
        String tokenSaltDecryption = UUID.randomUUID().toString();
        Security.TokenEntity decrypted = security.decrypt(result,tokenSaltDecryption);
        assertNull(decrypted);
    }
}