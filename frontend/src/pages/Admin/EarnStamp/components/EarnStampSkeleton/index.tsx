import { SkeletonContainer, SkeletonCoupon, SkeletonHeader, SkeletonSubHeader } from './style';

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
