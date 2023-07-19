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
      stampCoordinates: [
        {
          order: 1,
          xCoordinate: 1,
          yCoordinate: 1,
        },
        {
          order: 2,
          xCoordinate: 1,
          yCoordinate: 2,
        },
        {
          order: 3,
          xCoordinate: 1,
          yCoordinate: 3,
        },
        {
          order: 4,
          xCoordinate: 1,
          yCoordinate: 4,
        },
        {
          order: 5,
          xCoordinate: 2,
          yCoordinate: 1,
        },
        {
          order: 6,
          xCoordinate: 2,
          yCoordinate: 2,
        },
        {
          order: 7,
          xCoordinate: 2,
          yCoordinate: 3,
        },
        {
          order: 8,
          xCoordinate: 2,
          yCoordinate: 4,
        },
      ],
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
      stampCoordinates: [
        {
          order: 1,
          xCoordinate: 1,
          yCoordinate: 1,
        },
        {
          order: 2,
          xCoordinate: 1,
          yCoordinate: 2,
        },
        {
          order: 3,
          xCoordinate: 1,
          yCoordinate: 3,
        },
        {
          order: 4,
          xCoordinate: 1,
          yCoordinate: 4,
        },
        {
          order: 5,
          xCoordinate: 1,
          yCoordinate: 5,
        },
        {
          order: 6,
          xCoordinate: 2,
          yCoordinate: 1,
        },
        {
          order: 7,
          xCoordinate: 2,
          yCoordinate: 2,
        },
        {
          order: 8,
          xCoordinate: 2,
          yCoordinate: 3,
        },
        {
          order: 9,
          xCoordinate: 2,
          yCoordinate: 4,
        },
        {
          order: 10,
          xCoordinate: 2,
          yCoordinate: 5,
        },
      ],
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
      stampCoordinates: [
        {
          order: 1,
          xCoordinate: 1,
          yCoordinate: 1,
        },
        {
          order: 2,
          xCoordinate: 1,
          yCoordinate: 2,
        },
        {
          order: 3,
          xCoordinate: 1,
          yCoordinate: 3,
        },
        {
          order: 4,
          xCoordinate: 1,
          yCoordinate: 4,
        },
        {
          order: 5,
          xCoordinate: 1,
          yCoordinate: 5,
        },
        {
          order: 6,
          xCoordinate: 2,
          yCoordinate: 1,
        },
        {
          order: 7,
          xCoordinate: 2,
          yCoordinate: 2,
        },
        {
          order: 8,
          xCoordinate: 2,
          yCoordinate: 3,
        },
        {
          order: 9,
          xCoordinate: 2,
          yCoordinate: 4,
        },
        {
          order: 10,
          xCoordinate: 2,
          yCoordinate: 5,
        },
        {
          order: 11,
          xCoordinate: 3,
          yCoordinate: 1,
        },
        {
          order: 12,
          xCoordinate: 3,
          yCoordinate: 2,
        },
      ],
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
  },
  {
    id: 2,
    nickname: '레고밟은한우',
    stampCount: 1,
    rewardCount: 0,
    visitCount: 1,
    firstVisitDate: '23:06:22',
    isRegistered: false,
  },
  {
    id: 3,
    nickname: '라잇',
    stampCount: 8,
    rewardCount: 6,
    visitCount: 32,
    firstVisitDate: '23:01:10',
    isRegistered: true,
  },
];
