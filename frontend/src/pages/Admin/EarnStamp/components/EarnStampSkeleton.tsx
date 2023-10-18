import styled, { keyframes } from 'styled-components';

const skeletonLoading = keyframes`
  0% {
    background-position: -200% 0;
  }
  100% {
    background-position: 200% 0;
  }
`;

const SkeletonContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 190px;
`;

const Skeleton = styled.div`
  background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
  background-size: 200% 100%;
  animation: ${skeletonLoading} 1.5s infinite;
  border-radius: 4px;
`;

const SkeletonHeader = styled(Skeleton)`
  width: 150px;
  height: 20px;
  margin-bottom: 10px;
`;

const SkeletonSubHeader = styled(SkeletonHeader)`
  width: 100px;
`;

const SkeletonCoupon = styled(Skeleton)`
  width: 270px;
  height: 150px;
  margin-top: 20px;
`;

const EarnStampSkeleton = () => {
  return (
    <SkeletonContainer>
      <SkeletonHeader />
      <SkeletonSubHeader />
      <SkeletonCoupon />
    </SkeletonContainer>
  );
};

export default EarnStampSkeleton;
