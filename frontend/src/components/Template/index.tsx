import { PropsWithChildren } from 'react';
import { BaseTemplate } from './Template.style';

const Template = ({ children }: PropsWithChildren) => {
  return <BaseTemplate>{children}</BaseTemplate>;
};

export default Template;
