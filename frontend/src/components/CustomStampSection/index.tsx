import { ChangeEvent } from 'react';
import { Spacing } from '../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../../pages/Admin/CustomCouponDesign/style';

import { selectImgUrl, useLoadImg } from '../../hooks/useLoadImg';
import { CouponPreviewHeader, CustomCouponLabel } from '../CustomCouponSection/style';

import StampLoadImg from '../../assets/stamp_load_img.png';
import StampPreviewImg from '../../assets/stamp_preview.png';

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
      <CouponPreviewHeader>
        <CustomCouponLabel>💮 {label}</CustomCouponLabel>
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
      <Spacing $size={8} />
      <PreviewImageWrapper $height={50} $width={50}>
        <PreviewImage
          src={selectImgUrl(imgFileUrl, StampPreviewImg, StampLoadImg, isLoading)}
          $height={50}
          $width={50}
          onLoad={handleImageLoad}
        />
      </PreviewImageWrapper>
    </>
  );
};

export default CustomStampSection;
