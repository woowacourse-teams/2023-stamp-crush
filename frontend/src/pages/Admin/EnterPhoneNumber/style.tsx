import { styled } from 'styled-components';

export const Title = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  width: 100%;
  height: 80px;
  border-bottom: 3px solid ${({ theme }) => theme.colors.gray400};
  padding: auto 0;
  line-height: 110px;
  font-size: 26px;
  font-weight: 600;
`;

export const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  left: 30px;
  top: 0;
  bottom: 0;
  cursor: pointer;
`;

export const Container = styled.div`
  display: flex;
  height: auto;

  @media screen and (max-width: 768px) {
    justify-content: center;
    touch-action: none;
  }
`;
