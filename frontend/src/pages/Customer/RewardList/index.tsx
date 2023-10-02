import { useQuery } from '@tanstack/react-query';
import { getMyRewards } from '../../../api/get';
import SubHeader from '../../../components/Header/SubHeader';
import { CafeName, RewardContainer, RewardName, RewardWrapper } from './style';
import useCustomerRedirectRegisterPage from '../../../hooks/useCustomerRedirectRegisterPage';

const RewardList = () => {
  const { data: rewardData, status: rewardStatus } = useQuery(['myRewards'], {
    queryFn: () => getMyRewards({ params: { used: false } }),
  });
  useCustomerRedirectRegisterPage();

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <>로딩 중입니다.</>;

  const { rewards } = rewardData;

  return (
    <>
      <SubHeader title="내 리워드" />
      <RewardContainer>
        {rewards.map((reward) => (
          <RewardWrapper key={reward.id}>
            <CafeName>{reward.cafeName}</CafeName>
            <RewardName>{reward.rewardName}</RewardName>
          </RewardWrapper>
        ))}
      </RewardContainer>
    </>
  );
};

export default RewardList;
