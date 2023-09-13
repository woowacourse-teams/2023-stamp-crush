import { ChangeEvent, FormEventHandler, useEffect, useState } from 'react';
import useUploadImage from '../../../../hooks/useUploadImage';
import { Time } from '../../../../types/utils';
import { Cafe } from '../../../../types/domain/cafe';
import { isEmptyData, parseTime, splitTime } from '../../../../utils';
import { CafeIdParams, CafeInfoReqBody, MutateReq } from '../../../../types/api/request';
import { UseMutateFunction } from '@tanstack/react-query';

const useManageCafe = (
  cafeId: number,
  cafeInfo: Cafe,
  mutatePatchCafeInfo: UseMutateFunction<
    Response,
    unknown,
    MutateReq<CafeInfoReqBody, CafeIdParams>,
    unknown
  >,
) => {
  const [cafeImage, uploadCafeImage, setCafeImage] = useUploadImage();
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('');

  const [openTime, setOpenTime] = useState<Time>({ hour: '10', minute: '00' });
  const [closeTime, setCloseTime] = useState<Time>({ hour: '18', minute: '00' });

  useEffect(() => {
    if (!(isEmptyData(cafeInfo.openTime) && isEmptyData(cafeInfo.closeTime))) {
      setOpenTime(splitTime(cafeInfo.openTime));
      setCloseTime(splitTime(cafeInfo.closeTime));
    }
    if (!isEmptyData(cafeInfo.telephoneNumber)) setPhoneNumber(cafeInfo.telephoneNumber);
    if (!isEmptyData(cafeInfo.introduction)) setIntroduction(cafeInfo.introduction);
    if (!isEmptyData(cafeInfo.cafeImageUrl)) setCafeImage(cafeInfo.cafeImageUrl);
  }, [cafeInfo]);

  const inputPhoneNumber = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;

    const removeHyphenPhoneNumber = value.replace(/-/g, '');

    setPhoneNumber(removeHyphenPhoneNumber);
  };

  const inputIntroduction = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduction(e.target.value);
  };

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    uploadCafeImage(e);
  };

  // TODO: 시간이 빈값인 케이스 대처 x
  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const cafeInfoBody: CafeInfoReqBody = {
      openTime: parseTime(openTime),
      closeTime: parseTime(closeTime),
      telephoneNumber: phoneNumber,
      cafeImageUrl: cafeImage,
      introduction: introduction,
    };

    mutatePatchCafeInfo({
      params: {
        cafeId,
      },
      body: cafeInfoBody,
    });
  };

  return {
    submitCafeInfo,
    uploadImage,
    inputPhoneNumber,
    inputIntroduction,
    phoneNumber,
    openTime,
    closeTime,
    setOpenTime,
    setCloseTime,
    introduction,
    cafeImage,
  };
};

export default useManageCafe;
