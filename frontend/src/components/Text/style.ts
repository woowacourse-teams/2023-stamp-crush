import { styled } from 'styled-components';

interface StyledTextProps {
  $variant: 'default' | 'pageTitle' | 'subTitle';
}

const TYPE: Record<string, Record<string, string>> = {
  default: {
    fontSize: '16px',
    fontWeight: '400',
  },
  pageTitle: {
    fontSize: '34px',
    fontWeight: '800',
  },
  subTitle: {
    fontSize: '24px',
    fontWeight: '450',
  },
};

export const BaseText = styled.h1<StyledTextProps>`
  font-size: ${({ $variant }) => TYPE[$variant].fontSize};
  font-weight: ${({ $variant }) => TYPE[$variant].fontWeight};
  white-space: pre-line;
  color: #222;
`;
