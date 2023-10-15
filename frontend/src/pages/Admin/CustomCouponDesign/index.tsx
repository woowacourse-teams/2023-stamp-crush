import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import Button from '../../../components/Button';
import Text from '../../../components/Text';
import CouponPreviewSection from '../../../components/CouponPreviewSection';
import StampCustomModal from './components/StampCustomModal';
import useUploadImage from '../../../hooks/useUploadImage';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { parseExpireDate, parseStampCount } from '../../../utils';
import { CouponDesignLocation, StampCoordinate } from '../../../types/domain/coupon';
import { CustomCouponDesignContainer, ImageUploadContainer, SaveButtonWrapper } from './style';
import CouponPreviewImg from '../../../assets/coupon_preview.png';
import StampPreviewImg from '../../../assets/stamp_preview.png';
import CustomCouponSection from '../../../components/CustomCouponSection';
import CustomStampSection from '../../../components/CustomStampSection';
import { useMutateCouponPolicy } from '../../../hooks/useMutateCouponPolicy';

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
      <CustomCouponDesignContainer>
        <Text variant="pageTitle">쿠폰 제작 및 변경 - 커스텀</Text>
        <ImageUploadContainer>
          <div>
            <CustomCouponSection
              label="쿠폰 앞면"
              uploadImageInputId="coupon-front-image-input"
              imgFileUrl={frontImageUrl}
              isCustom={isCustom}
              uploadImageFile={uploadFrontImageUrl}
            />
            <Spacing $size={40} />
            <CustomCouponSection
              label="쿠폰 뒷면"
              uploadImageInputId="coupon-back-image-input"
              imgFileUrl={backImageUrl}
              isCustom={isCustom}
              uploadImageFile={uploadBackImageUrl}
            />
          </div>
          <div>
            <CustomStampSection
              label="스탬프"
              uploadImageInputId="stamp-image-input"
              imgFileUrl={stampImageUrl}
              isCustom={isCustom}
              uploadImageFile={uploadStampImage}
            />
            <Spacing $size={40} />
            <Button variant="secondary" size="medium" onClick={customStampPosition}>
              스탬프 위치 설정하기
            </Button>
          </div>
          <CouponPreviewSection
            isShown={true}
            frontImageUrl={frontImageUrl}
            backImageUrl={backImageUrl}
            stampImageUrl={stampImageUrl}
            stampCount={maxStampCount}
            coordinates={stampCoordinates}
          />
        </ImageUploadContainer>
        <SaveButtonWrapper>
          <Button variant="primary" size="medium" onClick={changeCouponDesignAndPolicy}>
            저장하기
          </Button>
        </SaveButtonWrapper>
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
