create table remember_me_cookie_config
(
    id int primary key,
    days_until_expiration int
);

insert into remember_me_cookie_config values (1,1);