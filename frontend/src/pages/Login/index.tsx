import { LoginLogo } from '../../assets';
import { CustomerKakaoLoginButton } from '../../assets';
import { Container } from './style';

const Login = () => {
  const KAKAO_LOGIN_PAGE_URL = 'https://stampcrush.site/api/login/kakao';

  return (
    <Container>
      <img src={LoginLogo} />
      <a href={KAKAO_LOGIN_PAGE_URL}>
        <img src={CustomerKakaoLoginButton} />
      </a>
    </Container>
  );
};

export default Login;
