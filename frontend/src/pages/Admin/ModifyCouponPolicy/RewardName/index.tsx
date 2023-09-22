import { Spacing } from '../../../../style/layout/common';
import Text from '../../../../components/Text';
import { ChangeEvent, Dispatch, SetStateAction } from 'react';
import { Input } from '../../../../components/Input';

interface RewardNameProps {
  rewardName: string;
  setRewardName: Dispatch<SetStateAction<string>>;
}

const RewardName = ({ rewardName, setRewardName }: RewardNameProps) => {
  const handleRewardName = (e: ChangeEvent<HTMLInputElement>) => {
    setRewardName(e.target.value);
  };

  return (
    <>
      <Text variant="subTitle">step3. 어떤 리워드(보상)을 지급하시겠어요?</Text>
      <Spacing $size={24} />
      <Input
        id="create-coupon-reward-input"
        value={rewardName}
        type="text"
        placeholder="ex) 아메리카노, 무료 음료 한잔"
        width="small"
        maxLength={20}
        onChange={handleRewardName}
      />
    </>
  );
};

export default RewardName;
