import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { getMyRewards } from '../../api/get';
import { RewardCafeName, RewardHistoryItem, RewardDateTitle } from './style';
import { parseStringDateToKorean, sortMapByKey, transformEntries } from '../../utils';
import { RewardHistoryType } from '../../types';

type RewardHistoryDatePropertys = Exclude<
  keyof RewardHistoryType,
  'id' | 'rewardName' | 'cafeName'
>;

export function concatHistoryDate(
  reward: RewardHistoryType,
  propertyName: RewardHistoryDatePropertys,
) {
  const target = reward[propertyName];
  if (!target) return reward;
  return { ...reward, [propertyName]: target.replaceAll(/[:\s]/g, '') };
}

// TODO: 함수명 변경
export function transformRewardsToMap(
  rewards: RewardHistoryType[],
  propertyName: RewardHistoryDatePropertys,
): Map<string, RewardHistoryType[]> {
  const result = new Map<string, RewardHistoryType[]>();

  transformEntries(rewards, propertyName, concatHistoryDate).forEach((reward) => {
    const target = reward[propertyName];
    if (!target) return;
    const existRewards = result.has(target) ? (result.get(target) as RewardHistoryType[]) : [];

    result.set(target, [
      ...existRewards,
      {
        ...reward,
      },
    ]);
  });

  return sortMapByKey(result);
}

const RewardHistoryPage = () => {
  const { data: rewardData, status: rewardStatus } = useQuery(['myRewards'], {
    queryFn: () => getMyRewards({ params: { used: true } }),
  });

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <>로딩 중입니다.</>;

  const rewardEntries = Array.from(transformRewardsToMap(rewardData.rewards, 'usedAt').entries());
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

export default RewardHistoryPage;
