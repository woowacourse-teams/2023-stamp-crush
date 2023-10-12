import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { CouponCreated } from '../../../../types/domain/coupon';
import { Option } from '../../../../types/utils';
import ROUTER_PATH from '../../../../constants/routerPath';
import { MODIFY_STEP_NUMBER } from '..';

const selectRoutePathByCreatedType = (createdType: CouponCreated) => {
  switch (createdType) {
    case 'template':
      return ROUTER_PATH.templateCouponDesign;
    case 'custom':
      return ROUTER_PATH.customCouponDesign;
    default:
      throw new Error('[ERROR]: 쿠폰 생성 타입이 잘못되었습니다.');
  }
};

const useStep = (
  createdType: CouponCreated,
  reward: string,
  expirePeriod: Option,
  stampCount: string,
) => {
  const [step, setStep] = useState(MODIFY_STEP_NUMBER.createdType);
  const navigate = useNavigate();

  const navigateNextPage = () => {
    navigate(`${ROUTER_PATH.modifyCouponPolicy}${selectRoutePathByCreatedType(createdType)}`, {
      state: {
        createdType,
        reward,
        expirePeriod,
        stampCount,
      },
    });
  };

  const moveNextStep = () => {
    if (step === MODIFY_STEP_NUMBER.rewardName && !reward) {
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

  return { step, moveNextStep, movePrevStep };
};

export default useStep;
