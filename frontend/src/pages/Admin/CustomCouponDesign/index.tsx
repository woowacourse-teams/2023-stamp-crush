import {
  CouponContainer,
  CustomCouponDesignContainer,
  PreviewCouponContainer,
  ImageUploadContainer,
  SaveButtonWrapper,
} from './style';
import { RowSpacing, Spacing, SubTitle, Title } from '../../../style/layout/common';
import CustomCouponSection from './CustomCouponSection';
import CustomStampSection from './CustomStampSection';
import Button from '../../../components/Button';
import { useLocation } from 'react-router-dom';
import ChoiceTemplate from './ChoiceTemplate';
import useUploadImage from '../../../hooks/useUploadImage';

const SectionSpacing = () => <Spacing $size={40} />;

const CustomCouponDesign = () => {
  const location = useLocation();
  const prevState = { ...location.state };
  const [frontImage, uploadFrontImage, setFrontImage] = useUploadImage();
  const [backImage, uploadBackImage, setBackImage] = useUploadImage();
  const [stampImage, uploadStampImage, setStampImage] = useUploadImage();

  return (
    <CustomCouponDesignContainer>
      <PreviewCouponContainer>
        <Title>쿠폰 제작 및 변경</Title>
        <SectionSpacing />
        <SubTitle>예상 쿠폰 이미지</SubTitle>
        <Spacing $size={20} />
        <ImageUploadContainer>
          <CouponContainer>
            <CustomCouponSection
              label="쿠폰 앞면"
              uploadImageInputId="coupon-front-image-input"
              imgFileUrl={frontImage}
              uploadImageFile={uploadFrontImage}
            />
            <Spacing $size={32} />
            <CustomCouponSection
              label="쿠폰 뒷면"
              uploadImageInputId="coupon-back-image-input"
              imgFileUrl={backImage}
              uploadImageFile={uploadBackImage}
            />
          </CouponContainer>
          <RowSpacing $size={72} />
          <CustomStampSection
            label="스탬프"
            uploadImageInputId="stamp-image-input"
            imgFileUrl={stampImage}
            uploadImageFile={uploadStampImage}
          />
        </ImageUploadContainer>
        <Spacing $size={40} />
        <SaveButtonWrapper>
          <Button variant="primary" size="medium">
            저장하기
          </Button>
        </SaveButtonWrapper>
      </PreviewCouponContainer>
      <RowSpacing $size={100} />
      <ChoiceTemplate
        frontImage={frontImage}
        backImage={backImage}
        stampImage={stampImage}
        setFrontImage={setFrontImage}
        setBackImage={setBackImage}
        setStampImage={setStampImage}
      />
    </CustomCouponDesignContainer>
  );
};

export default CustomCouponDesign;
