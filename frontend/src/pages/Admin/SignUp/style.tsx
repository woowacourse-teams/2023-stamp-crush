import styled from 'styled-components';

export const SignUpTemplate = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;

  & > :first-child {
    margin-bottom: 80px;
  }
`;

export const SignUpForm = styled.form`
  display: flex;
  position: relative;
  flex-direction: column;
  width: 400px;

  input {
    margin-bottom: 30px;
  }
`;

export const ErrorCaption = styled.p`
  position: absolute;
  bottom: 16px;
  height: fit-content;
  font-size: 12px;
  color: red;
`;

export const InputWrapper = styled.div`
  position: relative;
  height: fit-content;
`;
