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
  overflow: scroll;
  height: 80vh;
`;

export const WarnMsg = styled.p`
  color: ${({ theme }) => theme.colors.gray400};
`;

export const WarnMsgBox = styled.div`
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
`;

export const SampleImg = styled.img<{
  $templateType: string;
  $isSelected: boolean;
}>`
  cursor: pointer;
  width: ${({ $templateType }) => ($templateType === '스탬프' ? '50px' : '270px')};
  height: ${({ $templateType }) => ($templateType === '스탬프' ? '50px' : '150px')};
  border: ${({ theme, $isSelected }) => $isSelected && `5px solid ${theme.colors.main}`};
`;
