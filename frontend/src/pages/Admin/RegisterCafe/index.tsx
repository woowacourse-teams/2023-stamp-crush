import { Input } from '../../../components/Input';
import Template from '../../../components/Template';
import { RegisterCafeInputForm } from './style';

const RegisterCafe = () => {
  return (
    <Template>
      <RegisterCafeInputForm>
        <Input
          id={'business-registration-number-input'}
          label={'사업자등록번호'}
          required={true}
          placeholder={'사업자등록번호를 입력해주세요.'}
        />
        <Input
          id={'cafe-name-input'}
          label={'카페명'}
          required={true}
          placeholder={'카페명을 입력해주세요.'}
        />
        <Input
          id={'address-input'}
          label={'주소'}
          required={true}
          placeholder={'주소를 입력해주세요.'}
        />
        <Input
          id={'detailed-address-input'}
          label={'상세 주소'}
          required={true}
          placeholder={'상세 주소를 입력해주세요.'}
        />
      </RegisterCafeInputForm>
    </Template>
  );
};

export default RegisterCafe;
