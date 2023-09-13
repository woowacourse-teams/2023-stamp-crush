ALTER TABLE customer
    MODIFY COLUMN oauth_id bigint;

ALTER TABLE customer DROP COLUMN dtype;

drop table temporary_customer;
drop table register_customer;
