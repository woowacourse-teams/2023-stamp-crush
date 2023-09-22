import { FormEvent, useRef } from 'react';
import { CafeRegisterReqBody, MutateReq } from '../../../../types/api/request';
import { UseMutateFunction } from '@tanstack/react-query';

const useRegisterCafe = (
  mutate: UseMutateFunction<Response, unknown, MutateReq<CafeRegisterReqBody, unknown>, unknown>,
) => {
  const businessRegistrationNumberInputRef = useRef<HTMLInputElement>(null);
  const cafeNameInputRef = useRef<HTMLInputElement>(null);
  const roadAddressInputRef = useRef<HTMLInputElement>(null);
  const detailAddressInputRef = useRef<HTMLInputElement>(null);

  const submitCafeInfo = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (
      businessRegistrationNumberInputRef.current &&
      cafeNameInputRef.current &&
      roadAddressInputRef.current &&
      detailAddressInputRef.current
    ) {
      const cafeRegisterBody: CafeRegisterReqBody = {
        businessRegistrationNumber: businessRegistrationNumberInputRef.current?.value,
        name: cafeNameInputRef.current?.value,
        roadAddress: roadAddressInputRef.current?.value,
        detailAddress: detailAddressInputRef.current?.value,
      };
      mutate({ body: cafeRegisterBody });
    }
  };

  return {
    businessRegistrationNumberInputRef,
    cafeNameInputRef,
    roadAddressInputRef,
    detailAddressInputRef,
    submitCafeInfo,
  };
};

export default useRegisterCafe;
