import { ButtonHTMLAttributes } from 'react';
import { BaseButton } from './Button.style';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary';
  size?: 'medium' | 'large';
}

const Button = ({ variant = 'primary', size = 'medium', ...props }: ButtonProps) => {
  return <BaseButton {...props} $variant={variant} $size={size} />;
};

export default Button;
