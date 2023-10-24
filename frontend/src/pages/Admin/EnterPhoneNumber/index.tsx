import Dialpad from './components/Dialpad';
import { IoIosArrowBack } from '@react-icons/all-files/io/IoIosArrowBack';
import { Container, IconWrapper, Title } from './style';
import { useLocation, useNavigate } from 'react-router-dom';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import PrivateProvider from '../../../provider/PrivateProvider';
import ROUTER_PATH from '../../../constants/routerPath';
import PrivacyBox from './components/PrivacyBox';

const EnterPhoneNumber = () => {
  useRedirectRegisterPage();
  const location = useLocation();
  const navigate = useNavigate();

  const navigateBack = () => {
    navigate(ROUTER_PATH.customerList);
  };

  return (
    <PrivateProvider consumer={'admin'}>
      <Title>
        <IconWrapper onClick={navigateBack}>
          <IoIosArrowBack size="40" />
        </IconWrapper>
        {location.pathname === ROUTER_PATH.enterStamp ? '스탬프 적립' : '리워드 사용'}
      </Title>
      <Container>
        <PrivacyBox />
        <Dialpad />
      </Container>
    </PrivateProvider>
  );
};

export default EnterPhoneNumber;
