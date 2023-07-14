import { styled } from 'styled-components';

export const CustomCouponSectionContainer = styled.div``;

export const CouponPreviewHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const CouponPreviewLabel = styled.h4``;

export const ImageUpLoadInput = styled.input`
  display: none;
`;

export const ImageUpLoadInputLabel = styled.label`
  color: ${({ theme }) => theme.colors.point};
  cursor: pointer;
`;

export const PreviewImageWrapper = styled.div`
  width: 270px;
  height: 150px;
  border: 1px dotted ${({ theme }) => theme.colors.black};
`;

export const PreviewImage = styled.img`
  width: 270px;
  height: 150px;
`;
