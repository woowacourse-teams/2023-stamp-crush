export type RouterPath = `/${string}`;

export type NotEmptyArray<T> = T[] & { _brand: 'not empty array' };

export interface Option {
  key: string;
  value: string;
}

export interface Coordinate {
  xCoordinate: number;
  yCoordinate: number;
}

export interface Time {
  hour: string;
  minute: string;
}

export interface DateParseOption {
  hasYear: boolean;
  hasMonth: boolean;
  hasDay: boolean;
}
