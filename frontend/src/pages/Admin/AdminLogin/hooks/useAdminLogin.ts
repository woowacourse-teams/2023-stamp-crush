import { ChangeEvent, FormEvent, useState } from 'react';
import usePostAdminLogin from './usePostAdminLogin';

type FocusType = 'ID' | 'PW';

const useAdminLogin = () => {
  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');

  const [isFocusedId, setIsFocusedId] = useState(false);
  const [isFocusedPw, setIsFocusedPw] = useState(false);
  const { mutate: mutateAdminLogin, isSuccess } = usePostAdminLogin();

  const loginAdmin = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    mutateAdminLogin({
      body: {
        loginId,
        password,
      },
    });
  };

  const getLoginId = (e: ChangeEvent<HTMLInputElement>) => {
    setLoginId(e.target.value);
  };

  const getPassword = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleFocus = (type: FocusType) => () => {
    switch (type) {
      case 'ID':
        setIsFocusedId(true);
        setIsFocusedPw(false);
        break;
      case 'PW':
        setIsFocusedId(false);
        setIsFocusedPw(true);
        break;
      default:
        break;
    }
  };

  const handleBlur = () => {
    setIsFocusedId(false);
    setIsFocusedPw(false);
  };

  return {
    handleBlur,
    handleFocus,
    isFocusedId,
    isFocusedPw,
    loginAdmin,
    loginId,
    password,
    getLoginId,
    getPassword,
  };
};

export default useAdminLogin;
