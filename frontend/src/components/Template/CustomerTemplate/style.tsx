import styled from 'styled-components';

export const BaseCustomerTemplate = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;
  align-items: center;
`;

export const ContentContainer = styled.main`
  display: flex;
  position: relative;
  flex-direction: column;
  height: 90vh;
  max-width: 450px;
  margin: 0 auto;
  border-radius: 8px;

  @media screen and (min-width: 768px) {
    width: 80%;
    margin: 0 auto;
  }
`;
