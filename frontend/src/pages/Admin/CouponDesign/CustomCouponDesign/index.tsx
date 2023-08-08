import {
  CustomCouponDesignContainer,
  ImageUploadContainer,
  SaveButtonWrapper,
  StampCustomButtonWrapper,
} from './style';
import { RowSpacing, Spacing } from '../../../../style/layout/common';
import CustomCouponSection from '../CustomCouponSection';
import CustomStampSection from '../CustomStampSection';
import Button from '../../../../components/Button';
import useUploadImage from '../../../../hooks/useUploadImage';
import { useState } from 'react';
import { parseExpireDate, parseStampCount } from '../../../../utils';
import Text from '../../../../components/Text';
import StampCustomModal from './StampCustomModal';
import { CouponSettingReq } from '../../../../types/api';
import { CouponDesignLocation, StampCoordinate } from '../../../../types';
import CouponPreviewSection from '../CouponPreviewSection';
import { useLocation } from 'react-router-dom';
import { useMutateCouponPolicy } from '../hooks/useMutateCouponPolicy';
import CouponPreviewImg from '../../../../assets/coupon_preview.png';
import StampPreviewImg from '../../../../assets/stamp_preview.png';

const CustomCouponDesign = () => {
  const { state } = useLocation() as unknown as CouponDesignLocation;
  const [frontImage, uploadFrontImage] = useUploadImage(CouponPreviewImg);
  const [backImage, uploadBackImage] = useUploadImage(CouponPreviewImg);
  const [stampCoordinates, setStampCoordinates] = useState<StampCoordinate[]>([]);
  const [stampImage, uploadStampImage] = useUploadImage(StampPreviewImg);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const isCustom = state.createdType === 'custom';
  const maxStampCount = parseStampCount(state.stampCount);
  const { mutate } = useMutateCouponPolicy();

  const changeCouponDesignAndPolicy = () => {
    if (stampCoordinates.length !== maxStampCount || !frontImage || !stampImage || !backImage) {
      alert('모두 설정해주세요.');
      return;
    }

    const couponSettingBody: CouponSettingReq = {
      frontImageUrl: frontImage,
      backImageUrl: backImage,
      stampImageUrl: stampImage,
      coordinates: stampCoordinates,
      reward: state.reward,
      expirePeriod: parseExpireDate(state.expirePeriod.value),
      maxStampCount: maxStampCount,
    };

    mutate(couponSettingBody);
  };

  const customStampPosition = () => {
    if (!stampImage || !backImage) {
      alert('먼저 쿠폰 뒷면, 스탬프 이미지를 업로드해주세요.');
      return;
    }

    setIsModalOpen(true);
  };

  return (
    <>
      <Spacing $size={40} />
      <CustomCouponDesignContainer>
        <div>
          <Text variant="pageTitle">쿠폰 제작 및 변경</Text>
          <Spacing $size={40} />
          <Text variant="subTitle">예상 쿠폰 이미지</Text>
          <Spacing $size={20} />
          <ImageUploadContainer>
            <div>
              <CustomCouponSection
                label="쿠폰 앞면"
                uploadImageInputId="coupon-front-image-input"
                imgFileUrl={frontImage}
                isCustom={isCustom}
                uploadImageFile={uploadFrontImage}
              />
              <Spacing $size={32} />
              <CustomCouponSection
                label="쿠폰 뒷면"
                uploadImageInputId="coupon-back-image-input"
                imgFileUrl={backImage}
                isCustom={isCustom}
                uploadImageFile={uploadBackImage}
              />
            </div>
            <RowSpacing $size={72} />
            <div>
              <CouponPreviewSection
                isShown={true}
                frontImageUrl={frontImage}
                backImageUrl={backImage}
                stampImageUrl={stampImage}
                stampCount={maxStampCount}
                coordinates={stampCoordinates}
              />
              <Spacing $size={32} />
              <CustomStampSection
                label="스탬프"
                uploadImageInputId="stamp-image-input"
                imgFileUrl={stampImage}
                isCustom={isCustom}
                uploadImageFile={uploadStampImage}
              />
            </div>
          </ImageUploadContainer>
          <StampCustomButtonWrapper>
            <Button variant="secondary" size="medium" onClick={customStampPosition}>
              스탬프 위치 커스텀하기
            </Button>
          </StampCustomButtonWrapper>
          <Spacing $size={40} />
          <SaveButtonWrapper>
            <Button variant="primary" size="medium" onClick={changeCouponDesignAndPolicy}>
              저장하기
            </Button>
          </SaveButtonWrapper>
        </div>
        <RowSpacing $size={100} />
      </CustomCouponDesignContainer>
      <StampCustomModal
        isOpen={isModalOpen}
        stampCoordinates={stampCoordinates}
        backImgFileUrl={backImage}
        stampImgFileUrl={stampImage}
        maxStampCount={maxStampCount}
        setIsOpen={setIsModalOpen}
        setStampCoordinates={setStampCoordinates}
      />
    </>
  );
};

export default CustomCouponDesign;
