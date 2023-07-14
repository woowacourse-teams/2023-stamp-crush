import React from 'react';
import { CustomCouponDesignContainer } from './style';
import { Spacing, SubTitle, Title } from '../../../style/layout/common';
import CustomCouponSection from './CustomCouponSection';

const SectionSpacing = () => <Spacing $size={40} />;

const CustomCouponDesign = () => {
  return (
    <CustomCouponDesignContainer>
      <Title>쿠폰 제작 및 변경</Title>
      <SectionSpacing />
      <SubTitle>예상 쿠폰 이미지</SubTitle>
      <Spacing $size={20} />
      <CustomCouponSection label="쿠폰 앞면" uploadImageInputId="coupon-front-image-input" />
      <Spacing $size={32} />
      <CustomCouponSection label="쿠폰 뒷면" uploadImageInputId="coupon-back-image-input" />
    </CustomCouponDesignContainer>
  );
};

export default CustomCouponDesign;
