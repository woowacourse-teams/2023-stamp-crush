import { StampCountOption } from '../types/domain/coupon';
import { Option, RouterPath, TemplateMenu } from '../types/utils';

export const REGEX = {
  number: /^[0-9]+$/,
} as const;

export const TEMPLATE_MENU: Record<string, TemplateMenu> = {
  FRONT_IMAGE: '쿠폰(앞)',
  BACK_IMAGE: '쿠폰(뒤)',
  STAMP: '스탬프',
};

export const PHONE_NUMBER_LENGTH = 13;

export const ENTER_KEY_INDEX = 11;

export const BACK_KEY_INDEX = 9;

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
  { key: 'recentVisitDate', value: '최근방문순' },
];

export const REGISTER_TYPE_OPTION: Option[] = [
  { key: 'all', value: '전체' },
  { key: 'register', value: '회원' },
  { key: 'temporary', value: '임시' },
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

export const PARAMS_ERROR_MESSAGE = '[ERROR] params를 지정해주세요.';

export const DATE_PARSE_OPTION = {
  hasYear: false,
  hasMonth: true,
  hasDay: true,
};

export const INVALID_CAFE_ID = -1;

export const MEGA_BYTE = 1024 ** 2;

export const IMAGE_MAX_SIZE = 5 * MEGA_BYTE;

export const BASE_URL =
  process.env.NODE_ENV === 'development'
    ? process.env.REACT_APP_DEV_URL
    : process.env.REACT_APP_BASE_URL;

export const DEFAULT_CAFE = {
  id: 0,
  name: '',
  introduction: '',
  openTime: '',
  closeTime: '',
  telephoneNumber: '',
  cafeImageUrl: '',
  roadAddress: '',
  detailAddress: '',
};

export const INTRO_LIMITATION = 150;

export const PHONE_NUMBER_REGEX = /[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
export const ID_REGEX = /^[a-zA-Z0-9]+$/;
export const PW_REGEX = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d!@#$%^*+=-]{7,30}$/;
export const FEEDBACK_FORM_LINK = 'https://forms.gle/k2AsZnHQe7CKDBiBA';
