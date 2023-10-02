import { Spacing } from '../../../../style/layout/common';
import FlippedCoupon, {
  FlippedCouponProps,
} from '../../../Customer/CouponList/components/FlippedCoupon';
import { CustomCouponLabel } from '../CustomCouponSection/style';

type CouponPreviewSectionProps = FlippedCouponProps;

const CouponPreviewSection = (props: CouponPreviewSectionProps) => {
  return (
    <div>
      <CustomCouponLabel>완성된 쿠폰 미리보기</CustomCouponLabel>
      <Spacing $size={20} />
      <FlippedCoupon {...props} />
    </div>
  );
};

export default CouponPreviewSection;
