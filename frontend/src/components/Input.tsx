import { HTMLInputTypeAttribute } from 'react';
import { forwardRef } from 'react';
import styled from 'styled-components';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  type?: HTMLInputTypeAttribute;
  width?: number;
  placeholder?: string;
  variant?: 'basic' | 'underlined';
  maxLength?: number;
}

type StyledInputProps = {
  $width?: number;
  $center?: boolean;
  disabled?: boolean;
};

const BaseInput = styled.input<StyledInputProps>`
  width: ${(props) => (props.$width ? `${props.$width}px` : '100%')};
  padding: 12px;

  border: none;
  border-radius: 8px;
  text-align: ${(props) => (props.$center ? 'center' : 'initial')};
  font-size: 18px;

  &::placeholder {
    color: #aaa;
  }

  &:focus {
    background: #ddd;
  }
`;

const BasicInput = styled(BaseInput)`
  border: 1px solid #000;
`;

const UnderlinedInput = styled(BaseInput)`
  background: transparent;
  border-radius: 8px;
  border-bottom: 2px solid #bbb;

  &:focus {
    border-bottom: 2px solid #ebe5ce;
    outline: none;
  }
`;

const InputVariation = {
  basic: BasicInput,
  underlined: UnderlinedInput,
};

export const Input = forwardRef<HTMLInputElement, InputProps>((props, ref) => {
  const { type, placeholder, width, value, variant, maxLength } = props;

  const InputComponent = InputVariation[variant ?? 'basic'];

  return (
    <InputComponent
      ref={ref}
      type={type}
      $width={width}
      value={value}
      maxLength={maxLength}
      placeholder={placeholder}
    />
  );
});

Input.displayName = 'Input';
