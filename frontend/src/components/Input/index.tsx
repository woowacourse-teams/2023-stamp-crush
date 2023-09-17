import { HTMLInputTypeAttribute, forwardRef, InputHTMLAttributes } from 'react';
import { BaseInput, InputContainer, InputWidth, Label, LabelWrapper, Required } from './style';

const REQUIRED = '*' as const;

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  id: string;
  label?: string;
  type?: HTMLInputTypeAttribute;
  width?: InputWidth;
  placeholder?: string;
  maxLength?: number;
  required?: boolean;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
  (
    { id, label, type, width = 'fill', placeholder, maxLength, required = false }: InputProps,
    ref,
  ) => {
    return (
      <InputContainer>
        <LabelWrapper>
          <Label htmlFor={id}>{label}</Label>
          {required && <Required>{REQUIRED}</Required>}
        </LabelWrapper>
        <BaseInput
          ref={ref}
          id={id}
          $width={width}
          type={type}
          placeholder={placeholder}
          maxLength={maxLength}
          required={required}
          autoComplete="off"
        />
      </InputContainer>
    );
  },
);

Input.displayName = 'Input';
