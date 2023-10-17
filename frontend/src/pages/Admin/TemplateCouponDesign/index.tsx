import { useLocation } from 'react-router-dom';
import Text from '../../../components/Text';
import { Spacing } from '../../../style/layout/common';
import { CustomCouponDesignContainer } from '../CustomCouponDesign/style';
import useUploadImage from '../../../hooks/useUploadImage';
import { useState } from 'react';
import { parseExpireDate, parseStampCount } from '../../../utils';
import Button from '../../../components/Button';
import CouponPreviewImg from '../../../assets/coupon_preview.png';
import StampPreviewImg from '../../../assets/stamp_preview.png';
import { CouponSettingReqBody } from '../../../types/api/request';
import { CouponDesignLocation, StampCoordinate } from '../../../types/domain/coupon';
import { ImageUploadContainer, PreviewContainer, TemplateTabsContainer } from './style';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import { useMutateCouponPolicy } from '../../../hooks/useMutateCouponPolicy';
import CustomCouponSection from '../../../components/CustomCouponSection';
import CustomStampSection from '../../../components/CustomStampSection';
import CouponPreviewSection from '../../../components/CouponPreviewSection';
import ChoiceTemplate from '../../../components/ChoiceTemplate';

const TemplateCouponDesign = () => {
  const cafeId = useRedirectRegisterPage();
  const { state } = useLocation() as unknown as CouponDesignLocation;
  const [frontImageUrl, uploadFrontImageUrl, setFrontImageUrl] = useUploadImage(CouponPreviewImg);
  const [backImageUrl, uploadBackImageUrl, setBackImageUrl] = useUploadImage(CouponPreviewImg);
  const [stampCoordinates, setStampCoordinates] = useState<StampCoordinate[]>([]);
  const [stampImageUrl, uploadStampImageUrl, setStampImageUrl] = useUploadImage(StampPreviewImg);
  const { mutate } = useMutateCouponPolicy();
  const isCustom = state.createdType === 'custom';
  const maxStampCount = parseStampCount(state.stampCount);

  const changeCouponDesignAndPolicy = () => {
    if (stampCoordinates.length === 0 || !frontImageUrl || !stampImageUrl || !backImageUrl) {
      alert('이미지를 모두 선택해주세요.');
      return;
    }

    const couponSettingBody: CouponSettingReqBody = {
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

  return (
    <>
      <CustomCouponDesignContainer>
        <Text variant="pageTitle">쿠폰 제작 및 변경 - 템플릿</Text>
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
            <Spacing $size={32} />
            <CustomStampSection
              label="스탬프"
              uploadImageInputId="stamp-image-input"
              imgFileUrl={stampImageUrl}
              isCustom={isCustom}
              uploadImageFile={uploadStampImageUrl}
            />
          </div>
          <PreviewContainer>
            <CouponPreviewSection
              isShown={true}
              frontImageUrl={frontImageUrl}
              backImageUrl={backImageUrl}
              stampImageUrl={stampImageUrl}
              stampCount={maxStampCount}
              coordinates={stampCoordinates}
            />
            <Button variant="primary" size="medium" onClick={changeCouponDesignAndPolicy}>
              저장하기
            </Button>
          </PreviewContainer>
        </ImageUploadContainer>
        <TemplateTabsContainer>
          <ChoiceTemplate
            frontImageUrl={frontImageUrl}
            backImageUrl={backImageUrl}
            stampImageUrl={stampImageUrl}
            setFrontImageUrl={setFrontImageUrl}
            setBackImageUrl={setBackImageUrl}
            setStampImageUrl={setStampImageUrl}
            setStampCoordinates={setStampCoordinates}
          />
        </TemplateTabsContainer>
      </CustomCouponDesignContainer>
    </>
  );
};

export default TemplateCouponDesign;
