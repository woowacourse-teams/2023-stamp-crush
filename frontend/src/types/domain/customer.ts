export interface Customer {
  id: number;
  nickname: string;
  stampCount: number;
  maxStampCount: number;
  rewardCount: number;
  visitCount: number;
  firstVisitDate: string;
  isRegistered: boolean;
  recentVisitDate: string;
}

export interface CustomerPhoneNumber {
  id: number;
  nickname: string;
  phoneNumber: string;
}

export interface CustomerProfile {
  id: number;
  nickname: string;
  phoneNumber: string | null;
  email: string | null;
}

export interface CustomerRegisterType extends CustomerPhoneNumber {
  registerType: RegisterType;
}

export type RegisterType = 'temporary' | 'register';
