import styled, { css } from 'styled-components';

export const BaseCustomerTemplate = styled.div<{ $isHome: boolean }>`
  display: flex;
  width: 100vw;
  height: 100vh;

  ${({ $isHome }) =>
    $isHome &&
    css`
      touch-action: none;
    `}
`;

export const ContentContainer = styled.main`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  max-width: 450px;
  margin: 0 auto;
  border-radius: 8px;

  @media screen and (min-width: 450px) {
    position: relative;

    margin: 0 auto;
  }
`;
