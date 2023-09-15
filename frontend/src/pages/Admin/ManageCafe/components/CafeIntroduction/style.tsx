import styled from 'styled-components';

export const TextArea = styled.textarea`
  border: 1px solid #aaa;
  border-radius: 4px;
  padding: 10px;
  resize: none;
  height: 100px;
  font-size: 16px;
`;

export const RestrictionLabel = styled.label<{ $isExceed: boolean }>`
  color: ${({ $isExceed }) => ($isExceed ? 'tomato' : '#aaa')};
  margin-left: auto;
`;
