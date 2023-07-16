import React, { useState } from 'react';
import {
  CouponContainer,
  CustomCouponDesignContainer,
  C예상쿠폰이미지컨테이너,
  C템플릿고르기컨테이너,
  ImageUploadContainer,
  SaveButtonWrapper,
} from './style';
import { RowSpacing, Spacing, SubTitle, Title } from '../../../style/layout/common';
import CustomCouponSection from './CustomCouponSection';
import CustomStampSection from './CustomStampSection';
import Button from '../../../components/Button';
import { useLocation } from 'react-router-dom';

const SectionSpacing = () => <Spacing $size={40} />;

const TEMPLATE_OPTIONS = [
  {
    key: 'coupon-front',
    value: '쿠폰(앞)',
  },
  {
    key: 'coupon-back',
    value: '쿠폰(뒤)',
  },
  {
    key: 'stamp',
    value: '스탬프',
  },
];

const CustomCouponDesign = () => {
  const location = useLocation();
  const prevState = { ...location.state };

  const selectTabBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTemplateSelect(e.target.value);
  };
  return (
    <CustomCouponDesignContainer>
      <C예상쿠폰이미지컨테이너>
        <Title>쿠폰 제작 및 변경</Title>
        <SectionSpacing />
        <SubTitle>예상 쿠폰 이미지</SubTitle>
        <Spacing $size={20} />
        <ImageUploadContainer>
          <CouponContainer>
            <CustomCouponSection label="쿠폰 앞면" uploadImageInputId="coupon-front-image-input" />
            <Spacing $size={32} />
            <CustomCouponSection label="쿠폰 뒷면" uploadImageInputId="coupon-back-image-input" />
          </CouponContainer>
          <RowSpacing $size={72} />
          <CustomStampSection label="스탬프" uploadImageInputId="stamp-image-input" />
        </ImageUploadContainer>
        <Spacing $size={40} />
        <SaveButtonWrapper>
          <Button variant="primary" size="medium">
            저장하기
          </Button>
        </SaveButtonWrapper>
      </C예상쿠폰이미지컨테이너>
      <C템플릿고르기컨테이너>
        <TabBar
          name="template-type"
          options={TEMPLATE_OPTIONS}
          selectedValue={templateSelect}
          onChange={selectTabBar}
          height={54}
          width={350}
        />
      </C템플릿고르기컨테이너>
    </CustomCouponDesignContainer>
  );
};

export default CustomCouponDesign;
