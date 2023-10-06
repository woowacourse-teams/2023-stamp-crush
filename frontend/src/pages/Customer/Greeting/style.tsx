import styled from 'styled-components';

export const Container = styled.section`
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;

  & :nth-child(2) {
    display: flex;
    flex-direction: row;
    font-size: 24px;
    margin: 40px 0 20px 0;
  }

  & > p {
    color: #888;
  }
`;

export const NicknameWrapper = styled.h1`
  font-weight: bold;
  margin-left: 4px;
  margin-right: 2px;
`;

export const GreetingWrapper = styled.h2`
  line-height: 24px;
`;
