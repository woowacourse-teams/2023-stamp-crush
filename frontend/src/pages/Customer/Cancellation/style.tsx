import styled from 'styled-components';

export const ContentContainer = styled.div`
  padding: 2rem;
`;

export const Title = styled.h1`
  font-size: 1.6rem;
  font-weight: 800;
  padding-bottom: 1rem;
`;

export const Strong = styled.strong`
  font-weight: 1.3rem;
  font-weight: 900;
`;

export const SubParagraph = styled.p`
  color: ${({ theme }) => theme.colors.gray400};
`;

export const Heading2 = styled.h2`
  font-size: 1.4rem;
  font-weight: 900;
  padding: 1rem 0;
`;

export const CheckParagraph = styled.div`
  display: flex;
  gap: 1rem;
  padding: 2rem 0;
`;

export const Paragraph = styled.p`
  line-height: 1.6rem;
`;

export const CancellationButton = styled.button<{ isConfirm: boolean }>`
  color: ${({ theme, isConfirm }) => (isConfirm ? theme.colors.white : theme.colors.gray400)};
  background-color: ${({ theme, isConfirm }) => (isConfirm ? '#e55555' : theme.colors.gray200)};
  border-radius: 5px;
  padding: 1rem 0;
  width: 100%;
`;
