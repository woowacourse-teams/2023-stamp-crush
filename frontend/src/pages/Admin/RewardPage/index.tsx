import { useLocation } from 'react-router-dom';
import { RewardContainer } from './style';
import { Spacing } from '../../../style/layout/common';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { CustomerPhoneNumber } from '../../../types/domain/customer';
import Text from '../../../components/Text';
import RewardItemList from './components/RewardItemList';

interface RewardPageState {
  state: CustomerPhoneNumber;
}

const RewardPage = () => {
  const cafeId = useRedirectRegisterPage();
  const location = useLocation() as RewardPageState;

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">리워드 사용</Text>
      <Spacing $size={36} />
      <RewardContainer>
        <Text variant="subTitle">{location.state.nickname} 고객님</Text>
        <Spacing $size={72} />
        <Text variant="subTitle">보유 리워드 내역</Text>
        <Spacing $size={42} />
        <RewardItemList cafeId={cafeId} customerId={location.state.id} />
      </RewardContainer>
    </>
  );
};

export default RewardPage;
