import { CouponPreviewHeader, CustomCouponLabel } from './style';
import { Spacing } from '../../style/layout/common';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
  PreviewImage,
  PreviewImageWrapper,
} from '../../pages/Admin/CustomCouponDesign/style';
import Text from '../Text';
import { ChangeEvent } from 'react';
import CouponLoadImg from '../../assets/coupon_loading_img.png';
import CouponPreviewImg from '../../assets/coupon_preview.png';
import { selectImgUrl, useLoadImg } from '../../hooks/useLoadImg';

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
  const { isLoading, handleImageLoad } = useLoadImg(imgFileUrl);

  return (
    <>
      <CouponPreviewHeader>
        <CustomCouponLabel>💳 {label}</CustomCouponLabel>
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
        <PreviewImage
          src={selectImgUrl(imgFileUrl, CouponPreviewImg, CouponLoadImg, isLoading)}
          $height={150}
          $width={270}
          onLoad={handleImageLoad}
        />
      </PreviewImageWrapper>
      <Spacing $size={8} />
      <Text>270 * 150 의 이미지만 올려주세요.</Text>
    </>
  );
};

export default CustomCouponSection;
