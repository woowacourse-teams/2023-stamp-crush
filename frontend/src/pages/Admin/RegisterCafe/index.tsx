import { FormEventHandler, MouseEventHandler, useRef, useState } from 'react';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { ContentContainer, InputWithButtonWrapper, RegisterCafeInputForm, Title } from './style';
import { Address, useDaumPostcodePopup } from 'react-daum-postcode';

const RegisterCafe = () => {
  const businessRegistrationNumberInputRef = useRef<HTMLInputElement>(null);
  const cafeNameInputRef = useRef<HTMLInputElement>(null);
  const roadAddressInputRef = useRef<HTMLInputElement>(null);
  const detailAddressInputRef = useRef<HTMLInputElement>(null);

  const [roadAddress, setRoadAddress] = useState('');

  const openPostcodePopup = useDaumPostcodePopup();

  const handleComplete = (data: Address) => {
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

  const handleClick = () => {
    openPostcodePopup({ onComplete: handleComplete });
  };

  const certifyUser: MouseEventHandler<HTMLButtonElement> = () => {
    alert(businessRegistrationNumberInputRef.current?.value);
  };

  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    if (roadAddressInputRef.current && detailAddressInputRef.current)
      alert(
        `사업자등록번호 ${businessRegistrationNumberInputRef.current?.value}
      카페명 ${cafeNameInputRef.current?.value}
      카페 주소 ${roadAddressInputRef.current.value + detailAddressInputRef.current.value}
      `,
      );
  };

  return (
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
          <Button type="button" variant={'primary'} size={'medium'} onClick={certifyUser}>
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
          <Button type="button" variant={'primary'} size={'medium'} onClick={handleClick}>
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
  );
};

export default RegisterCafe;
