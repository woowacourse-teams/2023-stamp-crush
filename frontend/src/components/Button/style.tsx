import { styled } from 'styled-components';

interface StyledButtonProps {
  $variant: 'primary' | 'secondary';
  $size: 'medium' | 'large';
}

const TYPE: Record<string, Record<string, string>> = {
  primary: {
    color: 'point',
    background: 'main',
    border: '0 solid transparent',
  },
  secondary: {
    color: 'black',
    background: 'white',
    border: '2px solid #222',
  },
};

const SIZE: Record<string, Record<string, string>> = {
  medium: {
    padding: '8px 35px',
    width: 'auto',
  },
  large: {
    padding: '8px 0px',
    width: '100%',
  },
};

export const BaseButton = styled.button<StyledButtonProps>`
  outline: none;
  border-radius: 7px;

  transition: background-color 0.2s ease color 0.1s ease;
  font-weight: 600;
  line-height: 26px;
  font-size: 15px;

  width: ${({ $size }) => SIZE[$size].width};
  padding: ${({ $size }) => SIZE[$size].padding};
  border: ${({ $variant }) => TYPE[$variant].border};
  color: ${({ theme, $variant }) => theme.colors[TYPE[$variant].color]};
  background-color: ${({ theme, $variant }) => theme.colors[TYPE[$variant].background]};

  cursor: pointer;
  &:active {
    transform: scale(0.985);
    opacity: 70%;
  }
`;
