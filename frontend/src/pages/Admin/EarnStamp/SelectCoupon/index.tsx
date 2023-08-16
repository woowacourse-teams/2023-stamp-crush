import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../../components/Button';
import { Spacing } from '../../../../style/layout/common';
import { ChangeEvent, useState } from 'react';
import {
  CouponLabelContainer,
  CouponSelector,
  CouponSelectorLabel,
  SelectDescription,
  SelectTitle,
  SelectorItemWrapper,
} from './style';
import { useMutation, useQuery } from '@tanstack/react-query';
import { INVALID_CAFE_ID, ROUTER_PATH } from '../../../../constants';
import { useRedirectRegisterPage } from '../../../../hooks/useRedirectRegisterPage';
import { getCoupon, getCustomer } from '../../../../api/get';
import { postIssueCoupon, postRegisterUser } from '../../../../api/post';
import { formatDate } from '../../../../utils';
import Text from '../../../../components/Text';
import { CouponActivate } from '../../../../types';
import { CustomerPhoneNumberRes, IssueCouponRes, IssuedCouponsRes } from '../../../../types/api';
import { LuStamp } from 'react-icons/lu';
import { MdAddCard } from 'react-icons/md';
import { CouponSelectorContainer, CouponSelectorWrapper } from '../style';

const SelectCoupon = () => {
  const cafeId = useRedirectRegisterPage();
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
        return await getCoupon({ params: { customerId: customer.customer[0].id, cafeId } });
      },
      enabled: !!customer && cafeId !== INVALID_CAFE_ID,
    },
  );

  const { mutate: mutateIssueCoupon } = useMutation<IssueCouponRes, Error>({
    mutationFn: async () => {
      if (!customer) throw new Error('고객 정보를 불러오지 못했습니다.');
      return await postIssueCoupon({
        params: { customerId: customer.customer[0].id },
        body: {
          cafeId,
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

  const moveNextStep = () => {
    selectedCoupon === 'current'
      ? navigate(ROUTER_PATH.earnStamp, {
          state: {
            isPrevious,
            customer: foundCustomer,
            couponId: foundCoupon.id,
          },
        })
      : mutateIssueCoupon();
  };

  if (couponStatus === 'loading' || customerStatus === 'loading') return <p>Loading</p>;

  if (couponStatus === 'error' || customerStatus === 'error') return <p>Error</p>;

  const foundCustomer = customer.customer[0];
  const foundCoupon = coupon.coupons[0];

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">스탬프 적립</Text>
      <Spacing $size={40} />
      <Text variant="subTitle">step1. {foundCustomer.nickname} 고객님의 쿠폰을 선택해주세요.</Text>
      <CouponSelectorContainer>
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
              <MdAddCard size={30} />
            </CouponSelectorLabel>
          </SelectorItemWrapper>
          <Spacing $size={40} />
          {coupon.coupons.length > 0 && (
            <>
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
                  <LuStamp size={30} />
                </CouponSelectorLabel>
              </SelectorItemWrapper>
            </>
          )}
        </CouponLabelContainer>
        <Button onClick={moveNextStep}>다음</Button>
        {coupon.coupons.length > 0 && (
          <CouponSelectorWrapper>
            <h1>윤생 고객님의 현재 쿠폰</h1>
            <Spacing $size={8} />
            <img src="https://picsum.photos/seed/picsum/270/150" width={270} height={150} />
            <Spacing $size={8} />
            <span>
              스탬프: {foundCoupon.stampCount}/{10} <br />
              쿠폰 유효기간: {formatDate(foundCoupon.expireDate)}까지
            </span>
          </CouponSelectorWrapper>
        )}
      </CouponSelectorContainer>
    </>
  );
};

export default SelectCoupon;
