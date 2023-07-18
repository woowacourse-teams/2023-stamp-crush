import styled from 'styled-components';

export const ContentContainer = styled.main`
  display: flex;
  justify-content: center;
`;

export const RegisterCafeInputForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 35px;

  position: relative;

  & > button {
    position: absolute;
    right: 0;
    bottom: -100px;

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
