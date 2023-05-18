create table configuration
(
    id int primary key,
    remember_me_cookie_days_until_expiration int
);

insert into configuration values (1,1);