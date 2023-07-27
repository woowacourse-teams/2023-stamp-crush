import type { Meta, StoryFn } from '@storybook/react';
import ProgressBar from '../components/ProgressBar';
import { styled } from 'styled-components';

export default {
  title: 'ProgressBar',
  component: ProgressBar,
  tags: ['autodocs'],
} as Meta;

const Template: StoryFn = (props) => (
  <Wrapper>
    <ProgressBar stampCount={3} maxCount={8} {...props} />
  </Wrapper>
);

export const Example = Template.bind({});

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
