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
import { CouponSettingReq } from '../../../../types/api';
import { parseExpireDate, parseStampCount } from '../../../../utils';
import CustomCouponSection from '../CustomCouponSection';
import CouponPreviewSection from '../CouponPreviewSection';
import CustomStampSection from '../CustomStampSection';
import Button from '../../../../components/Button';
import ChoiceTemplate from '../ChoiceTemplate';
import { useMutateCouponPolicy } from '../hooks/useMutateCouponPolicy';

const TemplateCouponDesign = () => {
  const { state } = useLocation() as unknown as CouponDesignLocation;
  const [frontImage, uploadFrontImage, setFrontImage] = useUploadImage();
  const [backImage, uploadBackImage, setBackImage] = useUploadImage();
  const [stampCoordinates, setStampCoordinates] = useState<StampCoordinate[]>([]);
  const [stampImage, uploadStampImage, setStampImage] = useUploadImage();
  const { mutate } = useMutateCouponPolicy();
  const isCustom = state.createdType === 'custom';
  const maxStampCount = parseStampCount(state.stampCount);

  const changeCouponDesignAndPolicy = () => {
    if (stampCoordinates.length === 0 || !frontImage || !stampImage || !backImage) {
      alert('이미지를 모두 선택해주세요.');
      return;
    }

    const couponSettingBody: CouponSettingReq = {
      frontImageUrl: frontImage,
      backImageUrl: backImage,
      stampImageUrl: stampImage,
      coordinates: stampCoordinates,
      reward: state.reward,
      expirePeriod: parseExpireDate(state.expireSelect.value),
      maxStampCount: maxStampCount,
    };

    mutate(couponSettingBody);
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
          <Spacing $size={40} />
          <SaveButtonWrapper>
            <Button variant="primary" size="medium" onClick={changeCouponDesignAndPolicy}>
              저장하기
            </Button>
          </SaveButtonWrapper>
        </div>
        <RowSpacing $size={100} />
        <ChoiceTemplate
          frontImage={frontImage}
          backImage={backImage}
          stampImage={stampImage}
          setFrontImage={setFrontImage}
          setBackImage={setBackImage}
          setStampImage={setStampImage}
          setStampCoordinates={setStampCoordinates}
        />
      </CustomCouponDesignContainer>
    </>
  );
};

export default TemplateCouponDesign;
