import { BaseText } from './style';

interface TextProps {
  variant?: 'default' | 'pageTitle' | 'subTitle';
  children: string;
}

const Text = ({ variant = 'default', children }: TextProps) => {
  return <BaseText $variant={variant}>{children}</BaseText>;
};

export default Text;
