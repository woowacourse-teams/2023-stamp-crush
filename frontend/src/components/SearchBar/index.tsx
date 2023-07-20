import { BaseInput, Container, SearchButton } from './style';
import { BsSearch } from 'react-icons/bs';
import { ChangeEvent } from 'react';

export interface SearchBarProps extends React.InputHTMLAttributes<HTMLInputElement> {
  searchWord: string;
  setSearchWord: (searchWord: string) => void;
  onClick: () => void;
}

const SearchBar = ({ searchWord, setSearchWord, onClick, ...props }: SearchBarProps) => {
  const search = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchWord(e.target.value);
  };

  return (
    <Container>
      <BaseInput value={searchWord} onChange={search} {...props} />
      <SearchButton type="submit" onClick={onClick}>
        <BsSearch color="white" />
      </SearchButton>
    </Container>
  );
};

export default SearchBar;
