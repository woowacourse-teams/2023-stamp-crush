import { ButtonHTMLAttributes } from 'react';
import styled from 'styled-components';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary';
  size?: 'medium' | 'large';
}

const TYPE_VARIANTS = {
  primary: {
    color: 'var(--primary-text-color)',
    backgroundColor: 'var(--primary-color)',
  },
  secondary: {
    border: '1px solid #000000',
    color: '#000000',
    backgroundColor: 'white',
  },
};

const SIZE_VARIANTS = {
  medium: {
    fontSize: '14px',
    padding: '8px 35px',
  },
  large: {
    fontSize: '16px',
    padding: '8px 0px',
    width: '100%',
  },
};

const Button = ({ variant = 'primary', size = 'medium', ...props }: Props) => {
  return (
    <Wrapper
      style={{
        ...TYPE_VARIANTS[variant],
        ...SIZE_VARIANTS[size],
      }}
      {...props}
    />
  );
};

export default Button;

const Wrapper = styled.button`
  outline: none;
  border: 0 solid transparent;
  border-radius: 7px;

  transition: background-color 0.2s ease color 0.1s ease;
  font-weight: 600;
  line-height: 26px;

  cursor: pointer;
  &:active {
    transform: scale(0.985);
    opacity: 70%;
  }
`;
