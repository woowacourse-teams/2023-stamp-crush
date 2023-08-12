import { StampCountOption, RouterPath } from '../types';

export const REGEX = {
  number: /^[0-9]+$/,
} as const;

export const TEMPLATE_MENU = {
  FRONT_IMAGE: '쿠폰(앞)',
  BACK_IMAGE: '쿠폰(뒤)',
  STAMP: '스탬프',
};

export const PHONE_NUMBER_LENGTH = 13;

export const TEMPLATE_OPTIONS = [
  {
    key: 'coupon-front',
    value: TEMPLATE_MENU.FRONT_IMAGE,
  },
  {
    key: 'coupon-back',
    value: TEMPLATE_MENU.BACK_IMAGE,
  },
  {
    key: 'stamp',
    value: TEMPLATE_MENU.STAMP,
  },
];

export const CUSTOMERS_ORDER_OPTIONS = [
  {
    key: 'stampCount',
    value: '스탬프순',
  },
  {
    key: 'rewardCount',
    value: '리워드순',
  },
  {
    key: 'visitCount',
    value: '방문횟수순',
  },
];

export const STAMP_COUNT_OPTIONS: StampCountOption[] = [
  {
    key: 'eight',
    value: '8개',
  },
  {
    key: 'ten',
    value: '10개',
  },
  {
    key: 'twelve',
    value: '12개',
  },
];

export const EXPIRE_DATE_NONE = '없음';

export const EXPIRE_DATE_MAX = 1200;

export const STAMP_COUNT_CUSTOM_OPTIONS = [
  {
    key: 'eight',
    value: '8개',
  },
  {
    key: 'nine',
    value: '9개',
  },
  {
    key: 'ten',
    value: '10개',
  },
  {
    key: 'eleven',
    value: '11개',
  },
  {
    key: 'twelve',
    value: '12개',
  },
];

export const EXPIRE_DATE_OPTIONS = [
  {
    key: 'six-month',
    value: '6개월',
  },
  {
    key: 'twelve-month',
    value: '12개월',
  },
  {
    key: 'infinity',
    value: EXPIRE_DATE_NONE,
  },
];

export const ROUTER_PATH: Record<string, RouterPath> = {
  customerList: '/admin',
  adminLogin: '/admin/login',
  adminSignup: '/admin/sign-up',
  enterReward: '/admin/enter-reward',
  enterStamp: '/admin/enter-stamp',
  manageCafe: '/admin/manage-cafe',
  modifyCouponPolicy: '/admin/modify-coupon-policy',
  registerCafe: '/admin/register-cafe',
  earnStamp: '/admin/earn-stamp',
  selectCoupon: '/admin/select-coupon',
  templateCouponDesign: '/template-coupon-design',
  customCouponDesign: '/custom-coupon-design',
  useReward: '/admin/useReward',
  couponList: '/',
  login: '/login',
  signup: '/sign-up',
  myPage: '/my-page',
  rewardList: '/reward-list',
  rewardHistory: '/reward-history',
  stampHistory: '/stamp-history',
} as const;

export const PARAMS_ERROR_MESSAGE = '[ERROR] params를 지정해주세요.';

export const DATE_PARSE_OPTION = {
  hasYear: false,
  hasMonth: true,
  hasDay: true,
};
