import { ParseDateOption, Time } from '../types';

export const formatDate = (dateString: string) => {
  const dateArray = dateString.split(':');

  const year = parseInt(dateArray[0]);
  const month = parseInt(dateArray[1]);
  const day = parseInt(dateArray[2]);

  const date = new Date(year, month, day);

  const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;

  return formattedDate;
};

export const parseExpireDate = (value: string) => {
  return +value.replaceAll('개월', '');
};

export const parseStampCount = (value: string) => {
  return +value.replaceAll('개', '');
};

export const parseTime = (value: Time) => {
  return value.hour + ':' + value.minute;
};

export const parsePhoneNumber = (phoneNumber: string | undefined) => {
  if (!phoneNumber) return;

  const parsedPhoneNumber = phoneNumber.replace(/[^0-9]/g, '');

  if (parsedPhoneNumber.startsWith('02')) {
    return parsedPhoneNumber.replace(/^(\d{2})(\d{3,4})(\d{4})$/, '$1-$2-$3');
  } else {
    return parsedPhoneNumber.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
  }
};

export const isEmptyData = (data: string | undefined) => {
  if (data === undefined) return true;

  return data.length === 0;
};

export const addGoogleProxyUrl = (url: string) =>
  'https://images1-focus-opensocial.googleusercontent.com/gadgets/proxy?container=focus&refresh=2592000&url=' +
  encodeURIComponent(url);

export const sortMapByKey = <V, T extends Map<string, V>>(map: T): T => {
  const sortedMap = new Map<string, V>(
    [...map.entries()].sort((a, b) => {
      return a[0].localeCompare(b[0]);
    }),
  );
  return sortedMap as T;
};

export const parseStringDateToKorean = (input: string, options: ParseDateOption) => {
  const year = options.hasYear ? `${input.slice(0, 4)}년` : '';
  const month = options.hasMonth ? `${input.slice(4, 6)}월` : '';
  const day = options.hasDay ? `${input.slice(6, 8)}일` : '';

  return `${year} ${month} ${day}`.trim();
};
