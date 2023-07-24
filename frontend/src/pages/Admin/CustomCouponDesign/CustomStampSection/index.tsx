import { CustomStampSectionContainer } from './style';
import { Spacing } from '../../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
  PreviewLabel,
} from '../style';

interface CustomStampSectionProps {
  label: string;
  uploadImageInputId: string;
  imgFileUrl: string;
  uploadImageFile: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isCustom: boolean;
}

const CustomStampSection = ({
  label,
  uploadImageInputId,
  imgFileUrl,
  uploadImageFile,
  isCustom,
}: CustomStampSectionProps) => {
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
      {isCustom && (
        <ImageUpLoadInputLabel htmlFor={uploadImageInputId}>이미지 업로드 +</ImageUpLoadInputLabel>
      )}
      <Spacing $size={8} />
      <PreviewImageWrapper $height={50} $width={50}>
        <PreviewImage src={imgFileUrl} $height={50} $width={50} />
      </PreviewImageWrapper>
    </CustomStampSectionContainer>
  );
};

export default CustomStampSection;
