import { styled } from 'styled-components';

export const ChoiceTemplateContainer = styled.div`
  border-left: 1px solid ${({ theme }) => theme.colors.black};
`;

export const SampleImageContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 42px;
  width: 100%;
  align-items: center;
  gap: 42px;
`;

interface StyledSampleImageProps {
  $templateType: string;
  $isSelected: boolean;
}

export const SampleImg = styled.img<StyledSampleImageProps>`
  width: ${({ $templateType }) => ($templateType === '스탬프' ? '50px' : '270px')};
  height: ${({ $templateType }) => ($templateType === '스탬프' ? '50px' : '150px')};
  ${({ theme, $isSelected }) => ($isSelected ? `5px solid ${theme.colors.black}` : '')}
`;
