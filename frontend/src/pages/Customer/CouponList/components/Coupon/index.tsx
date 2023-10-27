import { MouseEvent, MouseEventHandler, useState } from 'react';
import { Coupon as CouponType } from '../../../../../types/domain/coupon';
import { CouponWrapper, ImageForLoading, StarIconWrapper } from './style';
import CouponLoading from '../../../../../assets/coupon_load_img_for_customer.png';
import { AiFillStar } from '@react-icons/all-files/ai/AiFillStar';
import { AiOutlineStar } from '@react-icons/all-files/ai/AiOutlineStar';

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

  return (
    <CouponWrapper
      $src={isImageLoaded ? coupon.couponInfos[0].frontImageUrl : CouponLoading}
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
      <ImageForLoading
        src={coupon.couponInfos[0].frontImageUrl}
        onLoad={checkLoadImage}
        alt={coupon.cafeInfo.name}
      />
    </CouponWrapper>
  );
};

export default Coupon;
