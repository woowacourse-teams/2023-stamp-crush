import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: 0 auto;

  div > div {
    margin-top: 100px;
  }

  div > input {
    width: 300px;
    margin-bottom: 40px;
  }
`;
