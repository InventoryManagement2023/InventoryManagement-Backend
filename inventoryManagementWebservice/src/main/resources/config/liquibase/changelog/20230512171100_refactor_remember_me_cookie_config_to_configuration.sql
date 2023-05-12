ALTER TABLE remember_me_cookie_config RENAME TO configuration;

ALTER TABLE configuration RENAME COLUMN
    days_until_expiration TO remember_me_cookie_days_until_expiration;