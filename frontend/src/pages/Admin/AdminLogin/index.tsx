import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api, ownerHeader } from '../../../api';
import { MutateReq } from '../../../types/api';
import { StampcrushLogo, NaverLoginButton } from '../../../assets';
import { Container, LoginButton, LogoImg } from './style';

// interface NaverLoginReqBody {
//   clientId: number;
// }

// interface NaverLoginRes {

// }

export const postNaverLogin = async () => {
  return await api.post('/admin/login/naver');
};

const AdminLogin = () => {
  const navigate = useNavigate();
  const { mutate: naverLogin } = useMutation({
    mutationFn: postNaverLogin,
    onSuccess: (data: any) => {
      // console.log(data.headers);
      // navigate(data.headers['location']);
    },
  });

  const goNaverLoginPage = () => {
    naverLogin();
  };

  return (
    <Container>
      <LogoImg src={StampcrushLogo} alt="스탬프크러쉬로고" />
      <LoginButton onClick={goNaverLoginPage}>
        <img src={NaverLoginButton} alt="네이버로그인" />
      </LoginButton>
      {/* <button>카카오로그인</button> */}
    </Container>
  );
};

export default AdminLogin;
