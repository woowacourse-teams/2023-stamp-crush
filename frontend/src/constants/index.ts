import { TemplateMenu } from '../types/utils';

export const TEMPLATE_MENU: Record<string, TemplateMenu> = {
  FRONT_IMAGE: '쿠폰(앞)',
  BACK_IMAGE: '쿠폰(뒤)',
  STAMP: '스탬프',
};

export const PHONE_NUMBER_LENGTH = 13;

export const ENTER_KEY_INDEX = 11;

export const BACK_KEY_INDEX = 9;

export const EXPIRE_DATE_NONE = '없음';

export const EXPIRE_DATE_MAX = 1200;

export const PARAMS_ERROR_MESSAGE = '[ERROR] params를 지정해주세요.';

export const DATE_PARSE_OPTION = {
  hasYear: false,
  hasMonth: true,
  hasDay: true,
};

export const INVALID_CAFE_ID = -1;

export const MEGA_BYTE = 1024 ** 2;

export const IMAGE_MAX_SIZE = 5 * MEGA_BYTE;

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
