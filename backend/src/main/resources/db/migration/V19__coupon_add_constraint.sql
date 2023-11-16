-- cafe_coupon_design_id에 대한 외래 키 제약 조건 추가
ALTER TABLE coupon
    ADD CONSTRAINT fk_coupon_cafe_coupon_design
        FOREIGN KEY (cafe_coupon_design_id) REFERENCES cafe_coupon_design (id);

-- cafe_policy_id에 대한 외래 키 제약 조건 추가
ALTER TABLE coupon
    ADD CONSTRAINT fk_coupon_cafe_policy
        FOREIGN KEY (cafe_policy_id) REFERENCES cafe_policy (id);
