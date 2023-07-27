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

export const PreviewCouponContainer = styled.div``;

interface StyledImageSizeProps {
  $width: number;
  $height: number;
  $opacity?: number;
}

export const PreviewImageWrapper = styled.div<StyledImageSizeProps>`
  position: relative;
  width: ${({ $width }) => `${$width}px`};
  height: ${({ $height }) => `${$height}px`};
  border: 1px dotted ${({ theme }) => theme.colors.black};
`;

export const PreviewImage = styled.img<StyledImageSizeProps>`
  width: ${({ $width }) => `${$width}px`};
  height: ${({ $height }) => `${$height}px`};
  opacity: ${({ $opacity }) => ($opacity ? $opacity : '1')};
  object-fit: cover;
`;

export const ImageUpLoadInputLabel = styled.label`
  color: ${({ theme }) => theme.colors.point};
  cursor: pointer;
  &:hover {
    color: tomato;
  }
`;

export const ImageUpLoadInput = styled.input`
  display: none;
`;

export const PreviewLabel = styled.label``;
