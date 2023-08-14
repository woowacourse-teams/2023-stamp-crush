import { StampcrushLogo, NaverLoginButton } from '../../../assets';
import { Container, LogoImg, NaverLoginLink } from './style';

const AdminLogin = () => {
  return (
    <Container>
      <LogoImg src={StampcrushLogo} alt="스탬프크러쉬로고" />
      <NaverLoginLink href="https://stampcrush.site/api/admin/login/kakao">
        <img src={NaverLoginButton} alt="네이버로그인" />
      </NaverLoginLink>
      ㄴ 카카오임
    </Container>
  );
};

export default AdminLogin;
