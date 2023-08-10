import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { MyRewardRes } from '../../types/api';
import { getMyRewards } from '../../api/get';
import { Reward } from '../../types';
import { RewardCafeName, RewardContainer, RewardDateTitle } from './style';

export const useRewardQuery = (used: boolean) => {
  const result = useQuery<MyRewardRes>(['myRewards', used], {
    queryFn: () => getMyRewards(used),
  });

  return result;
};

export const concatUsedAt = (rewards: Reward[]) => {
  return rewards.map((reward) => {
    if (!reward.usedAt) return reward;
    const [year, month, day] = reward.usedAt.split(':');
    return { ...reward, usedAt: `${year}${month}${day}` };
  });
};

// TODO: 타입 구체화

export const transformRewardsByUsedAt = (rewards: Reward[]) => {
  const result = new Map<string, Reward[]>();

  const copiedRewards = concatUsedAt(rewards);

  copiedRewards.forEach((reward) => {
    if (!reward.usedAt) return;

    if (result.has(reward.usedAt)) {
      const existEntrys = result.get(reward.usedAt) as Reward[];
      result.set(reward.usedAt, [
        ...existEntrys,
        {
          id: reward.id,
          cafeName: reward.cafeName,
          rewardName: reward.rewardName,
          createdAt: reward.createdAt,
          usedAt: reward.usedAt,
        },
      ]);
    } else {
      result.set(reward.usedAt, [
        {
          id: reward.id,
          cafeName: reward.cafeName,
          rewardName: reward.rewardName,
          createdAt: reward.createdAt,
          usedAt: reward.usedAt,
        },
      ]);
    }
  });
  return result;
};

function sortMapByKey(map: Map<string, Reward[]>) {
  const sortedMap = new Map(
    [...map.entries()].sort((a, b) => {
      return a[0].localeCompare(b[0]);
    }),
  );
  return sortedMap;
}

const RewardHistory = () => {
  const { data: rewardData, status: rewardStatus } = useRewardQuery(true);

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <>로딩 중입니다.</>;

  const transformRewardMap = Array.from(
    sortMapByKey(transformRewardsByUsedAt(rewardData.rewards)).entries(),
  );

  return (
    <>
      <SubHeader title="리워드 사용 내역" />
      <div>
        {transformRewardMap.map(([key, rewards]) => (
          <div key={key}>
            <RewardDateTitle>{key}</RewardDateTitle>
            {rewards.map((reward) => (
              <RewardContainer key={reward.id}>
                <RewardCafeName>{reward.cafeName}</RewardCafeName>
                <span>{reward.rewardName}</span>
              </RewardContainer>
            ))}
          </div>
        ))}
      </div>
    </>
  );
};

export default RewardHistory;
