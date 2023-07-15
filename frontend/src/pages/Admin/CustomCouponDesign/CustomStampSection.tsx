import { CustomStampSectionContainer } from './CustomStampSection.style';
import { Spacing } from '../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
  PreviewLabel,
} from './style';
import useUploadImage from '../../../hooks/useUploadImage';

interface CustomStampSectionProps {
  label: string;
  uploadImageInputId: string;
}
const CustomStampSection = ({ label, uploadImageInputId }: CustomStampSectionProps) => {
  const { imgFileUrl, uploadImageFile } = useUploadImage();

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
