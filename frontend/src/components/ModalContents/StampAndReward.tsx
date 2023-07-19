import {
  ContentContainer,
  Divider,
  RewardContainer,
  RewardContent,
  RewardItemContainer,
  RewardItemWrapper,
  SectionTitle,
  StampContainer,
  StampIndicator,
  Title,
} from './StampAndReward.style';
import Button from '../Button';
import Stepper from '../Stepper';
import { useState } from 'react';

export interface RewardInfo {
  id: number;
  name: string;
}

interface StampAndRewardProps {
  nickname?: string;
  stampCount?: number;
  goalStamp?: number;
  rewards?: RewardInfo[];
}

const StampAndReward = ({
  nickname = '',
  stampCount = 0,
  goalStamp = 10,
  rewards = [],
}: StampAndRewardProps) => {
  const [value, setValue] = useState(1);

  const earnStamp = () => {
    alert(value + '개의 스탬프가 적립되었습니다.');
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
          <Stepper value={value} setValue={setValue} minValue={1} />
          <Button onClick={earnStamp}>적립하기</Button>
        </StampContainer>
        <Divider />
        <RewardContainer>
          <SectionTitle>보유 리워드 내역</SectionTitle>
          <RewardItemContainer>
            {rewards.length ? (
              rewards.map(({ id, name }) => {
                return (
                  <RewardItemWrapper key={id}>
                    <RewardContent>{name}</RewardContent>
                    <Button onClick={() => activateReward(name)}>사용</Button>
                  </RewardItemWrapper>
                );
              })
            ) : (
              <p>보유한 리워드가 없습니다.</p>
            )}
          </RewardItemContainer>
        </RewardContainer>
      </ContentContainer>
    </>
  );
};

export default StampAndReward;
