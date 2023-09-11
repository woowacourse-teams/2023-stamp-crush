import { useState } from 'react';
import { MODIFY_STEP_NUMBER } from '../common/constant';
import { CouponCreated, Option } from '../../../../types';
import { selectRoutePathByCreatedType } from '../logic';
import { ROUTER_PATH } from '../../../../constants';
import { useNavigate } from 'react-router-dom';

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
