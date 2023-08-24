import Dialpad from './Dialpad';
import { Container, IconWrapper, PageContainer, PrivacyBox, Title } from './style';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';

const EnterPhoneNumber = () => {
  useRedirectRegisterPage();
  const navigate = useNavigate();

  const navigateBack = () => {
    navigate(ROUTER_PATH.customerList);
  };

  return (
    <PageContainer>
      <Title>
        <IconWrapper onClick={navigateBack}>
          <IoIosArrowBack size="40" />
        </IconWrapper>
        전화번호 입력
      </Title>
      <Container>
        <PrivacyBox>
          <h1>개인정보 제공동의</h1>
          <p>전화번호를 입력하시면 개인정보 제공에 동의하시는 것으로 간주됩니다.</p>
        </PrivacyBox>
        <Dialpad />
      </Container>
    </PageContainer>
  );
};

export default EnterPhoneNumber;
