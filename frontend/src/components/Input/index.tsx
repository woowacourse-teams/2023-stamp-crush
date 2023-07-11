import { HTMLInputTypeAttribute, forwardRef } from 'react';
import { BaseInput, InputContainer, Label, LabelWrapper, Required } from './Input.style';

const REQUIRED = '*' as const;

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  id: string;
  label?: string;
  type?: HTMLInputTypeAttribute;
  width?: number;
  placeholder?: string;
  maxLength?: number;
  required?: boolean;
}

export const Input = forwardRef<HTMLInputElement, InputProps>((props, ref) => {
  return (
    <InputContainer>
      <LabelWrapper>
        <Label htmlFor={props.id}>{props.label}</Label>
        {props.required && <Required>{REQUIRED}</Required>}
      </LabelWrapper>
      <BaseInput ref={ref} $width={props.width} />
    </InputContainer>
  );
});

Input.displayName = 'Input';
