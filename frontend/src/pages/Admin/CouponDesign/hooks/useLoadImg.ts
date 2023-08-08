import { useEffect, useState } from 'react';

export const useLoadImg = (imgFileUrl: string) => {
  const [prevUrl, setPrevUrl] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (prevUrl !== imgFileUrl) {
      setIsLoading(true);
    }
  }, [imgFileUrl]);

  const handleImageLoad = () => {
    setIsLoading(false);
    setPrevUrl(imgFileUrl);
  };

  return { isLoading, handleImageLoad };
};

export const selectImgUrl = (
  imgUrl: string,
  previewImgUrl: string,
  loadImgUrl: string,
  isLoading: boolean,
) => {
  if (isLoading) return loadImgUrl;
  if (!imgUrl) return previewImgUrl;
  return imgUrl;
};
