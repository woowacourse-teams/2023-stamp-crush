import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { ButtonContainer, StepContainer, StepWrapper } from './style';
import { Spacing } from '../../../style/layout/common';
import Text from '../../../components/Text';
import { EXPIRE_DATE_OPTIONS, ROUTER_PATH, STAMP_COUNT_OPTIONS } from '../../../constants';
import { CouponCreated, Option } from '../../../types';
import CreatedType from './CreatedType';
import MaxStampCount from './MaxStampCount';
import ExpiredPeriod from './ExpirePeriod';
import RewardName from './RewardName';
import { selectRoutePathByCreatedType } from './logic';

const MODIFY_STEP_NUMBER = {
  createdType: 1,
  maxStampCount: 2,
  rewardName: 3,
  expirePeriod: 4,
};

const ModifyCouponPolicy = () => {
  const navigate = useNavigate();
  const [step, setStep] = useState(MODIFY_STEP_NUMBER.createdType);
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

  const navigateNextPage = () => {
    navigate(`${ROUTER_PATH.modifyCouponPolicy}${selectRoutePathByCreatedType(createdType)}`, {
      state: {
        createdType,
        reward: rewardName,
        expirePeriod,
        stampCount: stampCount.value,
      },
    });
  };

  const moveNextStep = () => {
    if (step === MODIFY_STEP_NUMBER.rewardName && !rewardName) {
      alert('리워드를 작성해주세요.');
      return;
    }

    if (step === MODIFY_STEP_NUMBER.expirePeriod) {
      navigateNextPage();
      return;
    }

    setStep((prev) => (prev += 1));
  };

  const movePrevStep = () => {
    setStep((prev) => (prev -= 1));
  };

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
