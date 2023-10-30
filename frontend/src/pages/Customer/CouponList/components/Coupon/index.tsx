import { MouseEvent, MouseEventHandler, useState } from 'react';
import { Coupon as CouponType } from '../../../../../types/domain/coupon';
import { CouponWrapper, ImageForLoading, ProgressBarWrapper, StarIconWrapper } from './style';
import CouponLoading from '../../../../../assets/coupon_load_img_for_customer.png';
import { AiFillStar } from '@react-icons/all-files/ai/AiFillStar';
import { AiOutlineStar } from '@react-icons/all-files/ai/AiOutlineStar';
import ProgressBar from '../../../../../components/ProgressBar';
import Color from 'color-thief-react';
import { addGoogleProxyUrl } from '../../../../../utils';

interface CouponProps {
  coupon: CouponType;
  dataIndex: number;
  isOn: boolean;
  index: number;
  onClick: () => void;
  onClickStar: () => void;
}

const Coupon = ({ coupon, dataIndex, isOn, index, onClick, onClickStar }: CouponProps) => {
  const [isImageLoaded, setIsImageLoaded] = useState(false);

  const toggleIsFavorites = (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    onClickStar();
  };

  const checkLoadImage = () => {
    setIsImageLoaded(true);
  };

  const { frontImageUrl, stampCount, maxStampCount } = coupon.couponInfos[0];

  return (
    <CouponWrapper
      $src={isImageLoaded ? frontImageUrl : CouponLoading}
      onClick={onClick}
      aria-label={coupon.cafeInfo.name}
      data-index={dataIndex}
      $isOn={isOn}
      $index={index}
    >
      <StarIconWrapper onClick={toggleIsFavorites}>
        {coupon.cafeInfo.isFavorites ? (
          <AiFillStar size={32} color="#F9E000" />
        ) : (
          <AiOutlineStar size={32} color="F9E000" />
        )}
      </StarIconWrapper>
      <ImageForLoading src={frontImageUrl} onLoad={checkLoadImage} alt={coupon.cafeInfo.name} />
      <Color src={addGoogleProxyUrl(frontImageUrl)} format="hex" crossOrigin="anonymous">
        {({ data }) => (
          <ProgressBarWrapper>
            <ProgressBar stampCount={stampCount} maxStampCount={maxStampCount} color={data} />
          </ProgressBarWrapper>
        )}
      </Color>
    </CouponWrapper>
  );
};

export default Coupon;
