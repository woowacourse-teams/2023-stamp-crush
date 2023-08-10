import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { MyRewardRes } from '../../types/api';
import { getMyRewards } from '../../api/get';
import { Reward } from '../../types';
import { RewardCafeName, RewardContainer, RewardDateTitle } from './style';
import { parseStringDateToKorean, sortMapByKey } from '../../utils';

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

export const transformRewardsToMap = (rewards: Reward[]) => {
  const result = new Map<string, Reward[]>();

  concatUsedAt(rewards).forEach((reward) => {
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
  const { data: rewardData, status: rewardStatus } = useRewardQuery(true);

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <>로딩 중입니다.</>;

  const rewardEntries = Array.from(transformRewardsToMap(rewardData.rewards).entries());
  const titleParseOptions = {
    year: false,
    month: true,
    day: true,
  };
  return (
    <>
      <SubHeader title="리워드 사용 내역" />
      <div>
        {rewardEntries.map(([key, rewards]) => (
          <div key={key}>
            <RewardDateTitle>{parseStringDateToKorean(key, titleParseOptions)}</RewardDateTitle>
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
