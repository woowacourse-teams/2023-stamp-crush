alter table coupon
    add `cafe_policy_id` bigint;

alter table coupon
    add `cafe_coupon_design_id` bigint;

alter table if exists coupon
    add constraint FKcj366alvrsl0a8x69g9bk2idt
    foreign key (cafe_coupon_design_id)
    references cafe_coupon_design;

alter table if exists coupon
    add constraint FKkjxioygsq04xf882joj05gqkq
    foreign key (cafe_policy_id)
    references cafe_policy;
