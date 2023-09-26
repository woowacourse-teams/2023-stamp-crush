import { ChangeEvent, FormEvent, useState } from 'react';
import { LoginLogo } from '../../../assets';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { Spacing } from '../../../style/layout/common';
import { ErrorCaption, InputWrapper, SignUpForm, SignUpTemplate } from './style';
import { ID_REGEX, PW_REGEX } from '../../../constants';

const SignUp = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [checkedPassword, setCheckedPassword] = useState('');

  const [isValidateId, setIsValidateId] = useState(false);
  const [isValidatePw, setIsValidatePw] = useState(false);

  const signUp = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (password !== checkedPassword) {
      alert('비밀번호가 불일치합니다. 동일한 비밀번호를 입력해주세요.');
      return;
    }

    if (!ID_REGEX.test(id)) {
      alert('아이디는 영문자와 숫자만 허용됩니다.');
      return;
    }

    if (!PW_REGEX.test(password)) {
      alert(
        '비밀번호는 영문자, 숫자 및 특수문자 조합이어야 하며, 4자 이상 20자 이하로 입력되어야 합니다.',
      );
      return;
    }
  };

  const inputCheckedPassword = (e: ChangeEvent<HTMLInputElement>) => {
    setCheckedPassword(e.target.value);
  };

  const checkPassword = () => {
    return password === checkedPassword;
  };

  const validateId = (e: ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value);
    id.length > 0 && !ID_REGEX.test(id) ? setIsValidateId(true) : setIsValidateId(false);
  };

  const validatePw = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
    password.length > 0 && !PW_REGEX.test(password)
      ? setIsValidatePw(true)
      : setIsValidatePw(false);
  };

  return (
    <SignUpTemplate>
      <LoginLogo />
      <SignUpForm onSubmit={signUp}>
        <InputWrapper>
          <Input
            id="admin-id"
            label="아이디"
            minLength={5}
            maxLength={20}
            onChange={validateId}
            placeholder="아이디를 입력해주세요."
            required={true}
          />
          {isValidateId && <ErrorCaption>아이디는 영문과 숫자로만 작성해주세요.</ErrorCaption>}
        </InputWrapper>
        <InputWrapper>
          <Input
            id="admin-pw"
            label="비밀번호"
            type="password"
            minLength={8}
            maxLength={30}
            onChange={validatePw}
            onFocus={checkPassword}
            onBlur={checkPassword}
            placeholder="비밀번호를 입력해주세요."
            required={true}
          />
          {isValidatePw && (
            <ErrorCaption>
              비밀번호는 영문과 숫자, 특수기호(!@#$%^*+=-)의 조합으로 작성해주세요.
            </ErrorCaption>
          )}
        </InputWrapper>
        <InputWrapper>
          <Input
            id="admin-pw-check"
            label="비밀번호 확인"
            type="password"
            minLength={8}
            maxLength={30}
            onChange={inputCheckedPassword}
            onFocus={checkPassword}
            onBlur={checkPassword}
            placeholder="비밀번호를 재입력해주세요."
            required={true}
          />
          {checkedPassword !== '' && password !== checkedPassword && (
            <ErrorCaption>비밀번호가 불일치합니다.</ErrorCaption>
          )}
        </InputWrapper>
        <Spacing $size={4} />
        <Button size="medium">회원가입</Button>
      </SignUpForm>
    </SignUpTemplate>
  );
};

export default SignUp;
