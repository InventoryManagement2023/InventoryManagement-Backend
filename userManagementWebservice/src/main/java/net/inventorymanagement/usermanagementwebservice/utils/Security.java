package net.inventorymanagement.usermanagementwebservice.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.text.AES256TextEncryptor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Encryption with <a href="http://jasypt.org/">Jasypt</a> and {@link AES256TextEncryptor}
 * PBEWithHMACSHA512AndAES_256 and 1000 key obtention iterations
 */
@Slf4j
public class Security {

    public String encrypt(Security.TokenEntity entity, String tokenSalt) {
        if (StringUtils.isEmpty(tokenSalt)) {
            log.error("Unable to encrypt TokenEntity {} because token salt is null.", entity);
            return null;
        }
        AES256TextEncryptor encryptor = initEncryptor(tokenSalt);
        try {
            if (entity.isValid()) {
                return encryptor.encrypt(entity.toString());
            }
        } catch(Exception ex) {
            log.error("Unable to encrypt {}", entity, ex);
        }
        return null;
    }

    public Security.TokenEntity decrypt(String encryptedToken, String tokenSalt) {
        if (StringUtils.isEmpty(tokenSalt)) {
            log.error("Unable to decrypt token {} because token salt is null.", encryptedToken);
            return null;
        }
        AES256TextEncryptor encryptor = initEncryptor(tokenSalt);
        try {
            String decryptedToken = encryptor.decrypt(encryptedToken);
            return new TokenEntity(decryptedToken);
        } catch (Exception ex) {
            log.error("Unable to decrypt {}", encryptedToken, ex);
            return null;
        }
    }

    private AES256TextEncryptor initEncryptor(String password) {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(password);
        return encryptor;
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
