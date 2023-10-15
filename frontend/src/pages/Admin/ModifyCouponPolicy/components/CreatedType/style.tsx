import { styled } from 'styled-components';

export const CreatedTypePageContainer = styled.div`
  display: flex;
  gap: 40px;
`;

export const Label = styled.label<{ $isChecked: boolean }>`
  position: relative;
  width: 200px;
  height: ${({ $isChecked }) => ($isChecked ? '200px' : '60px')};
  border-radius: 20px;
  padding: 17px 20px;
  color: ${({ $isChecked, theme }) => ($isChecked ? 'black' : theme.colors.gray)};
  border: 3px solid
    ${({ $isChecked, theme }) => ($isChecked ? theme.colors.main : theme.colors.gray)};
  box-shadow: ${({ $isChecked }) => ($isChecked ? '0px 0px 5px 2px rgba(0, 0, 0, 0.25)' : '')};

  overflow: hidden;
  transform: ${({ $isChecked }) => ($isChecked ? 'scale(1.1)' : '')};
  transition: all 0.2s;
  cursor: pointer;

  &:hover {
    opacity: 80%;
  }

  &:first-of-type {
    margin-right: 50px;
  }
`;

export const RadioInput = styled.input`
  appearance: none;
`;

export const InputContainer = styled.div`
  display: flex;
  padding-left: 10px;
`;

export const TypeTitle = styled.h1`
  font-size: 20px;
  font-weight: 700;
`;

export const TypeDescription = styled.p`
  margin-top: 10px;
  font-size: 16px;
  width: 150px;
  padding: 5px;
  white-space: pre-line;
  line-height: 22px;
`;

export const IconWrapper = styled.div`
  position: absolute;
  bottom: 7px;
  right: 12px;
`;
