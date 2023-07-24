import { Address, useDaumPostcodePopup } from 'react-daum-postcode';

import { Dispatch, SetStateAction } from 'react';

const useFindAddress = (setRoadAddress: Dispatch<SetStateAction<string>>) => {
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
  const openPostcodePopup = useDaumPostcodePopup();

  const openAddressPopup = () => {
    openPostcodePopup({ onComplete: findAddress });
  };
  return {
    openAddressPopup,
  };
};

export default useFindAddress;
