import { LoginLogo } from '../../../assets';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { Spacing } from '../../../style/layout/common';
import { ErrorCaption, InputWrapper, SignUpForm, SignUpTemplate } from './style';
import useSignUp from './hooks/useSignUp';

const SignUp = () => {
  const {
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
  } = useSignUp();

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
            value={loginId}
            onChange={validateId}
            placeholder="아이디를 입력해주세요."
            required={true}
          />
          {isValidId && <ErrorCaption>영문과 숫자로만 작성해주세요(5~20자).</ErrorCaption>}
        </InputWrapper>
        <InputWrapper>
          <Input
            id="admin-pw"
            label="비밀번호"
            type="password"
            value={password}
            minLength={8}
            maxLength={30}
            onChange={validatePw}
            onFocus={checkPassword}
            onBlur={checkPassword}
            placeholder="비밀번호를 입력해주세요."
            required={true}
          />
          {isValidPw && (
            <ErrorCaption>
              영문과 숫자, 특수기호(!@#$%^*+=-)의 조합으로 작성해주세요(8~30자).
            </ErrorCaption>
          )}
        </InputWrapper>
        <InputWrapper>
          <Input
            id="admin-pw-check"
            label="비밀번호 확인"
            type="password"
            value={checkedPassword}
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
