import useAdminLogin from '../hooks/useAdminLogin';
import { LoginWrapper, LoginForm } from '../style';
import { AiOutlineUser } from '@react-icons/all-files/ai/AiOutlineUser';
import { AiOutlineLock } from '@react-icons/all-files/ai/AiOutlineLock';
import { Input } from '../../../../components/Input';
import { Spacing } from '../../../../style/layout/common';
import Button from '../../../../components/Button';

const AdminLoginForm = () => {
  const { isFocusedId, isFocusedPw, handleBlur, handleFocus, loginAdmin } = useAdminLogin();

  return (
    <LoginForm onSubmit={loginAdmin}>
      <LoginWrapper>
        <AiOutlineUser size={20} color={isFocusedId ? 'black' : '#aaa'} />
        <Input
          id="admin-login"
          placeholder="아이디"
          minLength={5}
          onFocus={handleFocus('ID')}
          onBlur={handleBlur}
        />
      </LoginWrapper>
      <Spacing $size={8} />
      <LoginWrapper>
        <AiOutlineLock size={20} color={isFocusedPw ? 'black' : '#aaa'} />
        <Input
          id="admin-password"
          type="password"
          placeholder="비밀번호"
          onFocus={handleFocus('PW')}
          onBlur={handleBlur}
        />
      </LoginWrapper>
      <Spacing $size={12} />
      <Button size="large">로그인</Button>
      <Spacing $size={8} />
    </LoginForm>
  );
};

export default AdminLoginForm;
