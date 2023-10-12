import { useMutation, useQuery } from '@tanstack/react-query';
import Button from '../../../components/Button';
import { RewardContainer, RewardContent, RewardItemContainer, RewardItemWrapper } from './style';
import Text from '../../../components/Text';
import { useLocation, useNavigate } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import { getReward } from '../../../api/get';
import { patchReward } from '../../../api/patch';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import {
  MutateReq,
  RewardReqBody,
  RewardIdParams,
  CustomerIdParams,
} from '../../../types/api/request';
import { Reward } from '../../../types/domain/reward';
import ROUTER_PATH from '../../../constants/routerPath';
import { INVALID_CAFE_ID } from '../../../constants/magicNumber';

const RewardPage = () => {
  const cafeId = useRedirectRegisterPage();
  const location = useLocation();
  const navigate = useNavigate();

  // TODO: 내카페의 고객이 아닌 고객의 리워드를 조회할 경우 ErrorMessage를 띄어줌
  const { data: rewardData, status: rewardStatus } = useQuery(
    ['getReward'],
    () => {
      return getReward({ params: { customerId: location.state.id, cafeId } });
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

  if (rewardStatus === 'error') {
    return <div>불러오는 중 에러가 발생했습니다. 다시 시도해주세요.</div>;
  }

  if (rewardStatus === 'loading') {
    return <div>고객 정보 불러오는 중...</div>;
  }

  const activateRewardButton = (rewardId: number) => {
    mutateReward({
      params: {
        rewardId,
        customerId: location.state.id,
      },
      body: {
        used: true,
        cafeId,
      },
    });
  };

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">리워드 사용</Text>
      <Spacing $size={36} />
      <RewardContainer>
        <Text variant="subTitle">{location.state.nickname} 고객님</Text>
        <Spacing $size={72} />
        <Text variant="subTitle">보유 리워드 내역</Text>
        <Spacing $size={42} />
        <RewardItemContainer>
          {rewardData.rewards.length ? (
            rewardData.rewards.map(({ id, name }: Reward) => (
              <RewardItemWrapper key={id}>
                <RewardContent>{name}</RewardContent>
                <Button onClick={() => activateRewardButton(id)}>사용</Button>
              </RewardItemWrapper>
            ))
          ) : (
            <p>보유한 리워드가 없습니다.</p>
          )}
        </RewardItemContainer>
      </RewardContainer>
    </>
  );
};

export default RewardPage;
