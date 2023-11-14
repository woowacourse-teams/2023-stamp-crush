alter table coupon
    add constraint cafe_coupon_design_fk
        foreign key (cafe_coupon_design_id)
            references cafe_coupon_design;

alter table coupon
    add constraint cafe_policy_fk
        foreign key (cafe_policy_id)
            references cafe_policy;
