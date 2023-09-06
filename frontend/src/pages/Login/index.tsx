import { LoginLogo } from '../../assets';
import { CustomerKakaoLoginButton } from '../../assets';
import { BASE_URL, ROUTER_PATH } from '../../constants';
import {
  Container,
  KakaoLoginImg,
  LoginLink,
  LogoImg,
  RedirectLink,
  ServiceIntro,
  ServiceIntroSub,
  RedirectContainer,
} from './style';

const Login = () => {
  const KAKAO_LOGIN_PAGE_URL = `${BASE_URL}/login/kakao`;

  return (
    <Container>
      <LogoImg src={LoginLogo} />
      <ServiceIntro>흩어져있는 종이 쿠폰을 한번에!</ServiceIntro>
      <ServiceIntroSub>카페쿠폰 관리 서비스</ServiceIntroSub>
      <LoginLink href={KAKAO_LOGIN_PAGE_URL}>
        <KakaoLoginImg src={CustomerKakaoLoginButton} alt="카카오 로그인" />
      </LoginLink>
      <RedirectContainer>
        <span>사장님이신가요? </span>
        <RedirectLink to={ROUTER_PATH.adminLogin}>사장님 로그인</RedirectLink>
      </RedirectContainer>
    </Container>
  );
};

export default Login;
