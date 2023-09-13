import { ROUTER_PATH } from '../../../../constants';
import { CouponCreated } from '../../../../types/domain/coupon';

export const selectRoutePathByCreatedType = (createdType: CouponCreated) => {
  switch (createdType) {
    case 'template':
      return ROUTER_PATH.templateCouponDesign;
    case 'custom':
      return ROUTER_PATH.customCouponDesign;
    default:
      throw new Error('[ERROR]: 쿠폰 생성 타입이 잘못되었습니다.');
  }
};
