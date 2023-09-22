import { forwardRef, HTMLInputTypeAttribute, InputHTMLAttributes } from 'react';
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

export const Input = forwardRef<HTMLInputElement, InputProps>((props, ref) => {
  return (
    <InputContainer>
      <LabelWrapper>
        <Label htmlFor={props.id}>{props.label}</Label>
        {props.required && <Required>{REQUIRED}</Required>}
      </LabelWrapper>
      <BaseInput ref={ref} $width={props.width} {...props} />
    </InputContainer>
  );
});

Input.displayName = 'Input';
