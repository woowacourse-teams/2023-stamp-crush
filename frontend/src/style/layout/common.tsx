import { styled } from 'styled-components';

interface StyledSpacingProps {
  $size: number;
}

export const PageContainer = styled.div`
  margin: 0 auto;
`;

export const Spacing = styled.div<StyledSpacingProps>`
  width: 100%;
  height: ${({ $size }) => `${$size}px`};
`;

export const RowSpacing = styled.div<StyledSpacingProps>`
  width: ${({ $size }) => `${$size}px`};
  height: 100%;
`;
