import { ReactNode } from 'react';
import { BaseText } from './style';

interface TextProps {
  variant?: 'default' | 'pageTitle' | 'subTitle';
  ariaLabel?: string;
  children: ReactNode;
}

const Text = ({ variant = 'default', ariaLabel = '텍스트', children }: TextProps) => {
  return (
    <BaseText $variant={variant} aria-label={ariaLabel}>
      {children}
    </BaseText>
  );
};

export default Text;
