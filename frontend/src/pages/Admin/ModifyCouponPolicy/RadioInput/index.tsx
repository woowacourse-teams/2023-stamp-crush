import { ChangeEvent, Dispatch } from 'react';
import { RadioInputsContainer, Label, LabelText, RadioInput } from './style';
import { Spacing } from '../../../../style/layout/common';

interface RadioInputsProps {
  setValue: Dispatch<React.SetStateAction<string>>;
}

const RadioInputs = ({ setValue }: RadioInputsProps) => {
  const changeSelectValue = (e: ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };

  return (
    <RadioInputsContainer>
      <Label>
        <RadioInput
          type="radio"
          name="create-formula"
          value="template"
          onChange={changeSelectValue}
        />
        <LabelText>템플릿(쿠폰이미지, 스탬프 개수, 스탬프 이미지)으로 선택할게요.</LabelText>
      </Label>
      <Spacing $size={8} />
      <label>
        <RadioInput
          type="radio"
          name="create-formula"
          value="custom"
          onChange={changeSelectValue}
        />
        <LabelText>직접 사진을 업로드 하여 커스텀 할게요.</LabelText>
      </label>
    </RadioInputsContainer>
  );
};

export default RadioInputs;
