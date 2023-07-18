import { StoryFn, Meta } from '@storybook/react';
import Button from '../components/Button';

export default {
  title: 'Button',
  component: Button,
  tags: ['autodocs'],
} as Meta;

const Template: StoryFn = (props) => <Button {...props} />;

export const Primary = Template.bind({});
Primary.args = {
  variant: 'primary',
  size: 'medium',
  children: '다음',
};

export const Secondary = Template.bind({});
Secondary.args = {
  variant: 'secondary',
  size: 'large',
  children: '확인',
};
