import Coupon from './Coupon';
import {
  BackDrop,
  CafeName,
  CouponListContainer,
  DetailButton,
  HeaderContainer,
  InfoContainer,
  LogoImg,
  MaxStampCount,
  NameContainer,
  ProgressBarContainer,
  StampCount,
} from './style';
import { useRef, useState } from 'react';
import { getCoupons } from '../../api/get';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { ROUTER_PATH } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import ProgressBar from '../../components/ProgressBar';
import Color from 'color-thief-react';
import { useNavigate } from 'react-router-dom';
import Alert from '../../components/Alert';
import useModal from '../../hooks/useModal';
import { CiCircleMore } from 'react-icons/ci';
import { postIsFavorites } from '../../api/post';

// TODO: 추후에 types 폴더로 위치 변경
export interface PostIsFavoritesReq {
  cafeId: number;
  isFavorites: boolean;
}

interface CouponType {
  cafeInfo: {
    id: number;
    name: string;
  };
  couponInfos: [
    {
      id: number;
      isFavorites: boolean;
      stampCount: number;
      maxStampCount: number;
      rewardName: string;
      frontImageUrl: string;
      backImageUrl: string;
      stampImageUrl: string;
      // TODO: coordinate
    },
  ];
}

const CouponList = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const [currentIndex, setCurrentIndex] = useState(0);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [isLast, setIsLast] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const queryClient = useQueryClient();
  const { data, status } = useQuery<{ coupons: CouponType[] }>(['coupons'], getCoupons, {
    onSuccess: (data) => {
      setCurrentIndex(data.coupons.length - 1);
    },
  });
  const { mutate: mutateIsFavorites } = useMutation(
    ({ cafeId, isFavorites }: PostIsFavoritesReq) => postIsFavorites({ cafeId, isFavorites }),
    {
      onSuccess: () => {
        closeModal();
      },
      onMutate: async () => {
        await queryClient.cancelQueries(['coupons']);
        queryClient.setQueryData<{ coupons: CouponType[] }>(['coupons'], (prev) => {
          if (!prev) return;

          prev.coupons[currentIndex].couponInfos[0].isFavorites =
            !prev.coupons[currentIndex].couponInfos[0].isFavorites;
          return undefined;
        });
      },
    },
  );

  if (status === 'error') return <>에러가 발생했습니다.</>;
  if (status === 'loading') return <>로딩 중입니다.</>;

  const currentCoupon = data.coupons[currentIndex];

  const swapCoupon = (e: React.MouseEvent<HTMLDivElement>) => {
    if (!couponListContainerRef.current) return;

    const coupon = couponListContainerRef.current.lastElementChild;
    if (e.target !== coupon) return;
    setIsLast(true);

    setTimeout(() => {
      setIsLast(false);
      couponListContainerRef.current?.prepend(coupon);
    }, 700);
  };

  const changeCurrentIndex = (index: number) => () => {
    setCurrentIndex((prevIndex) => {
      if (data) return index === 0 ? data.coupons.length - 1 : index - 1;
      return prevIndex;
    });
  };

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  const openAlert = () => {
    openModal();

    currentCoupon.couponInfos[0].isFavorites
      ? setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에서 해제하시겠어요?`)
      : setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에 등록하시겠어요?`);
  };

  const changeFavorites = () => {
    mutateIsFavorites({
      cafeId: currentCoupon.cafeInfo.id,
      isFavorites: currentCoupon.couponInfos[0].isFavorites,
    });
  };

  return (
    <>
      <HeaderContainer>
        <LogoImg src={AdminHeaderLogo} />
        <GoPerson size={24} onClick={navigateMyPage} />
      </HeaderContainer>
      <InfoContainer>
        <NameContainer>
          <CafeName>{currentCoupon.cafeInfo.name}</CafeName>
          {currentCoupon.couponInfos[0].isFavorites ? (
            <AiFillStar size={40} color={'#FFD600'} onClick={openAlert} />
          ) : (
            <AiOutlineStar size={40} color={'#FFD600'} onClick={openAlert} />
          )}
        </NameContainer>
        <ProgressBarContainer>
          <Color
            src={currentCoupon.couponInfos[0].frontImageUrl}
            format="hex"
            crossOrigin="anonymous"
          >
            {({ data: color }) => (
              <>
                <BackDrop $couponMainColor={color ? color : 'gray'} />
                <ProgressBar
                  stampCount={currentCoupon.couponInfos[0].stampCount}
                  maxCount={currentCoupon.couponInfos[0].maxStampCount}
                  progressColor={color}
                />
              </>
            )}
          </Color>
          <StampCount>{currentCoupon.couponInfos[0].stampCount}</StampCount>/
          <MaxStampCount>{currentCoupon.couponInfos[0].maxStampCount}</MaxStampCount>
        </ProgressBarContainer>
      </InfoContainer>
      <CouponListContainer ref={couponListContainerRef} onClick={swapCoupon} $isLast={isLast}>
        {data.coupons.map(({ cafeInfo, couponInfos }, index) => (
          <Coupon
            key={cafeInfo.id}
            frontImageUrl={couponInfos[0].frontImageUrl}
            data-index={index}
            onClick={changeCurrentIndex(index)}
          />
        ))}
      </CouponListContainer>
      <DetailButton>
        <CiCircleMore size={36} color={'#424242'} />
      </DetailButton>
      {isOpen && (
        <Alert
          text={alertMessage}
          rightOption={'네'}
          leftOption={'아니오'}
          onClickRight={changeFavorites}
          onClickLeft={closeModal}
        />
      )}
    </>
  );
};

export default CouponList;
