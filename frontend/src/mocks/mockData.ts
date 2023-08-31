export const cafes = {
  cafes: [
    {
      id: 1,
      name: '윤생까페',
      openTime: '09:00',
      closeTime: '18:00',
      telephoneNumber: '0212345678',
      cafeImageUrl: null,
      roadAddress: '서울시 송파구',
      detailAddress: '루터회관',
      businessRegistrationNumber: '00-000-00000',
      introduction: '안녕하세요.',
    },
  ],
};

export const samples8 = {
  sampleFrontImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
    },
    {
      id: 2,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
    },
    {
      id: 3,
      imageUrl: 'https://picsum.photos/270/150',
    },
    {
      id: 4,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
    },
    {
      id: 5,
      imageUrl: 'https://picsum.photos/270/150',
    },
    {
      id: 6,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
    },
  ],
  sampleBackImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
      stampCoordinates: [
        {
          order: 1,
          xCoordinate: 37,
          yCoordinate: 50,
        },
        {
          order: 2,
          xCoordinate: 86,
          yCoordinate: 50,
        },
        {
          order: 3,
          xCoordinate: 134,
          yCoordinate: 50,
        },
        {
          order: 4,
          xCoordinate: 182,
          yCoordinate: 50,
        },
        {
          order: 5,
          xCoordinate: 233,
          yCoordinate: 50,
        },
        {
          order: 6,
          xCoordinate: 37,
          yCoordinate: 100,
        },
        {
          order: 7,
          xCoordinate: 86,
          yCoordinate: 100,
        },
        {
          order: 8,
          xCoordinate: 134,
          yCoordinate: 100,
        },
      ],
    },
  ],
  sampleStampImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/50',
    },
    {
      id: 2,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1KVBztQdUCpvp8usHUbIbSBYvQManm6eN',
    },
  ],
};

export const samples10 = {
  sampleFrontImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
    },
    {
      id: 2,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
    },
  ],
  sampleBackImages: [
    {
      id: 2,
      imageUrl: 'https://picsum.photos/270/150',
      stampCoordinates: [
        {
          order: 1,
          xCoordinate: 37,
          yCoordinate: 50,
        },
        {
          order: 2,
          xCoordinate: 86,
          yCoordinate: 50,
        },
        {
          order: 3,
          xCoordinate: 134,
          yCoordinate: 50,
        },
        {
          order: 4,
          xCoordinate: 182,
          yCoordinate: 50,
        },
        {
          order: 5,
          xCoordinate: 233,
          yCoordinate: 50,
        },
        {
          order: 6,
          xCoordinate: 37,
          yCoordinate: 100,
        },
        {
          order: 7,
          xCoordinate: 86,
          yCoordinate: 100,
        },
        {
          order: 8,
          xCoordinate: 134,
          yCoordinate: 100,
        },
        {
          order: 9,
          xCoordinate: 182,
          yCoordinate: 100,
        },
        {
          order: 10,
          xCoordinate: 233,
          yCoordinate: 100,
        },
      ],
    },
  ],
  sampleStampImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/50',
    },
    {
      id: 2,
      imageUrl: 'https://drive.google.com/uc?export=view&id=1KVBztQdUCpvp8usHUbIbSBYvQManm6eN',
    },
  ],
};

export const samples12 = {
  sampleFrontImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
    },
  ],

  sampleBackImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
      stampCoordinates: [],
    },
  ],
  sampleStampImages: [
    {
      id: 1,
      imageUrl: 'https://picsum.photos/270/150',
    },
  ],
};

export const customers = [
  {
    id: 1,
    customerId: 1,
    nickname: '윤생',
    phoneNumber: '01011112222',
    stampCount: 3,
    expireDate: '2023:08:11',
    isPrevious: 'false',
    maxStampCount: 10,
  },
];

export const mockCoupons = [
  {
    id: 1,
    customerId: 1,
    nickname: '윤생',
    stampCount: 3,
    expireDate: '2023:08:11',
    isPrevious: 'false',
    maxStampCount: 10,
  },
];

export const rewards = [
  {
    id: 1,
    name: '아메리카노',
  },
  {
    id: 2,
    name: '조각케익',
  },
];

