import { useMutation, useQuery } from '@tanstack/react-query';
import Button from '../../../components/Button';
import {
  RewardContainer,
  RewardContent,
  RewardItemContainer,
  RewardItemWrapper,
} from '../../../components/ModalContents/StampAndReward.style';
import Text from '../../../components/Text';
import { useLocation, useNavigate } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import { BASE_URL } from '../../..';

const getCustomer = async (phoneNumber: string) => {
  const response = await fetch(`${BASE_URL}/customers?phone-number=${phoneNumber}`);
  if (!response.ok) {
    throw new Error();
  }

  const body = await response.json();
  return body.customer;
};

const getReward = async (customerId: number | undefined, cafeId: number) => {
  if (!customerId) {
    throw new Error('잘못된 요청입니다.');
  }
  const response = await fetch(
    `${BASE_URL}/customers/${customerId}/rewards?cafeId=${cafeId}&used=${false}`,
  );

  if (!response.ok) {
    throw new Error();
  }

  const body = await response.json();
  return body.rewards;
};

const patchReward = async (customerId: number, rewardId: number) => {
  const response = await fetch(`${BASE_URL}/customers/${customerId}/rewards/${rewardId}`, {
    method: 'PATCH',
    body: JSON.stringify({
      cafeId: 1,
      used: true,
    }),
  });

  if (!response.ok) {
    throw new Error();
  }
};

// TODO: 추후 완전한 타입이 만들어지면 유틸리티 타입으로 변경해야함.
interface CustomerDto {
  id: number;
  nickname: string;
  phoneNumber: string;
}

interface RewardDto {
  id: number;
  name: string;
}

const RewardPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  // TODO: 로케이션 state 적용
  const phoneNumber = '01011112222';
  const { data: customer } = useQuery(['getCustomer', phoneNumber], () => getCustomer(phoneNumber));
  const { data: rewards, status } = useQuery<RewardDto[]>(
    ['getReward', customer],
    // TODO: cafeId 전역으로 받아오기
    () => getReward(customer[0].id, 1),
    {
      enabled: !!customer,
    },
  );
  const { mutate: mutateReward } = useMutation({
    mutationFn: (rewardId: number) => patchReward(customer[0].id, rewardId),
    onSuccess() {
      navigate('/admin');
    },
    onError() {
      alert('에러가 발생했습니다. 네트워크 상태를 확인해주세요.');
    },
  });

  const activateRewardButton = (name: string, rewardId: number) => {
    mutateReward(rewardId);
  };

  if (status === 'error') {
    return <div>불러오는 중 에러가 발생했습니다. 다시 시도해주세요.</div>;
  }

  if (status === 'loading') {
    return <div>고객 정보 불러오는 중...</div>;
  }

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">리워드 사용</Text>
      <Spacing $size={36} />
      <RewardContainer>
        <Text variant="pageTitle">{customer[0].nickname}고객님</Text>
        <Spacing $size={72} />
        <Text variant="subTitle">보유 리워드 내역</Text>
        <Spacing $size={42} />
        <RewardItemContainer>
          {rewards.length ? (
            rewards.map(({ id, name }) => (
              <RewardItemWrapper key={id}>
                <RewardContent>{name}</RewardContent>
                <Button onClick={() => activateRewardButton(name, id)}>사용</Button>
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
