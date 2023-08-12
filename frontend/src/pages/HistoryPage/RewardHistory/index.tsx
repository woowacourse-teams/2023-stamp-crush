import { useQuery } from '@tanstack/react-query';
import { getMyRewards } from '../../../api/get';
import { CafeName, HistoryItem, DateTitle, HistoryList } from '../style';
import { parseStringDateToKorean, sortMapByKey, transformEntries } from '../../../utils';
import { RewardHistoryType } from '../../../types';
import { DATE_PARSE_OPTION } from '../../../constants';
import HistoryPage from '../HistoryPage';

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
  const title = '리워드 사용 내역';

  if (rewardStatus === 'loading') {
    return <HistoryPage title={title}>로딩중입니다..</HistoryPage>;
  }
  if (rewardStatus === 'error') {
    return <HistoryPage title={title}>에러가 발생했습니다. 잠시 후 다시시도해주세요.</HistoryPage>;
  }

  const rewardEntries = Array.from(transformRewardsToMap(rewardData.rewards, 'usedAt').entries());

  return (
    <HistoryPage title="리워드 사용 내역">
      <div>
        {rewardEntries.map(([key, rewards]) => (
          <div key={key}>
            <DateTitle>{parseStringDateToKorean(key, DATE_PARSE_OPTION)}</DateTitle>
            <HistoryList key={key}>
              {rewards.map((reward) => (
                <HistoryItem key={reward.id}>
                  <CafeName>{reward.cafeName}</CafeName>
                  <span>{reward.rewardName}</span>
                </HistoryItem>
              ))}
            </HistoryList>
          </div>
        ))}
      </div>
    </HistoryPage>
  );
};

export default RewardHistoryPage;
