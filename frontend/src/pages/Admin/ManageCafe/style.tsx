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

export const TextArea = styled.textarea`
  border: 1px solid #aaa;
  border-radius: 4px;
  padding: 10px;
  resize: none;
  height: 100px;
  font-size: 16px;
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
    gap: 10px;
  }
`;

export const PreviewCouponBackImage = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 70px;
  left: 22px;
  width: 270px;
  height: 150px;
  font-size: 16px;
  font-weight: 500;
  background: white;
  border: 3px dotted black;
`;

export const RestrictionLabel = styled.label<{ $isExceed: boolean }>`
  color: ${({ $isExceed }) => ($isExceed ? 'tomato' : '#aaa')};
  margin-left: auto;
`;

export const PreviewBackImage = styled.img`
  width: 100%;
  height: 100%;
`;
