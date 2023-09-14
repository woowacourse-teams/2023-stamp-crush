export interface RewardHistoryType {
  id: number;
  rewardName: string;
  cafeName: string;
  createdAt: string;
  usedAt: string | null;
}

export interface Reward {
  id: number;
  name: string;
}

export type RewardHistoryDateProperties = Exclude<
  keyof RewardHistoryType,
  'id' | 'rewardName' | 'cafeName'
>;
