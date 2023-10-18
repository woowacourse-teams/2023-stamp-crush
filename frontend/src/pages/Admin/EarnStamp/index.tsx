import { useLocation } from 'react-router-dom';
import { useState } from 'react';
import { EarnStampContainer, EarnStampPageContainer } from './style';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import Stepper from '../../../components/Stepper';
import Text from '../../../components/Text';
import { CustomerPhoneNumber } from '../../../types/domain/customer';
import CouponIndicator from './components/CouponIndicator';

const EarnStamp = () => {
  const cafeId = useRedirectRegisterPage();
  const [stamp, setStamp] = useState(1);
  const { state } = useLocation();
  const location = useLocation();
  const customer: CustomerPhoneNumber = location.state;

  return (
    <EarnStampPageContainer>
      <Text variant="pageTitle">스탬프 적립</Text>
      <p>{state.nickname} 고객에게 적립할 스탬프 갯수를 입력해주세요.</p>
      <Stepper value={stamp} setValue={setStamp} />
      <EarnStampContainer>
        <CouponIndicator stamp={stamp} customer={customer} customerId={Number(state.id)} />

        {/**TODO: 보유 쿠폰의 발급 시기와 새 쿠폰의 발급 시기에 차이가 있는 케이스에 대한 대처가 필요 */}
      </EarnStampContainer>
    </EarnStampPageContainer>
  );
};

export default EarnStamp;