export const customerCoupons = {
  coupons: [
    {
      cafeInfo: {
        id: 1,
        name: '깃짱카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 1,
          status: 'ACCUMULATING',
          stampCount: 1,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1_3XRlwig5m846bBUzUv-VqcOxN1PTyPY',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC236B.jpg',
          stampImageUrl:
            'https://blog.kakaocdn.net/dn/Idhl1/btqDj3EXl1n/Q8AkpYkKmc3wkAyXJZX3g0/img.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 20,
              yCoordinate: 35,
            },
            {
              order: 2,
              xCoordinate: 70,
              yCoordinate: 35,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 2,
        name: '하디카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 2,
          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1hdTvv_yBFdpyDpJWrNMMy9JlBKVNNy7D',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC241B.jpg',
          stampImageUrl:
            'https://w7.pngwing.com/pngs/608/604/png-transparent-rubber-stamp-free-miscellaneous-freight-transport-text.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 2,
              yCoordinate: 5,
            },
            {
              order: 2,
              xCoordinate: 5,
              yCoordinate: 5,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 3,
        name: '제나카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 3,

          status: 'ACCUMULATING',
          stampCount: 7,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1Rn4Gb2vE5eKnPL8SrLwlbv1jgGzy6AWE',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC240B.jpg',
          stampImageUrl:
            'https://w7.pngwing.com/pngs/608/604/png-transparent-rubber-stamp-free-miscellaneous-freight-transport-text.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 2,
              yCoordinate: 5,
            },
            {
              order: 2,
              xCoordinate: 5,
              yCoordinate: 5,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 4,
        name: '레고카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 4,
          status: 'ACCUMULATING',
          stampCount: 2,
          maxStampCount: 10,
          rewardName: '자바칩 프라푸치노 한 잔',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1kklV1yLgmqjdQtBPXt4PLhwfrVAP00S5',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2023/NC209B.jpg',
          stampImageUrl:
            'https://w7.pngwing.com/pngs/608/604/png-transparent-rubber-stamp-free-miscellaneous-freight-transport-text.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 30,
              yCoordinate: 48,
            },
            {
              order: 2,
              xCoordinate: 75,
              yCoordinate: 53,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 5,
        name: '라잇카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 5,

          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1Jm0UYUrbkXWLhP6GxrXTTWzcI-zREWyF',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC236B.jpg',
          stampImageUrl:
            'https://blog.kakaocdn.net/dn/Idhl1/btqDj3EXl1n/Q8AkpYkKmc3wkAyXJZX3g0/img.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 2,
              yCoordinate: 5,
            },
            {
              order: 2,
              xCoordinate: 5,
              yCoordinate: 5,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 6,
        name: '윤생카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 6,
          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1ngMdF1isvQlhsZfBI0VNp5VMsGQZ9cgb',
          backImageUrl: 'https://source.unsplash.com/random',
          stampImageUrl: 'https://source.unsplash.com/random',
          coordinates: [
            {
              order: 1,
              xCoordinate: 2,
              yCoordinate: 5,
            },
            {
              order: 2,
              xCoordinate: 5,
              yCoordinate: 5,
            },
          ],
        },
      ],
    },
    {
      cafeInfo: {
        id: 7,
        name: '레오카페',
        isFavorites: true,
      },
      couponInfos: [
        {
          id: 7,

          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
          backImageUrl: 'https://source.unsplash.com/random',
          stampImageUrl: 'https://source.unsplash.com/random',
          coordinates: [
            {
              order: 1,
              xCoordinate: 35,
              yCoordinate: 45,
            },
            {
              order: 2,
              xCoordinate: 80,
              yCoordinate: 40,
            },
            {
              order: 3,
              xCoordinate: 130,
              yCoordinate: 45,
            },
          ],
        },
      ],
    },
  ],
};

export const customerRewards = {
  rewards: [
    {
      id: 1,
      rewardName: '아메리카노',
      cafeName: '우아한카페',
      createdAt: '2023:08:06',
      usedAt: null,
    },
    {
      id: 2,
      rewardName: '블록쿠키',
      cafeName: '레고카페',
      createdAt: '2023:08:04',
      usedAt: null,
    },
  ],
};

export const usedCustomerRewards = {
  rewards: [
    {
      id: 1,
      rewardName: '아메리카노',
      cafeName: '라잇카페',
      createdAt: '2023:08:05',
      usedAt: '2023:08:07',
    },
    {
      id: 2,
      rewardName: '마들렌',
      cafeName: '윤생카페',
      createdAt: '2023:08:03',
      usedAt: '2023:08:07',
    },
    {
      id: 2,
      rewardName: '케이크',
      cafeName: '레고카페',
      createdAt: '2023:08:01',
      usedAt: '2023:08:02',
    },
    {
      id: 2,
      rewardName: '카페라떼',
      cafeName: '깃짱카페',
      createdAt: '2023:08:01',
      usedAt: '2023:08:02',
    },
  ],
};

export const stampHistorys = {
  stampHistorys: [
    {
      id: 1,
      cafeName: '우아한 카페',
      stampCount: 3,
      createdAt: '2023:08:12 18:00:00',
    },
    {
      id: 2,
      cafeName: '레오 카페',
      stampCount: 2,
      createdAt: '2023:08:11 18:10:00',
    },
    {
      id: 1,
      cafeName: '하디 카페',
      stampCount: 1,
      createdAt: '2023:08:12 18:17:00',
    },
    {
      id: 1,
      cafeName: '우아한 카페',
      stampCount: 1,
      createdAt: '2023:08:11 18:00:00',
    },
  ],
};
