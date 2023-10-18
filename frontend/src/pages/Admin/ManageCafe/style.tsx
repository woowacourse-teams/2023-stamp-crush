import styled from 'styled-components';
import { Skeleton } from '../../../components/Skeleton/style';

export const StepTitle = styled.p`
  font-size: 18px;
  font-weight: 600;
  color: #222;
  margin-bottom: 10px;
`;

export const ManageCafeForm = styled.form`
  display: grid;
  grid-template-rows: repeat(6, auto);
  position: relative;
  width: fit-content;
  height: fit-content;
  gap: 40px;

  & > button {
    margin-left: auto;
  }
`;

export const Wrapper = styled.div`
  display: flex;
  width: 100%;
  max-width: 400px;
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

export const ManageCafeGridContainer = styled.div`
  display: grid;
  width: 100%;
  height: 90%;
  grid-template-columns: repeat(2, 400px 400px);
  padding-top: 40px;
  gap: 40px;
`;

export const DefaultCafeImage = styled.div`
  width: 100%;
  height: 100%;
  background: white;
`;

export const SkeletonHeader = styled(Skeleton)`
  width: 100px;
  height: 24px;
`;

export const SkeletonPreview = styled(Skeleton)`
  width: 312px;
  height: 594px;
`;
