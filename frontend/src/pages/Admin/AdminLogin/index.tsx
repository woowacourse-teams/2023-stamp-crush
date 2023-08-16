import { StampcrushLogo, KakaoLoginButton } from '../../../assets';
import { Container, LogoImg, NaverLoginLink } from './style';

const AdminLogin = () => {
  const KAKAO_LOGIN_PAGE_URL = 'https://stampcrush.site/api/admin/login/kakao';

  return (
    <Container>
      <LogoImg src={StampcrushLogo} alt="스탬프크러쉬로고" />
      <NaverLoginLink href={KAKAO_LOGIN_PAGE_URL}>
        <img src={KakaoLoginButton} alt="네이버로그인" />
      </NaverLoginLink>
      ㄴ 카카오임
    </Container>
  );
};

export default AdminLogin;
