import { styled } from 'styled-components';

export const Container = styled.form`
  position: relative;
  width: 250px;
`;

export const BaseInput = styled.input`
  display: flex;
  width: 250px;
  height: 40px;

  background: white;
  box-shadow: 0px 0px 15px #888;
  border-radius: 30px;
  outline: none;
  padding: 0 15px;

  &:focus {
    border: 2px solid ${({ theme }) => theme.colors.text};
  }
`;

export const SearchButton = styled.button`
  width: 45px;
  height: 45px;
  border-radius: 50%;
  position: absolute;
  right: -10px;
  top: -2px;
  box-shadow: 0px 0px 15px #888;
  background: ${({ theme }) => theme.colors.text};

  cursor: pointer;
`;
