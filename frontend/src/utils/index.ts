import { EXPIRE_DATE_MAX, EXPIRE_DATE_NONE, IMAGE_MAX_SIZE } from '../constants';
import { ExpireDateOptionValue, StampCountOptionValue } from '../types/domain/coupon';
import { Time, DateParseOption, NotEmptyArray } from '../types/utils';

export const formatDate = (dateString: string) => {
  const dateArray = dateString.split(':');

  const year = parseInt(dateArray[0]);
  const month = parseInt(dateArray[1]);
  const day = parseInt(dateArray[2]);

  const date = new Date(year, month, day);

  const formattedDate = `${date.getFullYear()}년 ${date.getMonth()}월 ${date.getDate()}일`;

  return formattedDate;
};

export const parseExpireDate = (value: ExpireDateOptionValue) => {
  return value === EXPIRE_DATE_NONE ? EXPIRE_DATE_MAX : +value.replaceAll('개월', '');
};

export const parseStampCount = (value: StampCountOptionValue) => {
  return +value.replaceAll('개', '');
};

export const parseTime = (value: Time) => {
  return value.hour + ':' + value.minute;
};

export const parsePhoneNumber = (phoneNumber: string | undefined) => {
  if (!phoneNumber) return '';

  const parsedPhoneNumber = phoneNumber.replace(/[^0-9]/g, '');

  if (parsedPhoneNumber.startsWith('02')) {
    return parsedPhoneNumber.replace(/^(\d{2})(\d{3,4})(\d{4})$/, '$1-$2-$3');
  } else {
    return parsedPhoneNumber.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
  }
};

export const isEmptyData = (data: string | null) => {
  if (data === null) return true;

  return data.length === 0;
};

export const addGoogleProxyUrl = (url: string) =>
  'https://images1-focus-opensocial.googleusercontent.com/gadgets/proxy?container=focus&refresh=2592000&url=' +
  encodeURIComponent(url);

export const sortMapByKey = <T>(map: Map<string, T>) => {
  const sortedMap = new Map(
    [...map.entries()].sort((a, b) => {
      return a[0].localeCompare(b[0]);
    }),
  );
  return sortedMap;
};

export const parseStringDateToKorean = (input: string, options: DateParseOption) => {
  const year = options.hasYear ? `${input.slice(0, 4)}년` : '';
  const month = options.hasMonth ? `${input.slice(4, 6)}월` : '';
  const day = options.hasDay ? `${input.slice(6, 8)}일` : '';

  return `${year} ${month} ${day}`.trim();
};

export const transformEntries = <T extends NonNullable<unknown>, U extends keyof T>(
  arr: T[],
  propertyName: U,
  transformCallback: (target: T, propertyName: U) => T,
) => {
  return arr.map((element) => transformCallback(element, propertyName));
};

export const isLargeThanBoundarySize = (fileSize: number) => {
  return fileSize > IMAGE_MAX_SIZE;
};

export const getLocalStorage = <T>(key: string, defaultValue: T): T => {
  const data = localStorage.getItem(key);
  if (!data) return defaultValue;

  try {
    return JSON.parse(data);
  } catch (e) {
    console.error(`[ERROR] ${key}값의 LocalStorage data 파싱 과정에서 오류가 발생했습니다.`);
    return defaultValue;
  }
};

export const removeHyphen = (value: string) => value.replaceAll('-', '');

export const isNotEmptyArray = <T>(array: T[]): array is NotEmptyArray<T> => {
  return array.length > 0;
};

export const splitTime = (timeString: string) => {
  const [hour, minute] = timeString.split(':');

  return { hour, minute };
};
