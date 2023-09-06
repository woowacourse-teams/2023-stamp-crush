import { LoginLogo, AdminKakaoLoginButton } from '../../../assets';
import { BASE_URL, ROUTER_PATH } from '../../../constants';
import {
  Container,
  KakaoLoginImg,
  LogoImg,
  LoginLink,
  LoginContent,
  BackgroundImg,
  Title,
  SubTitle,
  Text,
  RedirectContainer,
  RedirectLink,
  CheckList,
} from './style';
import BackgroundImgSrc from '../../../assets/admin_login_background.jpg';
import CheckItem from './CheckItem';
const AdminLogin = () => {
  const KAKAO_LOGIN_PAGE_URL = `${BASE_URL}/admin/login/kakao`;

  return (
    <Container>
      <LoginContent>
        <LogoImg src={LoginLogo} alt="스탬프크러쉬로고" />
        <Title>안녕하세요 사장님!:{')'}</Title>
        <SubTitle>스탬프크러쉬입니다.</SubTitle>
        <Text>쿠폰 관리, 이제는 온라인으로 만나보세요.</Text>
        <CheckList>
          <CheckItem text="쿠폰 적립 고객 통계 제공" />
          <CheckItem text="원하는 디자인으로 쿠폰 디자인 가능" />
          <CheckItem text="손쉬운 적립 및 리워드 사용" />
        </CheckList>
        <LoginLink href={KAKAO_LOGIN_PAGE_URL}>
          <KakaoLoginImg src={AdminKakaoLoginButton} alt="카카오 로그인" />
        </LoginLink>
        <RedirectContainer>
          <span>고객님이신가요? </span>
          <RedirectLink to={ROUTER_PATH.login}>고객님 로그인</RedirectLink>
        </RedirectContainer>
      </LoginContent>
      <BackgroundImg src={BackgroundImgSrc} alt="배경 이미지" />
    </Container>
  );
};

export default AdminLogin;
