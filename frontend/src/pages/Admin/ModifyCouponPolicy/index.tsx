import { useState } from 'react';
import Button from '../../../components/Button';
import { ButtonContainer, StepContainer, StepWrapper } from './style';
import { Spacing } from '../../../style/layout/common';
import Text from '../../../components/Text';
import CreatedType from './CreatedType';
import MaxStampCount from './MaxStampCount';
import ExpiredPeriod from './ExpirePeriod';
import RewardName from './RewardName';
import { MODIFY_STEP_NUMBER } from './common/constant';
import useStep from './hooks/useStep';
import { CouponCreated, StampCountOption } from '../../../types/domain/coupon';
import { Option } from '../../../types/utils';
import { EXPIRE_DATE_NONE } from '../../../constants';

export const EXPIRE_DATE_OPTIONS = [
  {
    key: 'six-month',
    value: '6개월',
  },
  {
    key: 'twelve-month',
    value: '12개월',
  },
  {
    key: 'infinity',
    value: EXPIRE_DATE_NONE,
  },
];

export const STAMP_COUNT_OPTIONS: StampCountOption[] = [
  {
    key: 'eight',
    value: '8개',
  },
  {
    key: 'ten',
    value: '10개',
  },
  {
    key: 'twelve',
    value: '12개',
  },
];

const ModifyCouponPolicy = () => {
  const [createdType, setCreatedType] = useState<CouponCreated>('template');
  const [rewardName, setRewardName] = useState('');
  const [expirePeriod, setExpirePeriod] = useState<Option>({
    key: EXPIRE_DATE_OPTIONS[0].key,
    value: EXPIRE_DATE_OPTIONS[0].value,
  });
  const [stampCount, setStampCount] = useState<Option>({
    key: STAMP_COUNT_OPTIONS[0].key,
    value: STAMP_COUNT_OPTIONS[0].value,
  });
  const { step, movePrevStep, moveNextStep } = useStep(
    createdType,
    rewardName,
    expirePeriod,
    stampCount.value,
  );

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">쿠폰 제작 및 변경</Text>
      <Spacing $size={40} />
      <StepContainer>
        <StepWrapper>
          {(() => {
            switch (step) {
              case MODIFY_STEP_NUMBER.createdType:
                return <CreatedType value={createdType} setValue={setCreatedType} />;
              case MODIFY_STEP_NUMBER.maxStampCount:
                return (
                  <MaxStampCount
                    createdType={createdType}
                    stampCount={stampCount}
                    setStampCount={setStampCount}
                  />
                );
              case MODIFY_STEP_NUMBER.rewardName:
                return <RewardName rewardName={rewardName} setRewardName={setRewardName} />;
              case MODIFY_STEP_NUMBER.expirePeriod:
                return (
                  <ExpiredPeriod expirePeriod={expirePeriod} setExpirePeriod={setExpirePeriod} />
                );
              default:
                return null;
            }
          })()}
        </StepWrapper>
        <ButtonContainer $step={step}>
          {step !== MODIFY_STEP_NUMBER.createdType && (
            <Button variant="secondary" size="medium" onClick={movePrevStep}>
              이전
            </Button>
          )}
          <Button size="medium" onClick={moveNextStep}>
            다음
          </Button>
        </ButtonContainer>
      </StepContainer>
    </>
  );
};

export default ModifyCouponPolicy;
