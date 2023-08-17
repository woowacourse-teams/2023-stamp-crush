import { useNavigate } from 'react-router-dom';
import { LoginLogo, CustomerKakaoLoginButton } from '../../../assets';
import { Container, KakaoLoginImg, LogoImg, NaverLoginLink } from './style';
import { MouseEvent, useEffect } from 'react';
import { ROUTER_PATH } from '../../../constants';

const AdminLogin = () => {
  const navigate = useNavigate();
  const KAKAO_LOGIN_PAGE_URL = `${process.env.REACT_APP_BASE_URL}/admin/login/kakao`;

  const test = (e: MouseEvent<HTMLAnchorElement>) => {
    if (process.env.NODE_ENV === 'development') {
      e.preventDefault();
      navigate(ROUTER_PATH.customerList);
    }
  };

  return (
    <Container>
      <LogoImg src={LoginLogo} alt="스탬프크러쉬로고" />
      <NaverLoginLink onClick={test} href={KAKAO_LOGIN_PAGE_URL}>
        <KakaoLoginImg src={CustomerKakaoLoginButton} alt="카카오 로그인" />
      </NaverLoginLink>
    </Container>
  );
};

export default AdminLogin;
