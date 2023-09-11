import { ChangeEvent, Dispatch, SetStateAction } from 'react';
import {
  IconWrapper,
  InputContainer,
  Label,
  RadioInput,
  TypeDescription,
  TypeTitle,
} from './style';
import { Spacing } from '../../../../style/layout/common';
import Text from '../../../../components/Text';
import { CouponCreated } from '../../../../types';
import { GrSelect } from 'react-icons/gr';
import { MdOutlinePhotoSizeSelectLarge } from 'react-icons/md';

interface CreatedTypeProps {
  value: CouponCreated;
  setValue: Dispatch<SetStateAction<CouponCreated>>;
}

const CreatedType = ({ value, setValue }: CreatedTypeProps) => {
  const changeSelectValue = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value === 'template' || e.target.value === 'custom') setValue(e.target.value);
  };

  return (
    <>
      <Text variant="subTitle">step1. 쿠폰을 어떻게 제작하시겠어요?</Text>
      <Spacing $size={30} />
      <InputContainer>
        <RadioInput
          id="template"
          type="radio"
          name="create-formula"
          value="template"
          onChange={changeSelectValue}
        />
        <Label htmlFor="template" $isChecked={value === 'template'}>
          <TypeTitle>템플릿</TypeTitle>
          <TypeDescription>
            스탬프크러쉬에서 <br /> 제공해주는 템플릿을 <br /> 사용할게요.
          </TypeDescription>
          <IconWrapper>
            <GrSelect size={30} />
          </IconWrapper>
        </Label>
        <RadioInput
          id="custom"
          type="radio"
          name="create-formula"
          value="custom"
          onChange={changeSelectValue}
        />
        <Label htmlFor="custom" $isChecked={value === 'custom'}>
          <TypeTitle>커스텀</TypeTitle>
          <TypeDescription>
            직접 쿠폰 이미지를 <br /> 업로드하여 <br />
            커스텀할게요.
          </TypeDescription>
          <IconWrapper>
            <MdOutlinePhotoSizeSelectLarge size={30} />
          </IconWrapper>
        </Label>
      </InputContainer>
    </>
  );
};

export default CreatedType;
