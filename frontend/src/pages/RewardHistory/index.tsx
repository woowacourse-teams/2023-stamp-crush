import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { getMyRewards } from '../../api/get';
import { Reward } from '../../types';
import { RewardCafeName, RewardHistoryItem, RewardDateTitle } from './style';
import { parseStringDateToKorean, sortMapByKey } from '../../utils';

// TODO: 어떻게 분리할 것인지? 생각해보기

export function transfromEntries<T extends NonNullable<unknown>, U extends string>(
  arr: T[],
  propertyName: U,
  transformCallback: (target: T, propertyName: U) => T,
) {
  return arr.map((element) => transformCallback(element, propertyName));
}

export const concatDate = (
  reward: Reward,
  propertyName: Exclude<keyof Reward, 'id' | 'rewardName' | 'cafeName'>,
) => {
  if (!reward[propertyName]) return reward;
  return { ...reward, [propertyName]: reward[propertyName]?.replaceAll(':', '') };
};

export const transformRewardsToMap = (rewards: Reward[]): Map<string, Reward[]> => {
  const result = new Map<string, Reward[]>();

  transfromEntries(rewards, 'usedAt', concatDate).forEach((reward) => {
    if (!reward.usedAt) return;
    const existRewards = result.has(reward.usedAt) ? (result.get(reward.usedAt) as Reward[]) : [];

    result.set(reward.usedAt, [
      ...existRewards,
      {
        ...reward,
      },
    ]);
  });
  return sortMapByKey(result);
};

const RewardHistory = () => {
  const { data: rewardData, status: rewardStatus } = useQuery(['myRewards'], {
    queryFn: () => getMyRewards({ params: { used: true } }),
  });

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <>로딩 중입니다.</>;

  const rewardEntries = Array.from(transformRewardsToMap(rewardData.rewards).entries());
  const dateParseOption = {
    hasYear: false,
    hasMonth: true,
    hasDay: true,
  };
  return (
    <>
      <SubHeader title="리워드 사용 내역" />
      <div>
        {rewardEntries.map(([key, rewards]) => (
          <div key={key}>
            <RewardDateTitle>{parseStringDateToKorean(key, dateParseOption)}</RewardDateTitle>
            <ul key={key}>
              {rewards.map((reward) => (
                <RewardHistoryItem key={reward.id}>
                  <RewardCafeName>{reward.cafeName}</RewardCafeName>
                  <span>{reward.rewardName}</span>
                </RewardHistoryItem>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </>
  );
};

export default RewardHistory;
