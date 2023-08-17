import { useLocation } from 'react-router-dom';
import Text from '../../../../components/Text';
import { RowSpacing, Spacing } from '../../../../style/layout/common';
import {
  CustomCouponDesignContainer,
  ImageUploadContainer,
  SaveButtonWrapper,
} from '../CustomCouponDesign/style';
import useUploadImage from '../../../../hooks/useUploadImage';
import { CouponDesignLocation, StampCoordinate } from '../../../../types';
import { useState } from 'react';
import { parseExpireDate, parseStampCount } from '../../../../utils';
import CustomCouponSection from '../CustomCouponSection';
import CouponPreviewSection from '../CouponPreviewSection';
import CustomStampSection from '../CustomStampSection';
import Button from '../../../../components/Button';
import ChoiceTemplate from '../ChoiceTemplate';
import { useMutateCouponPolicy } from '../hooks/useMutateCouponPolicy';
import CouponPreviewImg from '../../../../assets/coupon_preview.png';
import StampPreviewImg from '../../../../assets/stamp_preview.png';
import { CouponSettingReqBody } from '../../../../types/api';
import { useRedirectRegisterPage } from '../../../../hooks/useRedirectRegisterPage';

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
      <Spacing $size={40} />
      <CustomCouponDesignContainer>
        <div>
          <Text variant="pageTitle">쿠폰 템플릿으로 제작</Text>
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
                uploadImageFile={uploadStampImageUrl}
              />
            </div>
          </ImageUploadContainer>
          <Spacing $size={40} />
          <SaveButtonWrapper>
            <Button variant="primary" size="medium" onClick={changeCouponDesignAndPolicy}>
              저장하기
            </Button>
          </SaveButtonWrapper>
        </div>
        <RowSpacing $size={100} />
        <ChoiceTemplate
          frontImageUrl={frontImageUrl}
          backImageUrl={backImageUrl}
          stampImageUrl={stampImageUrl}
          setFrontImageUrl={setFrontImageUrl}
          setBackImageUrl={setBackImageUrl}
          setStampImageUrl={setStampImageUrl}
          setStampCoordinates={setStampCoordinates}
        />
      </CustomCouponDesignContainer>
    </>
  );
};

export default TemplateCouponDesign;
