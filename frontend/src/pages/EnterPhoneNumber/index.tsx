import Dialpad from '../../components/Dialpad';
import { Container, PageContainer, PrivacyBox, Title } from './style';

const EnterPhoneNumber = () => {
  return (
    <PageContainer>
      <Title>전화번호 입력</Title>
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
