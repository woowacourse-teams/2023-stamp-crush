ALTER TABLE customer
    ADD deleted BOOLEAN DEFAULT false;

ALTER TABLE coupon
    ADD deleted BOOLEAN DEFAULT false;

ALTER TABLE coupon_design
    ADD deleted BOOLEAN DEFAULT false;

ALTER TABLE coupon_policy
    ADD deleted BOOLEAN DEFAULT false;

ALTER TABLE coupon_stamp_coordinate
    ADD deleted BOOLEAN DEFAULT false;

ALTER TABLE stamp
    ADD deleted BOOLEAN DEFAULT false;
