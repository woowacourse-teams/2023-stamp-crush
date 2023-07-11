import { styled } from 'styled-components';

export const Container = styled.section`
  display: flex;
  width: 540px;
  flex-direction: column;
  border: 1px solid black;
`;

export const KeyContainer = styled.div`
  display: grid;

  grid-template-columns: repeat(3, 180px);
  grid-template-rows: repeat(4, 120px);
`;

export const Pad = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid black;

  font-size: 50px;
  background-color: white;

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
  height: 150px;
  padding: 20px 0px;

  font-size: 50px;
  text-align: center;

  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;
