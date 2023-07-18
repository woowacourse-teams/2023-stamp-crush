import styled from 'styled-components';

export const ContentContainer = styled.main`
  display: flex;
  justify-content: center;
  padding-top: 48px;
`;

export const RegisterCafeInputForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 35px;

  position: relative;

  & > button {
    margin-left: auto;
    width: 160px;
    height: 56px;

    font-size: 16px;
  }
`;

export const InputWithButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-end;
  justify-content: space-between;

  width: 550px;

  button {
    height: 42px;
  }
`;

export const Title = styled.header`
  font-size: 34px;
  font-weight: 700;
`;
