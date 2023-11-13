import { useMutation } from '@tanstack/react-query';
import { ChangeEvent, useState } from 'react';
import { postUploadImage } from '../api/post';
import { ImageUploadRes } from '../types/api/response';
import { isLargeThanBoundarySize } from '../utils';

const useUploadImage = (initImgUrl = '') => {
  const [imgFileUrl, setImgFileUrl] = useState<string>(initImgUrl);
  const { mutate } = useMutation({
    mutationFn: (imageFile: File) => postUploadImage(imageFile),
    onSuccess: (res) => {
      const body = res.data as ImageUploadRes;
      setImgFileUrl(body.imageUrl);
    },
    onError: () => {
      alert('파일을 업로드하는데 실패하였습니다.');
    },
  });
  const uploadImageFile = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const uploadedImage = e.target.files[0];
    if (isLargeThanBoundarySize(uploadedImage.size)) {
      alert('파일은 5MB 미만 이어야 합니다.');
      return;
    }
    mutate(uploadedImage);
  };

  return [imgFileUrl, uploadImageFile, setImgFileUrl] as const;
};

export default useUploadImage;
