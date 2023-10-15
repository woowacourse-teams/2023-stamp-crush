import styled from 'styled-components';
import { PageContainer } from '../../../style/layout/common';

export const CustomCouponDesignContainer = styled(PageContainer)`
  display: flex;
  flex-direction: column;
  margin: 40px 0;
  width: 100%;
  height: 100%;
  position: relative;
`;

export const ImageUploadContainer = styled.div`
  display: flex;
  max-width: 80%;
  justify-content: space-between;
  margin-top: 40px;
`;

export const SaveButtonWrapper = styled.div`
  position: absolute;
  right: 20px;
  bottom: 20px;
`;

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
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  width: 150px;
  height: 40px;
  background: #eee;
  border-radius: 5px;
  border: 1px solid ${({ theme }) => theme.colors.text};
  color: ${({ theme }) => theme.colors.text};
  cursor: pointer;
  &:hover {
    color: tomato;
  }
`;

export const ImageUpLoadInput = styled.input`
  display: none;
`;

export const StampCustomButtonWrapper = styled.div`
  display: flex;
`;
