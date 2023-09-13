alter table customer
    add `login_id` varchar(255);

alter table customer
    add `encrypted_password` varchar(255);

alter table customer
    add `email` varchar(255);

alter table customer
    add `oauth_provider` varchar(255);

alter table customer
    add `oauth_id` varchar(255);

alter table customer
    add `customer_type` ENUM('REGISTER', 'TEMPORARY');
