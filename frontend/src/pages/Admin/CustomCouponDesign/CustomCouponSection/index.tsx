import { CouponPreviewHeader } from './style';
import { Spacing } from '../../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../style';
import Text from '../../../../components/Text';
import { ChangeEvent } from 'react';

interface CustomCouponSectionProps {
  label: string;
  uploadImageInputId: string;
  imgFileUrl: string;
  isCustom: boolean;
  uploadImageFile: (e: ChangeEvent<HTMLInputElement>) => void;
}

const CustomCouponSection = ({
  label,
  uploadImageInputId,
  imgFileUrl,
  isCustom,
  uploadImageFile,
}: CustomCouponSectionProps) => {
  return (
    <>
      <CouponPreviewHeader>
        <label>{label}</label>
        <ImageUpLoadInput
          id={uploadImageInputId}
          type="file"
          accept="image/jpg,image/png,image/jpeg,image/gif"
          onChange={uploadImageFile}
        />
        {isCustom && (
          <ImageUpLoadInputLabel htmlFor={uploadImageInputId}>
            이미지 업로드 +
          </ImageUpLoadInputLabel>
        )}
      </CouponPreviewHeader>
      <Spacing $size={12} />
      <PreviewImageWrapper $height={150} $width={270}>
        <PreviewImage src={imgFileUrl} $height={150} $width={270} />
      </PreviewImageWrapper>
      <Spacing $size={8} />
      <Text>270 * 150 의 이미지만 올려주세요.</Text>
    </>
  );
};

export default CustomCouponSection;
