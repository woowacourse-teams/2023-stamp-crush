UPDATE coupon
    INNER JOIN coupon_design ON coupon_design.id = coupon.coupon_design_id
    INNER JOIN cafe_coupon_design ON
    coupon_design.front_image_url = cafe_coupon_design.front_image_url AND
    coupon_design.back_image_url = cafe_coupon_design.back_image_url AND
    coupon_design.stamp_image_url = cafe_coupon_design.stamp_image_url AND
    coupon.cafe_id = cafe_coupon_design.cafe_id
    SET coupon.cafe_coupon_design_id = cafe_coupon_design.id;

UPDATE coupon
    INNER JOIN coupon_policy ON coupon_policy.id = coupon.coupon_policy_id
    INNER JOIN cafe_policy ON
    coupon_policy.max_stamp_count = cafe_policy.max_stamp_count AND
    coupon_policy.reward_name = cafe_policy.reward AND
    coupon_policy.expired_period = cafe_policy.expire_period AND
    coupon.cafe_id = cafe_policy.cafe_id
    SET coupon.cafe_policy_id = cafe_policy.id;
