ALTER TABLE stamp
    ADD deleted bit(1) DEFAULT false;

ALTER TABLE favorites
    ADD deleted bit(1) DEFAULT false;

ALTER TABLE reward
    ADD deleted bit(1) DEFAULT false;
