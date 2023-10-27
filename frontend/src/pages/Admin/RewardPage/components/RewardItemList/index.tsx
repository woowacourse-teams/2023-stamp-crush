import { Reward } from '../../../../../types/domain/reward';
import { RewardName, RewardItemListContainer, RewardItem } from './style';
import Button from '../../../../../components/Button';
import useReward from '../../hooks/useReward';
import useMutateReward from '../../hooks/useMutateReward';
import { useNavigate } from 'react-router-dom';

interface RewardItemListProps {
  cafeId: number;
  customerId: number;
}

const isNotFoundUser = (error: Error) => {
  return error.message === '404';
};
const RewardItemList = ({ cafeId, customerId }: RewardItemListProps) => {
  const navigate = useNavigate();
  const reward = useReward(cafeId, customerId);
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

  const refreshReward = () => {
    reward.refetch();
  };

  const goBackButton = () => {
    navigate(-1);
  };

  if (reward.status === 'error') {
    return (
      <RewardItemListContainer>
        {isNotFoundUser(reward.error) ? (
          <>
            <div>고객을 카페에서 찾을 수 없습니다.</div>
            <Button onClick={goBackButton}>뒤로가기</Button>
          </>
        ) : (
          <>
            <div>에러가 발생했습니다.</div>
            <Button onClick={refreshReward}>다시 불러오기</Button>
          </>
        )}
      </RewardItemListContainer>
    );
  }

  if (reward.status === 'loading') {
    return (
      <RewardItemListContainer>
        <div>고객 정보 불러오는 중...</div>
      </RewardItemListContainer>
    );
  }

  if (reward.data.rewards.length === 0) {
    return <RewardItemListContainer>보유한 리워드가 없습니다.</RewardItemListContainer>;
  }

  return (
    <RewardItemListContainer>
      {reward.data.rewards.map(({ id, name }: Reward) => (
        <RewardItem key={id}>
          <RewardName>{name}</RewardName>
          <Button onClick={() => activateRewardButton(id)}>사용</Button>
        </RewardItem>
      ))}
    </RewardItemListContainer>
  );
};

export default RewardItemList;
