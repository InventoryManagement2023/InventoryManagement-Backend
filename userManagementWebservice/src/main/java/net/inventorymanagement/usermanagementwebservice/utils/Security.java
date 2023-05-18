package net.inventorymanagement.usermanagementwebservice.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.StrongTextEncryptor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Encryption with <a href="http://jasypt.org/">Jasypt</a> and {@link StrongTextEncryptor}
 * PBEWithMD5AndTripleDES and 1000 key obtention iterations
 */
@Slf4j
public class Security {

    private static final String CIPHER_KEY = "q!j?LVyGG4St$4ZbY44C";
    private final StrongTextEncryptor encryptor = new StrongTextEncryptor();

    public Security() {
        encryptor.setPassword(CIPHER_KEY);
    }

    public String encrypt(Security.TokenEntity entity) {
        try {
            if (entity.isValid()) {
                return encryptor.encrypt(entity.toString());
            }
        } catch(Exception ex) {
            log.error("Unable to encrypt {}", entity, ex);
        }
        return null;
    }

    public Security.TokenEntity decrypt(String encryptedToken) {
        try {
            String decryptedToken = encryptor.decrypt(encryptedToken);
            return new TokenEntity(decryptedToken);
        } catch (Exception ex) {
            log.error("Unable to decrypt {}", encryptedToken, ex);
            return null;
        }
    }

    @Getter
    @Setter
    public static class TokenEntity implements Serializable {
        private Integer userId;
        private Date expirationDate;

        private final SimpleDateFormat sdf;
        private static final String delimiter = "@";

        private TokenEntity() {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        public TokenEntity(Integer userId, Date expirationDate) {
            this();
            this.userId = userId;
            this.expirationDate = expirationDate;
        }

        protected TokenEntity(String plainTokenString) throws Exception{
            this();
            String[] parts = plainTokenString.split(delimiter);
            this.userId = Integer.valueOf(parts[0]);
            this.expirationDate = sdf.parse(parts[1]);
            if (!isValid()) {
                throw new Exception("The given plainTokenString " + plainTokenString + " doesn't produce a valid TokenEntity!");
            }
        }

        @Override
        public String toString() {
            return userId + delimiter + sdf.format(expirationDate);
        }

        public boolean isValid() {
            return userId != null && expirationDate != null;
        }
    }
}
