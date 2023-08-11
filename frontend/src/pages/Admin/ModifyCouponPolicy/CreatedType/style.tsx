import { styled } from 'styled-components';

export const Label = styled.label`
  position: relative;
  width: 200px;
  height: 250px;
  background: #d3e3fa;
  border-radius: 10px;
  padding: 20px;
  cursor: pointer;

  &:hover {
    opacity: 80%;
  }
  &:first-of-type {
    margin-right: 50px;
    background: #ffeefd;
  }
`;

export const RadioInput = styled.input`
  appearance: none;

  &:checked + ${Label} {
    box-shadow: 0 0 0 3px inset;
  }
`;

export const InputContainer = styled.div`
  display: flex;
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
  bottom: 20px;
  right: 20px;
`;
