import { Reward } from '../../../../../types/domain/reward';
import { RewardName, RewardItemListContainer, RewardItem } from './style';
import Button from '../../../../../components/Button';
import useReward from '../../hooks/useReward';
import useMutateReward from '../../hooks/useMutateReward';

interface RewardItemListProps {
  cafeId: number;
  customerId: number;
}
const RewardItemList = ({ cafeId, customerId }: RewardItemListProps) => {
  // TODO: 내카페의 고객이 아닌 고객의 리워드를 조회할 경우 ErrorMessage를 띄어줌
  const { data: rewardData, status: rewardStatus } = useReward(cafeId, customerId);
  const { mutate: mutateReward } = useMutateReward();

  const activateRewardButton = (rewardId: number) => {
    mutateReward({
      params: {
        rewardId,
        customerId,
      },
      body: {
        used: true,
        cafeId,
      },
    });
  };

  if (rewardStatus === 'error') {
    return <div>불러오는 중 에러가 발생했습니다. 다시 시도해주세요.</div>;
  }

  if (rewardStatus === 'loading') {
    return <div>고객 정보 불러오는 중...</div>;
  }

  if (rewardData.rewards.length === 0) {
    return <p>보유한 리워드가 없습니다.</p>;
  }

  return (
    <RewardItemListContainer>
      {rewardData.rewards.map(({ id, name }: Reward) => (
        <RewardItem key={id}>
          <RewardName>{name}</RewardName>
          <Button onClick={() => activateRewardButton(id)}>사용</Button>
        </RewardItem>
      ))}
    </RewardItemListContainer>
  );
};

export default RewardItemList;
