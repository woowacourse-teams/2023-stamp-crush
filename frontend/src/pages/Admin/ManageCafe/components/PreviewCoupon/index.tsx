import { useRedirectRegisterPage } from '../../../../../hooks/useRedirectRegisterPage';
import useGetCouponDesign from '../../hooks/useGetCouponDesign';
import { PreviewBackImage, PreviewCouponBackImage, SkeletonBackImg } from './style';

const PreviewCoupon = () => {
  const cafeId = useRedirectRegisterPage();
  const { data: couponDesignData, status: couponDesignStatus } = useGetCouponDesign(cafeId);

  if (couponDesignStatus === 'loading')
    return (
      <PreviewCouponBackImage>
        <SkeletonBackImg />
      </PreviewCouponBackImage>
    );

  if (couponDesignStatus === 'error')
    return (
      <PreviewCouponBackImage>
        <p>쿠폰 이미지를 불러오는 데 실패했습니다.</p>
      </PreviewCouponBackImage>
    );

  return (
    <PreviewCouponBackImage>
      {couponDesignData.backImageUrl ? (
        <PreviewBackImage src={couponDesignData.backImageUrl} />
      ) : (
        <p>쿠폰 뒷면 이미지가 들어갈 공간입니다.</p>
      )}
    </PreviewCouponBackImage>
  );
};

export default PreviewCoupon;
