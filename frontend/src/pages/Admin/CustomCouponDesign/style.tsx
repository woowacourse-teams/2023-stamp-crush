import { styled } from 'styled-components';
import { PageContainer } from '../../../style/layout/common';

export const CustomCouponDesignContainer = styled(PageContainer)`
  display: flex;
`;

export const ImageUploadContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const CouponContainer = styled.div``;

export const SaveButtonWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  width: 100%;
`;

export const C예상쿠폰이미지컨테이너 = styled.div``;

export const C템플릿고르기컨테이너 = styled.div`
  border-left: 1px solid ${({ theme }) => theme.colors.black};
`;
