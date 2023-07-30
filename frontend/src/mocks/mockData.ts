export const cafes = [
  {
    id: 1,
    name: '윤생까페',
    openTime: '09:00',
    closeTime: '18:00',
    telephoneNumber: '0212345678',
    cafeImageUrl: 'https://picsum.photos/200/300',
    roadAddress: '서울 시 송파구',
    detailAddress: '루터회관',
    businessRegistrationNumber: '00-000-00000',
  },
];

export const samples8 = {
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

export const samples10 = {
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

interface MockCustomer {
  id: number;
  nickname: string;
  phoneNumber: string;
}

export const customers: MockCustomer[] = [
  { id: 1, nickname: '윤생', phoneNumber: '01011112222' },
  { id: 2, nickname: '라잇', phoneNumber: '01033334444' },
  { id: 3, nickname: '레고', phoneNumber: '01055556666' },
];

export const mockCoupons = [
  {
    id: 1,
    customerId: 1,
    nickname: '윤생',
    stampCount: 3,
    expireDate: '2023:08:11',
    isPrevious: 'false',
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

// TODO: 리워드를 만들기 위한 스탬프 갯수가 없음
export const cafeCustomer = [
  {
    id: 1,
    nickname: '윤생1234',
    stampCount: 4,
    rewardCount: 3,
    visitCount: 10,
    firstVisitDate: '23:07:18',
    isRegistered: true,
    maxStampCount: 10,
  },
  {
    id: 2,
    nickname: '레고밟은한우',
    stampCount: 1,
    rewardCount: 0,
    visitCount: 12,
    firstVisitDate: '23:06:22',
    isRegistered: false,
    maxStampCount: 10,
  },
  {
    id: 3,
    nickname: '라잇',
    stampCount: 8,
    rewardCount: 6,
    visitCount: 8,
    firstVisitDate: '23:01:10',
    isRegistered: true,
    maxStampCount: 8,
  },
];

export const customerCoupons = {
  coupons: [
    {
      cafeInfo: {
        id: 1,
        name: '우아한카페',
      },
      couponInfos: [
        {
          id: 1,
          isFavorites: true,
          status: 'ACCUMULATING',
          stampCount: 1,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl: 'https://woowahan-cdn.woowahan.com/static/image/share_kor.jpg',
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
        name: '윤생카페',
      },
      couponInfos: [
        {
          id: 2,
          isFavorites: false,
          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl: 'https://the2.sfo2.cdn.digitaloceanspaces.com/m_photo/268167.webp',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC241B.jpg',
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
        id: 3,
        name: '카페, 빛',
      },
      couponInfos: [
        {
          id: 3,
          isFavorites: true,
          status: 'ACCUMULATING',
          stampCount: 7,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://img.freepik.com/premium-vector/golden-bright-star-light-effect-bright-star-beautiful-light-to-illustrate-star-white-sparks-sparkle-with-a-special-light-sparkles-on-transparent-background_220217-2514.jpg',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC240B.jpg',
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
        id: 4,
        name: '블럭레고카페',
      },
      couponInfos: [
        {
          id: 4,
          isFavorites: true,
          status: 'ACCUMULATING',
          stampCount: 2,
          maxStampCount: 10,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://p.turbosquid.com/ts-thumb/0y/GUpc1A/YYRcOWWr/lego_woman_generic_person_thumbnail_0000/jpg/1541672521/600x600/fit_q87/293a4ab10b3b21b10fba9335608cc2eaa0fe4f6c/lego_woman_generic_person_thumbnail_0000.jpg',
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
        id: 5,
        name: '크러쉬카페',
      },
      couponInfos: [
        {
          id: 5,
          isFavorites: true,
          status: 'ACCUMULATING',
          stampCount: 3,
          maxStampCount: 8,
          rewardName: '아메리카노',
          frontImageUrl:
            'https://www.womansense.co.kr/upload/woman/article/201912/thumb/43651-396205-sampleM.jpg',
          backImageUrl:
            'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC236B.jpg',
          stampImageUrl:
            'https://blog.kakaocdn.net/dn/Idhl1/btqDj3EXl1n/Q8AkpYkKmc3wkAyXJZX3g0/img.png',
          coordinates: [
            {
              order: 1,
              xCoordinate: 50,
              yCoordinate: 20,
            },
            {
              order: 2,
              xCoordinate: 80,
              yCoordinate: 20,
            },
          ],
        },
      ],
    },
  ],
};
