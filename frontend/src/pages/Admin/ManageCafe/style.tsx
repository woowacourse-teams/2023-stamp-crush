import styled from 'styled-components';

export const PageContainer = styled.main`
  display: flex;
  flex-direction: row;
  width: 100vw;
  padding-top: 40px;
  gap: 100px;
`;

export const ManageCafeForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 30%;
  gap: 40px;

  & > button {
    margin-left: auto;
  }
`;

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export const TextArea = styled.textarea`
  border: 1px solid #aaa;
  border-radius: 4px;
  padding: 10px;
  resize: none;
`;

export const PreviewContainer = styled.section`
  display: flex;
  flex-direction: column;
`;

export const PreviewOverviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  align-items: center;
  width: 100%;
  top: 250px;
`;

export const PreviewContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  bottom: 80px;
  left: 50px;
  width: 200px;
  height: 100px;
  gap: 20px;

  :nth-child(n) {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    gap: 5px;
  }
`;
