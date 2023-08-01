import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { RowSpacing, Spacing } from '../../../style/layout/common';
import { ChangeEvent, useState } from 'react';
import {
  CouponSelectContainer,
  CouponSelector,
  CouponSelectorContainer,
  CouponSelectorLabel,
  CouponSelectorWrapper,
  ExpirationDate,
  SelectorItemWrapper,
  TitleWrapper,
} from './style';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getCoupon, getCustomer } from '../../../api/get';
import { postIssueCoupon, postRegisterUser } from '../../../api/post';
import { formatDate } from '../../../utils';
import Text from '../../../components/Text';
import { ROUTER_PATH } from '../../../constants';
import { CouponActivate } from '../../../types';

const SelectCoupon = () => {
  const location = useLocation();
  const [isPrevious, setIsPrevious] = useState(true);
  const [selectedCoupon, setSelectedCoupon] = useState<CouponActivate>('current');
  const phoneNumber = location.state.phoneNumber;

  // 전화번호로 조회
  const {
    data: customer,
    status: customerStatus,
    refetch: refetchCustomer,
  } = useQuery(['customer', phoneNumber], () => getCustomer(phoneNumber), {
    onSuccess: (data) => {
      if (data.customer.length === null) {
        mutateTempCustomer(phoneNumber);
      }
    },
  });

  // 임시 가입 고객 생성
  const { mutate: mutateTempCustomer, status: tempCustomerStatus } = useMutation(
    (phoneNumber: string) => postRegisterUser(phoneNumber),
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
  const { mutate: mutateIssueCoupon } = useMutation(
    () => postIssueCoupon(customer.customer[0].id),
    {
      onSuccess: (data) => {
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
    },
  );

  const selectCoupon = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    if (value !== 'current' && value !== 'new') return;

    setIsPrevious(value === 'current');
    setSelectedCoupon(value);
  };

  if (couponStatus === 'loading' || tempCustomerStatus === 'loading') return <p>Loading</p>;

  if (couponStatus === 'error' || customerStatus === 'error') return <p>Error</p>;

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
              현재 스탬프 개수: {foundCoupon.stampCount}/{10}
            </Text>
            <Spacing $size={8} />
            <img src="https://picsum.photos/seed/picsum/270/150" width={270} height={150} />
            <Spacing $size={45} />
            <ExpirationDate>쿠폰 유효기간: {formatDate(foundCoupon.expireDate)}까지</ExpirationDate>
          </CouponSelectorWrapper>
        )}
        <RowSpacing $size={146} />
        <CouponSelectorWrapper>
          <SelectorItemWrapper>
            <CouponSelector
              type="checkbox"
              value="new"
              $size={20}
              onChange={selectCoupon}
              checked={selectedCoupon === 'new'}
            />
            <CouponSelectorLabel $isChecked={!isPrevious}>
              새로운 쿠폰을 만들게요
            </CouponSelectorLabel>
          </SelectorItemWrapper>
          <Spacing $size={40} />
          {coupon.coupons.length > 0 && (
            <SelectorItemWrapper>
              <CouponSelector
                type="checkbox"
                value="current"
                $size={20}
                onChange={selectCoupon}
                checked={selectedCoupon === 'current'}
              />
              <CouponSelectorLabel $isChecked={isPrevious}>
                현재 쿠폰에 적립할게요
              </CouponSelectorLabel>
            </SelectorItemWrapper>
          )}
        </CouponSelectorWrapper>
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
