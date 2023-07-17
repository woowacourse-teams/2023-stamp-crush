import { FormEventHandler, MouseEventHandler, useRef } from 'react';
import Button from '../../../components/Button';
import { Input } from '../../../components/Input';
import Template from '../../../components/Template';
import { ContentContainer, InputWithButtonWrapper, RegisterCafeInputForm, Title } from './style';

const RegisterCafe = () => {
  const businessRegistrationNumberInputRef = useRef<HTMLInputElement>(null);
  const cafeNameInputRef = useRef<HTMLInputElement>(null);
  const roadAddressInputRef = useRef<HTMLInputElement>(null);
  const detailAddressInputRef = useRef<HTMLInputElement>(null);

  const certifyUser: MouseEventHandler<HTMLButtonElement> = () => {
    alert(businessRegistrationNumberInputRef.current?.value);
  };

  const findRoadAddress: MouseEventHandler<HTMLButtonElement> = () => {
    alert(roadAddressInputRef.current?.value);
  };

  const submitCafeInfo: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    alert(
      `사업자등록번호 ${businessRegistrationNumberInputRef.current?.value}
      카페명 ${cafeNameInputRef.current?.value}
      카페 주소 ${roadAddressInputRef.current?.value}
      상세 주소 ${detailAddressInputRef.current?.value}
      `,
    );
  };

  return (
    <Template>
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
              width={410}
              placeholder={'카페 주소를 입력해주세요.'}
              required={true}
            />
            <Button type="button" variant={'primary'} size={'medium'} onClick={findRoadAddress}>
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
    </Template>
  );
};

export default RegisterCafe;
