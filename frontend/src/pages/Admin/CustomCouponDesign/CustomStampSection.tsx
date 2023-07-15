import React, { useState } from 'react';
import { CustomStampSectionContainer } from './CustomStampSection.style';
import { Spacing } from '../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
  PreviewLabel,
} from './style';

interface CustomStampSectionProps {
  label: string;
  uploadImageInputId: string;
}
const CustomStampSection = ({ label, uploadImageInputId }: CustomStampSectionProps) => {
  const [imgFileUrl, setImgFileUrl] = useState<string>();

  const uploadImageFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const files = e.target.files[0];
    // TODO: 해당 로직을 어떻게 저장할지 논의 해보아야 함.
    const imgUrl = URL.createObjectURL(files);
    setImgFileUrl(imgUrl);
  };
  return (
    <CustomStampSectionContainer>
      <PreviewLabel>{label}</PreviewLabel>
      <Spacing $size={4} />
      <ImageUpLoadInput
        id={uploadImageInputId}
        type="file"
        accept="image/jpg,image/png,image/jpeg,image/gif"
        onChange={uploadImageFile}
      />
      <ImageUpLoadInputLabel htmlFor={uploadImageInputId}>이미지 업로드 +</ImageUpLoadInputLabel>
      <Spacing $size={8} />
      <PreviewImageWrapper $height={50} $width={50}>
        <PreviewImage src={imgFileUrl} $height={50} $width={50} />
      </PreviewImageWrapper>
    </CustomStampSectionContainer>
  );
};

export default CustomStampSection;
