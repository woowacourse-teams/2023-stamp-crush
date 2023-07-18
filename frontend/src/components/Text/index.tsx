import { PropsWithChildren } from 'react';
import { BaseText } from './style';

interface TextProps {
  variant: 'default' | 'pageTitle' | 'subTitle';
}

const Text = ({ variant = 'default', children }: TextProps & PropsWithChildren) => {
  return <BaseText $variant={variant}>{children}</BaseText>;
};

export default Text;
