import { styled } from 'styled-components';

export const CustomStampSectionContainer = styled.div``;

export const StampPreviewLabel = styled.label`
  display: block;
`;

export const PreviewImageWrapper = styled.div`
  width: 50px;
  height: 50px;
  border: 1px dotted ${({ theme }) => theme.colors.black};
`;

export const PreviewImage = styled.img`
  width: 50px;
  height: 50px;
`;
