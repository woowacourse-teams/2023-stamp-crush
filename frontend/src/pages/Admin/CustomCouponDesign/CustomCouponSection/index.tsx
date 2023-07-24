import { CustomCouponSectionContainer, CouponPreviewHeader } from './style';
import { Spacing } from '../../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
  PreviewLabel,
} from '../style';
import Text from '../../../../components/Text';

interface CustomCouponSectionProps {
  label: string;
  uploadImageInputId: string;
  imgFileUrl: string;
  uploadImageFile: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isCustom: boolean;
}

const CustomCouponSection = ({
  label,
  uploadImageInputId,
  imgFileUrl,
  uploadImageFile,
  isCustom,
}: CustomCouponSectionProps) => {
  return (
    <CustomCouponSectionContainer>
      <CouponPreviewHeader>
        <PreviewLabel>{label}</PreviewLabel>
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
    </CustomCouponSectionContainer>
  );
};

export default CustomCouponSection;
