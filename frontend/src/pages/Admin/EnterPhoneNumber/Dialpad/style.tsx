import { styled } from 'styled-components';

export const Container = styled.section`
  display: flex;
  flex-direction: column;
  border-left: 1px solid black;
  border-right: 1px solid black;
  border-collapse: separate;
  min-width: 580px;
`;

export const KeyContainer = styled.div`
  display: grid;
  height: 100%;

  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(4, 1fr);
`;

export const Pad = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid black;

  font-size: 50px;
  background-color: white;
  color: black;

  &:active {
    background-color: ${({ theme }) => theme.colors.gray100};
    transform: scale(0.98);
  }
  &:last-child {
    background-color: ${({ theme }) => theme.colors.main};
    color: ${({ theme }) => theme.colors.point};
  }
`;

export const BaseInput = styled.input`
  outline: none;
  padding: 20px 0px;
  height: 210px;
  border: 1px solid black;
  border-top: none;

  font-size: 50px;
  text-align: center;

  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;
