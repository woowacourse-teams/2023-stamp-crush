import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import { Container, NicknameWrapper, GreetingWrapper } from './style';
import { LoginLogo } from '../../../assets';
import ROUTER_PATH from '../../../constants/routerPath';

const Greeting = () => {
  const navigate = useNavigate();
  const { customerProfile } = useCustomerProfile();

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      navigate(ROUTER_PATH.couponList);
    }, 1000);

    return () => clearTimeout(timeoutId);
  }, [navigate]);

  return (
    <Container>
      <LoginLogo />
      <div>
        반갑습니다. <NicknameWrapper>{customerProfile?.profile.nickname}</NicknameWrapper> 고객님!
      </div>
      <GreetingWrapper>저희 스탬프크러쉬 서비스를 이용해주셔서 감사합니다!</GreetingWrapper>
      <p>잠시 후 서비스로 이동합니다.</p>
    </Container>
  );
};

export default Greeting;
