import { styled } from 'styled-components';

interface StyledSpacingProps {
  $size: number;
}

export const CreateCouponContainer = styled.div`
  margin: 0 auto;
`;

export const Spacing = styled.div<StyledSpacingProps>`
  width: 100%;
  height: ${({ $size }) => `${$size}px`};
`;

export const Title = styled.h2`
  font-size: 36px;
  font-weight: 700;
`;

export const SubTitle = styled.p`
  font-size: 16px;
  font-weight: 700;
`;

export const Text = styled.span`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.black};
`;

export const NextButtonWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  width: 100%;
`;
