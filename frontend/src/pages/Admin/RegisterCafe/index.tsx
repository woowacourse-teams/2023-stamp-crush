import { useState } from 'react';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import { ContentContainer, InputWithButtonWrapper, RegisterCafeInputForm, Title } from './style';
import useFindAddress from '../../../hooks/useFindAddress';
import usePostRegisterCafe from './hooks/usePostRegisterCafe';
import useRegisterCafe from './hooks/useRegisterCafe';

const inputCommonProps = {
  width: 550,
  autoComplete: 'off',
  required: true,
};

const inputCommonPropsWithButton = {
  width: 410,
  autoComplete: 'off',
  required: true,
};

const RegisterCafe = () => {
  const [roadAddress, setRoadAddress] = useState('');
  const { openAddressPopup } = useFindAddress(setRoadAddress);

  const { mutate } = usePostRegisterCafe();
  const {
    businessRegistrationNumberInputRef,
    cafeNameInputRef,
    roadAddressInputRef,
    detailAddressInputRef,
    submitCafeInfo,
  } = useRegisterCafe(mutate);

  return (
    <ContentContainer>
      <RegisterCafeInputForm onSubmit={submitCafeInfo}>
        <Title>내 카페 등록</Title>
        <Input
          id={'business-registration-number-input'}
          ref={businessRegistrationNumberInputRef}
          label={'사업자등록번호'}
          placeholder={'사업자등록번호를 입력해주세요.'}
          {...inputCommonProps}
        />
        <Input
          id={'cafe-name-input'}
          ref={cafeNameInputRef}
          label={'카페명'}
          placeholder={'카페명을 입력해주세요.'}
          {...inputCommonProps}
        />
        <InputWithButtonWrapper>
          <Input
            id={'cafe-address-input'}
            ref={roadAddressInputRef}
            label={'카페 주소'}
            value={roadAddress}
            placeholder={'카페 주소를 입력해주세요.'}
            {...inputCommonPropsWithButton}
          />
          <Button type="button" variant={'secondary'} size={'medium'} onClick={openAddressPopup}>
            주소 찾기
          </Button>
        </InputWithButtonWrapper>
        <Input
          id={'detailed-address-input'}
          ref={detailAddressInputRef}
          label={'상세 주소'}
          placeholder={'상세 주소를 입력해주세요.'}
          {...inputCommonProps}
        />
        <Button type="submit" id="register-cafe-submit-btn" variant={'primary'} size={'medium'}>
          등록하기
        </Button>
      </RegisterCafeInputForm>
    </ContentContainer>
  );
};

export default RegisterCafe;
