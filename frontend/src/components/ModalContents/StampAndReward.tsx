import {
  ContentContainer,
  Divider,
  RewardContainer,
  RewardContent,
  RewardItemWrapper,
  SectionTitle,
  StampContainer,
  StampIndicator,
  Title,
} from './StampAndReward.style';
import Button from '../Button';

export interface RewardInfo {
  id: number;
  name: string;
}

interface StampAndRewardProps {
  nickname: string;
  stampCount: number;
  goalStamp: number;
  rewards: RewardInfo[];
}

const StampAndReward = ({ nickname, stampCount, goalStamp, rewards }: StampAndRewardProps) => {
  const earnStamp = () => {
    alert('스탬프가 적립되었습니다.');
  };

  const activateReward = (name: string) => {
    alert(name + '을(를) 사용했습니다.');
  };

  return (
    <>
      <Title>{`${nickname} 고객님`}</Title>
      <ContentContainer>
        <StampContainer>
          <SectionTitle>스탬프 추가 적립</SectionTitle>
          <StampIndicator>{`현재 스탬프 개수: ${stampCount}/${goalStamp}`}</StampIndicator>
          <Button onClick={earnStamp}>적립하기</Button>
        </StampContainer>
        <Divider />
        <RewardContainer>
          <SectionTitle>보유 리워드 내역</SectionTitle>
          {rewards.map(({ id, name }) => {
            return (
              <RewardItemWrapper key={id}>
                <RewardContent>{name}</RewardContent>
                <Button onClick={() => activateReward(name)}>사용</Button>
              </RewardItemWrapper>
            );
          })}
        </RewardContainer>
      </ContentContainer>
    </>
  );
};

export default StampAndReward;
