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
import CouponPreviewSection from '../CouponPreviewSection';
import { useLocation } from 'react-router-dom';
import { useMutateCouponPolicy } from '../hooks/useMutateCouponPolicy';
import CouponPreviewImg from '../../../../assets/coupon_preview.png';
import StampPreviewImg from '../../../../assets/stamp_preview.png';
import { useRedirectRegisterPage } from '../../../../hooks/useRedirectRegisterPage';
import { CouponDesignLocation, StampCoordinate } from '../../../../types/domain/coupon';

const CustomCouponDesign = () => {
  const cafeId = useRedirectRegisterPage();
  const { state } = useLocation() as unknown as CouponDesignLocation;
  const [frontImageUrl, uploadFrontImageUrl] = useUploadImage(CouponPreviewImg);
  const [backImageUrl, uploadBackImageUrl] = useUploadImage(CouponPreviewImg);
  const [stampCoordinates, setStampCoordinates] = useState<StampCoordinate[]>([]);
  const [stampImageUrl, uploadStampImage] = useUploadImage(StampPreviewImg);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const isCustom = state.createdType === 'custom';
  const maxStampCount = parseStampCount(state.stampCount);
  const { mutate } = useMutateCouponPolicy();

  const changeCouponDesignAndPolicy = () => {
    if (
      stampCoordinates.length !== maxStampCount ||
      !frontImageUrl ||
      !stampImageUrl ||
      !backImageUrl
    ) {
      alert('모두 설정해주세요.');
      return;
    }

    const couponSettingBody = {
      frontImageUrl,
      backImageUrl,
      stampImageUrl,
      coordinates: stampCoordinates,
      reward: state.reward,
      expirePeriod: parseExpireDate(state.expirePeriod.value),
      maxStampCount: maxStampCount,
    };

    mutate({ params: { cafeId }, body: couponSettingBody });
  };

  const customStampPosition = () => {
    if (!stampImageUrl || !backImageUrl) {
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
                imgFileUrl={frontImageUrl}
                isCustom={isCustom}
                uploadImageFile={uploadFrontImageUrl}
              />
              <Spacing $size={32} />
              <CustomCouponSection
                label="쿠폰 뒷면"
                uploadImageInputId="coupon-back-image-input"
                imgFileUrl={backImageUrl}
                isCustom={isCustom}
                uploadImageFile={uploadBackImageUrl}
              />
            </div>
            <RowSpacing $size={72} />
            <div>
              <CouponPreviewSection
                isShown={true}
                frontImageUrl={frontImageUrl}
                backImageUrl={backImageUrl}
                stampImageUrl={stampImageUrl}
                stampCount={maxStampCount}
                coordinates={stampCoordinates}
              />
              <Spacing $size={32} />
              <CustomStampSection
                label="스탬프"
                uploadImageInputId="stamp-image-input"
                imgFileUrl={stampImageUrl}
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
        backImgFileUrl={backImageUrl}
        stampImgFileUrl={stampImageUrl}
        maxStampCount={maxStampCount}
        setIsOpen={setIsModalOpen}
        setStampCoordinates={setStampCoordinates}
      />
    </>
  );
};

export default CustomCouponDesign;
