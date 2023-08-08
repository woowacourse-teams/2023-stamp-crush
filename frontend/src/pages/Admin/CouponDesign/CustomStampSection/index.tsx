import { ChangeEvent } from 'react';
import { Spacing } from '../../../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../CustomCouponDesign/style';
import { useLoadImg } from '../hooks/useLoadImg';
import StampLoadImg from '../../../../assets/stamp_load_img.png';

interface CustomStampSectionProps {
  label: string;
  uploadImageInputId: string;
  imgFileUrl: string;
  isCustom: boolean;
  uploadImageFile: (e: ChangeEvent<HTMLInputElement>) => void;
}

const CustomStampSection = ({
  label,
  uploadImageInputId,
  imgFileUrl,
  isCustom,
  uploadImageFile,
}: CustomStampSectionProps) => {
  const { isLoading, handleImageLoad } = useLoadImg(imgFileUrl);

  return (
    <>
      <label>{label}</label>
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
        <PreviewImage
          src={isLoading ? StampLoadImg : imgFileUrl}
          $height={50}
          $width={50}
          onLoad={handleImageLoad}
        />
      </PreviewImageWrapper>
    </>
  );
};

export default CustomStampSection;
