import { ReactNode } from 'react';
import { BaseText } from './style';

interface TextProps {
  variant?: 'default' | 'pageTitle' | 'subTitle';
  children: ReactNode;
}

const Text = ({ variant = 'default', children }: TextProps) => {
  return <BaseText $variant={variant}>{children}</BaseText>;
};

export default Text;
