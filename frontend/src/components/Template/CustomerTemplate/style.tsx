import styled from 'styled-components';

export const BaseCustomerTemplate = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;

  @media screen and (min-width: 768px) {
    align-items: center;
  }
`;

export const ContentContainer = styled.main`
  display: flex;
  flex-direction: column;
  height: 90vh;
  max-width: 450px;
  margin: 0 auto;
  border-radius: 8px;

  @media screen and (min-width: 768px) {
    position: relative;
    width: 80%;
    margin: 0 auto;
  }

  @media screen and (max-width: 768px) {
    width: 100%;
    position: static;
  }
`;
