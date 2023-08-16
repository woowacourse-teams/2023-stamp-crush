import { styled } from 'styled-components';

type StyledInputProps = {
  $width?: number;
  $center?: boolean;
  disabled?: boolean;
  $required?: boolean;
};

export const BaseInput = styled.input<StyledInputProps>`
  width: ${(props) => (props.$width ? `${props.$width}px` : '100%')};
  padding: 8px 4px;

  border: none;
  text-align: ${(props) => (props.$center ? 'center' : 'initial')};
  font-size: 16px;

  background: transparent;
  border-bottom: 1px solid ${({ theme }) => theme.colors.black};
  transition: 0.4s ease-in-out;

  &:focus {
    border-bottom: 1px solid ${({ theme }) => theme.colors.main};
    outline: none;
    transition: 0.4s ease-in-out;
  }
  &::placeholder {
    color: #aaa;
  }
`;

export const Label = styled.label`
  font-weight: 700;
`;

export const Required = styled.span`
  color: #ff0505;
`;

export const LabelWrapper = styled.div<StyledInputProps>`
  display: flex;
  flex-direction: row;
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
`;
