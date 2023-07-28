import { Meta, StoryFn } from '@storybook/react';
import SearchBar, { SearchBarProps } from '../components/SearchBar';

export default {
  title: 'SearchBar',
  component: SearchBar,
} as Meta<SearchBarProps>;

export const Template: StoryFn<SearchBarProps> = (props: SearchBarProps) => {
  return (
    <SearchBar
      searchWord={props.searchWord}
      setSearchWord={props.setSearchWord}
      onClick={props.onClick}
    />
  );
};
