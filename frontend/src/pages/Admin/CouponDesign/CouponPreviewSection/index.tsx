import { Spacing } from '../../../../style/layout/common';
import FlippedCoupon, {
  FlippedCouponProps,
} from '../../../Customer/CouponList/components/FlippedCoupon';

type CouponPreviewSectionProps = FlippedCouponProps;

const CouponPreviewSection = (props: CouponPreviewSectionProps) => {
  return (
    <div>
      <label>쿠폰 미리보기</label>
      <Spacing $size={12} />
      <FlippedCoupon {...props} />
    </div>
  );
};

export default CouponPreviewSection;
