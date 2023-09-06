import { LoginLogo, CustomerKakaoLoginButton } from '../../../assets';
import { BASE_URL } from '../../../constants';
import { Container, KakaoLoginImg, LogoImg, NaverLoginLink } from './style';

const AdminLogin = () => {
  const KAKAO_LOGIN_PAGE_URL = `${BASE_URL}/admin/login/kakao`;

  return (
    <Container>
      <LogoImg src={LoginLogo} alt="스탬프크러쉬로고" />
      <NaverLoginLink href={KAKAO_LOGIN_PAGE_URL}>
        <KakaoLoginImg src={CustomerKakaoLoginButton} alt="카카오 로그인" />
      </NaverLoginLink>
    </Container>
  );
};

export default AdminLogin;
