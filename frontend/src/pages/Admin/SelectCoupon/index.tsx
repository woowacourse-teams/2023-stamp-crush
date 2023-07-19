import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import Header from '../../../components/Header';
import Template from '../../../components/Template';
import { Spacing, SubTitle, Text, Title } from '../../../style/layout/common';
import { ChangeEvent, useEffect, useState } from 'react';
import {
  CouponSelectContainer,
  CouponSelector,
  CouponSelectorLabel,
  CouponSelectorWrapper,
  ExpirationDate,
  TitleWrapper,
} from './style';
import { useMutation, useQuery } from '@tanstack/react-query';

const getCustomer = async (phoneNumber: string) => {
  const response = await fetch(`/customers?phone-number=${phoneNumber}`);

  if (!response.ok) {
    throw new Error('고객 조회 실패');
  }

  return await response.json();
};

export const getCoupon = async (customerId: string, cafeId: string) => {
  const response = await fetch(`/customers/${customerId}/coupons?cafeId=${cafeId}&active=true`);

  if (!response.ok) {
    throw new Error('쿠폰 조회 실패');
  }

  return await response.json();
};

// 신규 가입
const registerUser = async (phoneNumber: string) => {
  const response = await fetch('/temporary-customers', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ phoneNumber }),
  });

  if (!response.ok) {
    throw new Error('회원 등록에 실패했습니다.');
  }
};

// 신규 쿠폰 발급
const issueCoupon = async (customerId: string) => {
  const response = await fetch(`/customers/${customerId}/coupons`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ cafeId: '1' }),
  });

  if (!response.ok) {
    throw new Error('쿠폰 발급에 실패했습니다.');
  }

  return await response.json();
};

const SelectCoupon = () => {
  const phoneNumber = useLocation().state.phoneNumber.replaceAll('-', '');

  const [isPrevious, setIsPrevious] = useState(true);
  const [selectedCoupon, setSelectedCoupon] = useState('current');

  // 전화번호로 조회
  const {
    data: customer,
    status: customerStatus,
    isSuccess,
    refetch: refetchCustomer,
  } = useQuery(['customer', phoneNumber], () => getCustomer(phoneNumber));

  // 임시 가입 고객 생성
  const { mutate: mutateTempCustomer, status: tempCustomerStatus } = useMutation(
    (phoneNumber: string) => registerUser(phoneNumber),
    {
      onSuccess: () => {
        setSelectedCoupon('new');
        refetchCustomer();
      },
      onError: () => {
        throw new Error('스탬프 적립에 실패했습니다.');
      },
    },
  );

  useEffect(() => {
    if (isSuccess && customer?.customer?.[0] === null) {
      mutateTempCustomer(phoneNumber);
    }
  }, [isSuccess, customer, mutateTempCustomer, phoneNumber]);

  // 쿠폰 조회
  const { data: coupon, status: couponStatus } = useQuery(
    ['coupons', customer],
    () => getCoupon(customer.customer[0].id, '1'),
    {
      enabled: !!customer,
    },
  );

  const navigate = useNavigate();

  // 신규 쿠폰 발급
  const { mutate: mutateIssueCoupon } = useMutation(() => issueCoupon(customer.customer[0].id), {
    onSuccess: (data) => {
      const newCouponId = +data.couponId;
      navigate('/admin/stamp/2', {
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

  const selectCoupon = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setIsPrevious(value === 'current');
    setSelectedCoupon(value);
  };

  if (couponStatus === 'loading' || tempCustomerStatus === 'loading') return <p>Loading</p>;

  if (couponStatus === 'error' || customerStatus === 'error') return <p>Error</p>;

  const foundCustomer = customer.customer[0];
  const foundCoupon = coupon.coupons[0];

  return (
    <>
      <Header />
      <Template>
        <CouponSelectContainer>
          <TitleWrapper>
            <Title>스탬프 적립</Title>
            <SubTitle>1/2</SubTitle>
          </TitleWrapper>
          <SubTitle>{foundCustomer.nickname} 고객님의 현재 쿠폰</SubTitle>
          {coupon.coupons.length > 0 && (
            <>
              <Text>
                현재 스탬프 개수: {foundCoupon.stampCount}/{8}
              </Text>
              <ExpirationDate>쿠폰 유효기간: {foundCoupon.expireDate}까지</ExpirationDate>
            </>
          )}

          <CouponSelectorWrapper>
            <CouponSelector
              type="radio"
              value="new"
              size={20}
              onChange={selectCoupon}
              checked={selectedCoupon === 'new'}
            />
            <CouponSelectorLabel $isChecked={!isPrevious}>
              새로운 쿠폰을 만들게요
            </CouponSelectorLabel>
          </CouponSelectorWrapper>
          <Spacing $size={40} />
          {coupon.coupons.length > 0 && (
            <CouponSelectorWrapper>
              <CouponSelector
                type="radio"
                value="current"
                size={20}
                onChange={selectCoupon}
                checked={selectedCoupon === 'current'}
              />
              <CouponSelectorLabel $isChecked={isPrevious}>
                현재 쿠폰에 적립할게요
              </CouponSelectorLabel>
            </CouponSelectorWrapper>
          )}
          <Button
            onClick={() =>
              selectedCoupon === 'current'
                ? navigate('/admin/stamp/2', {
                    state: {
                      isPrevious,
                      customer: foundCustomer,
                      couponId: foundCoupon.id,
                    },
                  })
                : mutateIssueCoupon()
            }
          >
            다음
          </Button>
        </CouponSelectContainer>
      </Template>
    </>
  );
};

export default SelectCoupon;
