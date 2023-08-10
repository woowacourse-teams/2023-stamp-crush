import { styled } from 'styled-components';
import { PageContainer } from '../../../style/layout/common';

export const CustomCouponDesignContainer = styled(PageContainer)`
  display: flex;
`;

export const ImageUploadContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const SaveButtonWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  width: 100%;
`;

// FIXME: 빈 스타일드 컴포넌트 수정 요망
export const PreviewCouponContainer = styled.div``;

export const CouponContainer = styled.div``;

export const PreviewImageWrapper = styled.div<{
  $width: number;
  $height: number;
  $opacity?: number;
}>`
  position: relative;
  width: ${({ $width }) => `${$width}px`};
  height: ${({ $height }) => `${$height}px`};
  border: 1px dotted ${({ theme }) => theme.colors.black};
`;

export const PreviewImage = styled.img<{ $width: number; $height: number; $opacity?: number }>`
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

export const StampImageSelector = styled.div`
  display: flex;
  flex-direction: column;
`;

export const StampCustomButtonWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  width: 100%;
`;
