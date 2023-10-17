import { RouterPath } from '../types/utils';

const ROUTER_PATH: Record<string, RouterPath> = {
  intro: '/intro',
  customerList: '/admin',
  adminLogin: '/admin/login',
  adminAuth: '/admin/login/auth/kakao',
  auth: '/login/auth/kakao',
  adminSignUp: '/admin/sign-up',
  enterReward: '/admin/enter-reward',
  enterStamp: '/admin/enter-stamp',
  manageCafe: '/admin/manage-cafe',
  modifyCouponPolicy: '/admin/modify-coupon-policy',
  registerCafe: '/admin/register-cafe',
  earnStamp: '/admin/earn-stamp',
  templateCouponDesign: '/template-coupon-design',
  customCouponDesign: '/custom-coupon-design',
  useReward: '/admin/use-reward',
  couponList: '/',
  login: '/login',
  signup: '/sign-up',
  myPage: '/my-page',
  rewardList: '/reward-list',
  rewardHistory: '/reward-history',
  stampHistory: '/stamp-history',
  inputPhoneNumber: '/input-phone-number',
  customerCancellation: '/cancellation',
  customerSetting: '/customer-setting',
  greeting: '/greeting',
} as const;

export default ROUTER_PATH;
