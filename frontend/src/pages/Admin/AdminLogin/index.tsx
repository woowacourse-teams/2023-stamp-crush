import { api } from '../../../api';
import { StampcrushLogo, NaverLoginButton } from '../../../assets';
import { Container, LogoImg, NaverLoginLink } from './style';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { ownerHeader } from '../../../api';
import { MutateReq } from '../../../types/api';

export const postNaverLogin = async () => {
  return await api.post('/admin/login/naver');
};

const AdminLogin = () => {
  return (
    <Container>
      <LogoImg src={StampcrushLogo} alt="스탬프크러쉬로고" />
      <NaverLoginLink href="https://stampcrush.site/api/admin/login/naver">
        <img src={NaverLoginButton} alt="네이버로그인" />
      </NaverLoginLink>
    </Container>
  );
};

export default AdminLogin;
