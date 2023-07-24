import { FormEventHandler, MouseEventHandler, useRef, useState } from 'react';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { ContentContainer, InputWithButtonWrapper, RegisterCafeInputForm, Title } from './style';
import { Address, useDaumPostcodePopup } from 'react-daum-postcode';
import Header from '../../../components/Header';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../../../api';

interface CafeFormData {
  ownerId: number;
  businessRegistrationNumber: string;
  name: string;
  roadAddress: string;
  detailAddress: string;
}

const postRegisterCafe = async ({
  ownerId,
  businessRegistrationNumber,
  name,
  roadAddress,
  detailAddress,
}: CafeFormData) => {
  await api.post(`/cafes/${ownerId}`, {
    businessRegistrationNumber,
    name,
    roadAddress,
    detailAddress,
  });
};

const RegisterCafe = () => {
  const businessRegistrationNumberInputRef = useRef<HTMLInputElement>(null);
  const cafeNameInputRef = useRef<HTMLInputElement>(null);
  const roadAddressInputRef = useRef<HTMLInputElement>(null);
  const detailAddressInputRef = useRef<HTMLInputElement>(null);

  const navigate = useNavigate();

  const [roadAddress, setRoadAddress] = useState('');

  const openPostcodePopup = useDaumPostcodePopup();

  const { mutate, isLoading, isError } = useMutation(
    (formData: CafeFormData) => postRegisterCafe(formData),
    {
      onSuccess: () => {
        navigate('/admin');
      },
      onError: () => {
        throw new Error('카페 등록에 실패했습니다.');
      },
    },
  );

  // 도로명 주소 메서드
  const findAddress = (data: Address) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    setRoadAddress(fullAddress);
  };

  const openAddressPopup = () => {
    openPostcodePopup({ onComplete: findAddress });
  };

  const certifyUser: MouseEventHandler<HTMLButtonElement> = () => {
    alert(businessRegistrationNumberInputRef.current?.value);
  };

  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (
      businessRegistrationNumberInputRef.current &&
      cafeNameInputRef.current &&
      roadAddressInputRef.current &&
      detailAddressInputRef.current
    ) {
      const formData: CafeFormData = {
        ownerId: 1,
        businessRegistrationNumber: businessRegistrationNumberInputRef.current?.value,
        name: cafeNameInputRef.current?.value,
        roadAddress: roadAddressInputRef.current?.value,
        detailAddress: detailAddressInputRef.current?.value,
      };
      mutate(formData);
    }
  };

  if (isLoading)
    return (
      <>
        <Header /> <ContentContainer>Loading...</ContentContainer>
      </>
    );

  if (isError)
    return (
      <>
        <Header /> <ContentContainer>Error</ContentContainer>
      </>
    );

  return (
    <>
      <Header />
      <ContentContainer>
        <RegisterCafeInputForm onSubmit={submitCafeInfo}>
          <Title>내 카페 등록</Title>
          <InputWithButtonWrapper>
            <Input
              id={'business-registration-number-input'}
              ref={businessRegistrationNumberInputRef}
              label={'사업자등록번호'}
              width={410}
              placeholder={'사업자등록번호를 입력해주세요.'}
              required={true}
            />
            <Button type="button" variant={'secondary'} size={'medium'} onClick={certifyUser}>
              인증하기
            </Button>
          </InputWithButtonWrapper>
          <Input
            id={'cafe-name-input'}
            ref={cafeNameInputRef}
            label={'카페명'}
            width={550}
            placeholder={'카페명을 입력해주세요.'}
            required={true}
          />
          <InputWithButtonWrapper>
            <Input
              id={'cafe-address-input'}
              ref={roadAddressInputRef}
              label={'카페 주소'}
              value={roadAddress}
              width={410}
              placeholder={'카페 주소를 입력해주세요.'}
              required={true}
            />
            <Button type="button" variant={'secondary'} size={'medium'} onClick={openAddressPopup}>
              주소 찾기
            </Button>
          </InputWithButtonWrapper>
          <Input
            id={'detailed-address-input'}
            ref={detailAddressInputRef}
            label={'상세 주소'}
            width={550}
            placeholder={'상세 주소를 입력해주세요.'}
            required={true}
          />

          <Button type="submit" id="register-cafe-submit-btn" variant={'primary'} size={'medium'}>
            등록하기
          </Button>
        </RegisterCafeInputForm>
      </ContentContainer>
    </>
  );
};

export default RegisterCafe;
