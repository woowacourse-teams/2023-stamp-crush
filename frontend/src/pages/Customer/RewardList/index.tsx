import { useQuery } from '@tanstack/react-query';
import { getMyRewards } from '../../../api/get';
import SubHeader from '../../../components/Header/SubHeader';
import { CafeName, EmptyList, RewardContainer, RewardName, RewardWrapper } from './style';
import CustomerLoadingSpinner from '../../../components/LoadingSpinner/CustomerLoadingSpinner';

const RewardList = () => {
  const { data: rewardData, status: rewardStatus } = useQuery(['myRewards'], {
    queryFn: () => getMyRewards({ params: { used: false } }),
  });

  if (rewardStatus === 'error') return <>에러가 발생했습니다.</>;
  if (rewardStatus === 'loading') return <CustomerLoadingSpinner />;

  const { rewards } = rewardData;

  if (rewards.length === 0)
    return (
      <>
        <SubHeader title="내 리워드" />
        <EmptyList>
          보유한 리워드가 없어요 🥲 <br /> 스탬프를 차곡차곡 쌓아 리워드를 받아보세요!
        </EmptyList>
      </>
    );

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
