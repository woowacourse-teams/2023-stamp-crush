import { useMutation, useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getReward } from '../../../../../api/get';
import { Reward } from '../../../../../types/domain/reward';
import { RewardName, RewardItemListContainer, RewardItem } from './style';
import { INVALID_CAFE_ID } from '../../../../../constants/magicNumber';
import {
  CustomerIdParams,
  MutateReq,
  RewardIdParams,
  RewardReqBody,
} from '../../../../../types/api/request';
import { patchReward } from '../../../../../api/patch';
import ROUTER_PATH from '../../../../../constants/routerPath';
import Button from '../../../../../components/Button';

interface RewardItemListProps {
  cafeId: number;
  customerId: number;
}
const RewardItemList = ({ cafeId, customerId }: RewardItemListProps) => {
  const navigate = useNavigate();

  // TODO: 내카페의 고객이 아닌 고객의 리워드를 조회할 경우 ErrorMessage를 띄어줌
  const { data: rewardData, status: rewardStatus } = useQuery(
    ['getReward'],
    () => {
      return getReward({ params: { customerId, cafeId } });
    },
    {
      enabled: cafeId !== INVALID_CAFE_ID,
    },
  );

  const { mutate: mutateReward } = useMutation({
    mutationFn: (request: MutateReq<RewardReqBody, RewardIdParams & CustomerIdParams>) => {
      return patchReward(request);
    },
    onSuccess() {
      navigate(ROUTER_PATH.customerList);
    },
    onError() {
      alert('에러가 발생했습니다. 네트워크 상태를 확인해주세요.');
    },
  });

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
