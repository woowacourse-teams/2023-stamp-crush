import {
  CustomCouponSectionContainer,
  CouponPreviewHeader,
  CouponPreviewLabel,
  ImageUpLoadInput,
  PreviewImageWrapper,
  PreviewImage,
  ImageUpLoadInputLabel,
} from './CustomCouponSection.style';
import { Spacing, Text } from '../../../style/layout/common';
import { useState } from 'react';

interface CustomCouponSectionProps {
  label: string;
  uploadImageInputId: string;
}
const CustomCouponSection = ({ label, uploadImageInputId }: CustomCouponSectionProps) => {
  const [imgFileUrl, setImgFileUrl] = useState<string>();

  const uploadImageFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const files = e.target.files[0];
    // TODO: 해당 로직을 어떻게 저장할지 논의 해보아야 함.ㄴ
    const imgUrl = URL.createObjectURL(files);
    setImgFileUrl(imgUrl);
  };
  return (
    <CustomCouponSectionContainer>
      <CouponPreviewHeader>
        <CouponPreviewLabel>{label}</CouponPreviewLabel>
        <ImageUpLoadInput
          id={uploadImageInputId}
          type="file"
          accept="image/jpg,image/png,image/jpeg,image/gif"
          onChange={uploadImageFile}
        />
        <ImageUpLoadInputLabel htmlFor={uploadImageInputId}>이미지 업로드 +</ImageUpLoadInputLabel>
      </CouponPreviewHeader>
      <Spacing $size={12} />
      <PreviewImageWrapper>
        <PreviewImage src={imgFileUrl} />
      </PreviewImageWrapper>
      <Spacing $size={8} />
      <Text>270 * 150 의 이미지만 올려주세요.</Text>
    </CustomCouponSectionContainer>
  );
};

export default CustomCouponSection;
