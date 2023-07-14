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

export const Title = styled.h2`
  font-size: 36px;
  font-weight: 700;
`;

export const SectionTitle = styled.p`
  font-size: 16px;
  font-weight: 700;
`;

export const SubTitle = styled.h3`
  font-size: 24px;
  font-weight: 700;
`;

export const Text = styled.span`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.black};
`;
