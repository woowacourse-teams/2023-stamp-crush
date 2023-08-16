import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { RowSpacing, Spacing } from '../../../style/layout/common';
import { ChangeEvent, useState } from 'react';
import {
  CouponLabelContainer,
  CouponSelectContainer,
  CouponSelector,
  CouponSelectorContainer,
  CouponSelectorLabel,
  CouponSelectorWrapper,
  ExpirationDate,
  SelectDescription,
  SelectTitle,
  SelectorItemWrapper,
  TitleWrapper,
} from './style';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getCoupon, getCouponDesign, getCustomer } from '../../../api/get';
import { postIssueCoupon, postRegisterUser } from '../../../api/post';
import { formatDate } from '../../../utils';
import Text from '../../../components/Text';
import { ROUTER_PATH } from '../../../constants';
import { CouponActivate } from '../../../types';
import { CustomerPhoneNumberRes, IssueCouponRes, IssuedCouponsRes } from '../../../types/api';
import { LuStamp } from 'react-icons/lu';
import { MdAddCard } from 'react-icons/md';
import FlippedCoupon from '../../CouponList/FlippedCoupon';

const SelectCoupon = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [isPrevious, setIsPrevious] = useState(true);
  const [selectedCoupon, setSelectedCoupon] = useState<CouponActivate>('current');
  const phoneNumber = location.state.phoneNumber;

  const {
    data: customer,
    status: customerStatus,
    refetch: refetchCustomer,
  } = useQuery<CustomerPhoneNumberRes>(['customer', phoneNumber], {
    queryFn: () => getCustomer({ params: { phoneNumber } }),
    onSuccess: (data) => {
      if (data.customer.length === 0) {
        mutateTempCustomer({ body: phoneNumber });
      }
    },
  });

  const { mutate: mutateTempCustomer } = useMutation({
    mutationFn: postRegisterUser,
    onSuccess: () => {
      setSelectedCoupon('new');
      refetchCustomer();
    },
    onError: () => {
      throw new Error('스탬프 적립에 실패했습니다.');
    },
  });

  const { data: coupon, status: couponStatus } = useQuery<IssuedCouponsRes, Error>(
    ['coupon', customer],
    {
      queryFn: async () => {
        if (!customer) throw new Error('고객 정보를 불러오지 못했습니다.');
        return await getCoupon({ params: { customerId: customer.customer[0].id, cafeId: 1 } });
      },
      enabled: !!customer,
    },
  );

  const { data: couponDesignData, status: couponDesignStatus } = useQuery({
    queryKey: ['couponDesign'],
    queryFn: () => getCouponDesign({ params: { cafeId: 1 } }),
  });

  // TODO: cafe id 하드코딩 된 값 제거
  const TEMP_CAFE_ID = 1;
  const { mutate: mutateIssueCoupon } = useMutation<IssueCouponRes, Error>({
    mutationFn: async () => {
      if (!customer) throw new Error('고객 정보를 불러오지 못했습니다.');
      return await postIssueCoupon({
        params: { customerId: customer.customer[0].id },
        body: {
          cafeId: TEMP_CAFE_ID,
        },
      });
    },
    onSuccess: (data: IssueCouponRes) => {
      const newCouponId = +data.couponId;
      navigate(ROUTER_PATH.earnStamp, {
        state: {
          isPrevious,
          customer: foundCustomer,
          couponId: newCouponId,
        },
      });
    },
    onError: () => {
      throw new Error('스탬프 적립에 실패했습니다.');
    },
  });

  const selectCoupon = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    if (value !== 'current' && value !== 'new') return;

    setIsPrevious(value === 'current');
    setSelectedCoupon(value);
  };

  if (
    couponStatus === 'loading' ||
    customerStatus === 'loading' ||
    couponDesignStatus === 'loading'
  )
    return <p>Loading</p>;

  if (couponStatus === 'error' || customerStatus === 'error' || couponDesignStatus === 'error')
    return <p>Error</p>;

  const foundCustomer = customer.customer[0];
  const foundCoupon = coupon.coupons[0];

  return (
    <CouponSelectContainer>
      <TitleWrapper>
        <Text variant="pageTitle">스탬프 적립</Text>
        <Text variant="subTitle">1/2</Text>
      </TitleWrapper>
      <Spacing $size={90} />
      <Text variant="subTitle">{foundCustomer.nickname} 고객님의 현재 쿠폰</Text>
      <Spacing $size={80} />
      <CouponSelectorContainer>
        {coupon.coupons.length > 0 && (
          <CouponSelectorWrapper>
            <Text>
              현재 스탬프 개수: {foundCoupon.stampCount}/{foundCoupon.maxStampCount}
            </Text>
            <Spacing $size={8} />
            <FlippedCoupon
              frontImageUrl={couponDesignData.frontImageUrl}
              backImageUrl={couponDesignData.backImageUrl}
              stampImageUrl={couponDesignData.stampImageUrl}
              stampCount={foundCoupon.stampCount}
              coordinates={couponDesignData.coordinates}
              isShown={true}
            />
            <Spacing $size={45} />
            <ExpirationDate>쿠폰 유효기간: {formatDate(foundCoupon.expireDate)}까지</ExpirationDate>
          </CouponSelectorWrapper>
        )}
        <RowSpacing $size={20} />
        <CouponLabelContainer>
          <SelectorItemWrapper>
            <CouponSelector
              id="new"
              type="radio"
              value="new"
              onChange={selectCoupon}
              checked={selectedCoupon === 'new'}
            />
            <CouponSelectorLabel htmlFor="new" $isChecked={!isPrevious}>
              <SelectTitle>새 쿠폰 발급</SelectTitle>
              <SelectDescription>
                새 쿠폰을 만들고
                <br />새 쿠폰에 적립할게요.
              </SelectDescription>
              <MdAddCard size={40} />
            </CouponSelectorLabel>
          </SelectorItemWrapper>
          <Spacing $size={40} />
          {coupon.coupons.length > 0 && (
            <SelectorItemWrapper>
              <CouponSelector
                id="current"
                type="radio"
                value="current"
                onChange={selectCoupon}
                checked={selectedCoupon === 'current'}
              />
              <CouponSelectorLabel htmlFor="current" $isChecked={isPrevious}>
                <SelectTitle>현재 쿠폰 적립</SelectTitle>
                <SelectDescription>
                  고객님이 보유하신 <br />
                  현재 쿠폰에 적립할게요.
                </SelectDescription>
                <LuStamp size={40} />
              </CouponSelectorLabel>
            </SelectorItemWrapper>
          )}
        </CouponLabelContainer>
      </CouponSelectorContainer>
      <Spacing $size={70} />
      <Button
        onClick={() =>
          // TODO: 함수로 분리
          selectedCoupon === 'current'
            ? navigate(ROUTER_PATH.earnStamp, {
                state: {
                  isPrevious,
                  customer: foundCustomer,
                  couponId: foundCoupon.id,
                  couponDesignData,
                },
              })
            : mutateIssueCoupon()
        }
      >
        다음
      </Button>
    </CouponSelectContainer>
  );
};

export default SelectCoupon;
