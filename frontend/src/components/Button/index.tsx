import { ButtonHTMLAttributes } from 'react';
import { BaseButton } from './Button.style';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary';
  size?: 'medium' | 'large';
}

const Button = ({ variant = 'primary', size = 'medium', ...props }: Props) => {
  return <BaseButton {...props} $variant={variant} $size={size} />;
};

export default Button;
