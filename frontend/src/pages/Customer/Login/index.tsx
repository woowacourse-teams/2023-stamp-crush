import { BASE_URL } from '../../../api';
import { CustomerKakaoLoginButton } from '../../../assets';
import { CustomerLoginBackground } from '../../../assets/png';
import {
  Container,
  KakaoLoginImg,
  LoginLink,
  ServiceIntro,
  ServiceIntroSub,
  Template,
  BackgroundImage,
  Backdrop,
} from './style';

const Login = () => {
  const KAKAO_LOGIN_PAGE_URL = `${BASE_URL}/login/kakao`;

  return (
    <Template>
      <BackgroundImage src={CustomerLoginBackground} />
      <Backdrop />
      <Container>
        <ServiceIntro>흩어져있는 종이 쿠폰을 한번에</ServiceIntro>
        <ServiceIntroSub>카페 쿠폰 관리 서비스</ServiceIntroSub>
        <LoginLink href={KAKAO_LOGIN_PAGE_URL}>
          <KakaoLoginImg src={CustomerKakaoLoginButton} alt="카카오 로그인" />
        </LoginLink>
      </Container>
    </Template>
  );
};

export default Login;
