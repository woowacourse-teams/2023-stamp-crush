import { styled } from 'styled-components';

// FIXME: 빈 스타일드 컴포넌트
export const RadioInputsContainer = styled.div``;

export const Label = styled.label`
  display: block;
`;

export const LabelText = styled.span`
  padding-left: 8px;
`;

export const RadioInput = styled.input`
  width: 12px;
  height: 12px;
  appearance: none;
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 2px;

  &:hover {
    border: 2px solid ${({ theme }) => theme.colors.gray300};
  }

  &:checked {
    background-color: ${({ theme }) => theme.colors.point};
    border: 1px solid ${({ theme }) => theme.colors.point};
  }
`;
