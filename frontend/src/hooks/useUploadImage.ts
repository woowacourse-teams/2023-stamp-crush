import { ChangeEvent, useState } from 'react';

const useUploadImage = () => {
  const [imgFileUrl, setImgFileUrl] = useState<string>('');

  const uploadImageFile = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const files = e.target.files[0];
    // TODO: 해당 로직을 어떻게 저장할지 논의 해보아야 함.
    const imgUrl = URL.createObjectURL(files);
    setImgFileUrl(imgUrl);
  };

  return [imgFileUrl, uploadImageFile, setImgFileUrl] as const;
};

export default useUploadImage;
