import styled from 'styled-components';

export const PageContainer = styled.main`
  display: flex;
  justify-content: space-between;

  width: 90%;
  padding-top: 40px;
`;

export const StepTitle = styled.p`
  font-size: 18px;
  font-weight: 600;
  color: #222;
  margin-bottom: 10px;
`;

export const ManageCafeForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 40%;
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

export const PreviewContainer = styled.section`
  display: flex;
  flex-direction: column;
  border-radius: 20px;
  gap: 10px;
`;

export const PreviewOverviewContainer = styled.div`
  display: flex;
  flex-direction: column;

  position: absolute;
  align-items: center;
  width: 100%;
  height: 130px;
  top: 250px;
  padding: 0 10px;
  gap: 10px;
  word-break: break-all;
  overflow: hidden;

  :nth-child(2) {
    overflow: scroll;
  }
`;
