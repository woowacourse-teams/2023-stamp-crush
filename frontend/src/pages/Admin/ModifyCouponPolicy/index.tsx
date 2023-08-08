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

const ModifyCouponPolicy = () => {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
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
    if (createdType === 'template') {
      navigate(ROUTER_PATH.templateCouponDesign, {
        state: {
          createdType,
          reward: rewardName,
          expirePeriod,
          stampCount: stampCount.value,
        },
      });
    }

    if (createdType === 'custom') {
      navigate(ROUTER_PATH.customCouponDesign, {
        state: {
          createdType,
          reward: rewardName,
          expirePeriod,
          stampCount: stampCount.value,
        },
      });
    }
  };

  const moveNextStep = () => {
    if (step === 3 && !rewardName) {
      alert('리워드를 작성해주세요.');
      return;
    }

    if (step === 4) {
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
              case 1:
                return <CreatedType setValue={setCreatedType} />;
              case 2:
                return (
                  <MaxStampCount
                    createdType={createdType}
                    stampCount={stampCount}
                    setStampCount={setStampCount}
                  />
                );
              case 3:
                return <RewardName rewardName={rewardName} setRewardName={setRewardName} />;
              case 4:
                return (
                  <ExpiredPeriod expirePeriod={expirePeriod} setExpirePeriod={setExpirePeriod} />
                );
              default:
                return null;
            }
          })()}
        </StepWrapper>
        <ButtonContainer $step={step}>
          {step !== 1 && (
            <Button variant="secondary" size="medium" onClick={movePrevStep}>
              이전으로
            </Button>
          )}
          <Button variant="secondary" size="medium" onClick={moveNextStep}>
            다음으로
          </Button>
        </ButtonContainer>
      </StepContainer>
    </>
  );
};

export default ModifyCouponPolicy;
