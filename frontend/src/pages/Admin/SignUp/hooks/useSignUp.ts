import { ChangeEvent, FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ID_REGEX, PW_REGEX, ROUTER_PATH } from '../../../../constants';
import usePostSignUp from './usePostSignUp';

const useSignUp = () => {
  const navigate = useNavigate();

  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');
  const [checkedPassword, setCheckedPassword] = useState('');

  const [isValidId, setIsValidId] = useState(false);
  const [isValidPw, setIsValidPw] = useState(false);

  const { mutate: mutateSignUp } = usePostSignUp();

  const signUp = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (password !== checkedPassword) {
      alert('비밀번호가 불일치합니다. 동일한 비밀번호를 입력해주세요.');
      return;
    }

    if (!ID_REGEX.test(loginId) || loginId.length < 5 || loginId.length > 20) {
      alert('아이디는 5~20글자의 영문자와 숫자만 허용됩니다.');
      return;
    }

    if (!PW_REGEX.test(password) || password.length < 8 || password.length > 30) {
      alert(
        '비밀번호는 영문자, 숫자 및 특수문자 조합이어야 하며, 8자 이상 30자 이하로 입력되어야 합니다.',
      );
      return;
    }

    mutateSignUp({
      body: {
        loginId,
        password,
      },
    });
    navigate(ROUTER_PATH.adminLogin);
  };

  const inputCheckedPassword = (e: ChangeEvent<HTMLInputElement>) => {
    setCheckedPassword(e.target.value);
  };

  const checkPassword = () => {
    return password === checkedPassword;
  };

  const validateId = (e: ChangeEvent<HTMLInputElement>) => {
    setLoginId(e.target.value);
    loginId.length > 0 && !ID_REGEX.test(loginId) ? setIsValidId(true) : setIsValidId(false);
  };

  const validatePw = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
    password.length > 0 && !PW_REGEX.test(password) ? setIsValidPw(true) : setIsValidPw(false);
  };

  return {
    loginId,
    password,
    checkedPassword,
    validateId,
    validatePw,
    isValidId,
    isValidPw,
    checkPassword,
    inputCheckedPassword,
    signUp,
  };
};

export default useSignUp;
