import { Input } from '../../components/Input';

const InputPhoneNumber = () => {
  return (
    <Input
      id="input-phone-number"
      label={'전화번호'}
      placeholder={'-를 제외한 번호를 입력해주세요'}
      required
    />
  );
};

export default InputPhoneNumber;
