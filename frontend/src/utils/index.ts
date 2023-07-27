import { Time } from '../pages/Admin/ManageCafe';

export const formatDate = (dateString: string) => {
  const dateArray = dateString.split(':');

  const year = parseInt(dateArray[0]);
  const month = parseInt(dateArray[1]);
  const day = parseInt(dateArray[2]);

  const date = new Date(year, month, day);

  const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;

  return formattedDate;
};

export const parseStampCount = (value: string) => {
  return +value.replaceAll('개', '');
};

export const parseTime = (value: Time) => {
  return value.hour + ':' + value.minute;
};

export const parsePhoneNumber = (phoneNumber: string | undefined) => {
  if (!phoneNumber) return;
  const number = phoneNumber.replace(/\D/g, ''); // Remove all non-numeric characters

  if (number.startsWith('02')) {
    return number.length <= 2
      ? number
      : number.length <= 6
      ? number.substring(0, 2) + '-' + number.substring(2)
      : number.length <= 9
      ? number.substring(0, 2) + '-' + number.substring(2, 5) + '-' + number.substring(5)
      : number.substring(0, 2) + '-' + number.substring(2, 6) + '-' + number.substring(6, 10);
  } else {
    return number.length < 4
      ? number
      : number.length < 7
      ? number.substring(0, 3) + '-' + number.substring(3)
      : number.substring(0, 3) + '-' + number.substring(3, 7) + '-' + number.substring(7, 11);
  }
};
