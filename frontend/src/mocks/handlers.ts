import { rest } from 'msw';
import {
  cafes,
  customers,
  rewards,
  samples10,
  samples12,
  samples8,
  mockCoupons,
  customerCoupons,
  usedCustomerRewards,
  customerRewards,
  stampHistorys,
} from './mockData';

const customerList = [...customers];
const coupons = [...mockCoupons];

export const handlers = [
  // 내 카페 조회
  rest.get('/admin/cafes', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(cafes));
  }),

  // 카페 관리
  rest.patch('/admin/cafes/:cafeId', async (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 카페 등록
  rest.post('/admin/cafes', async (req, res, ctx) => {
    const { businessRegistrationNumber, name, roadAddress, detailAddress } = await req.json();

    if (!businessRegistrationNumber || !name || !roadAddress || !detailAddress) {
      return res(ctx.status(400));
    }
    return res(ctx.status(201), ctx.set('Location', '/cafes/1'));
  }),

  // 스탬프 개수별로 기본 샘플 조회
  rest.get('/admin/coupon-samples', (req, res, ctx) => {
    const maxStampCountQueryParam = req.url.searchParams.get('max-stamp-count');

    if (!maxStampCountQueryParam) return res(ctx.status(400));

    const maxStampCount = +maxStampCountQueryParam;

    if (maxStampCount === 8) {
      return res(ctx.status(200), ctx.json(samples8));
    }
    if (maxStampCount === 10) {
      return res(ctx.status(200), ctx.json(samples10));
    }
    if (maxStampCount === 12) {
      return res(ctx.status(200), ctx.json(samples12));
    }
  }),

  // 쿠폰 디자인 및 정책 수정
  rest.post('/admin/coupon-setting', async (req, res, ctx) => {
    // TODO: cafe id 핸들링
    const cafeIdParam = req.url.searchParams.get('cafe-id');
    const { frontImageUrl, backImageUrl, stampImageUrl, coordinates, reward, expirePeriod } =
      await req.json();

    if (
      !frontImageUrl ||
      !backImageUrl ||
      !stampImageUrl ||
      !coordinates ||
      !reward ||
      !expirePeriod
    ) {
      return res(ctx.status(400));
    }

    return res(ctx.status(204));
  }),

  // 전화번호로 고객 조회
  rest.get('/admin/customers', (req, res, ctx) => {
    const phoneNumberQueryParam = req.url.searchParams.get('phone-number');
    const findUserResult = customerList.find(
      (customer) => customer.phoneNumber === phoneNumberQueryParam,
    );

    if (!findUserResult) {
      return res(ctx.status(200), ctx.json({ customer: [] }));
    }

    return res(
      ctx.status(200),
      ctx.json({
        customer: [findUserResult],
      }),
    );
  }),

  // 임시 가입 고객 생성
  rest.post('/admin/temporary-customers', async (req, res, ctx) => {
    const body = await req.json();
    const newId = Math.floor(Math.random() * 1000 + 29);

    const createdCustomer = {
      id: newId,
      nickname: '레고(임시회원, 신규)',
      phoneNumber: body.phoneNumber,
      customerId: newId,
      stampCount: 1,
      expireDate: '2023:08:11',
      isPrevious: 'false',
      maxStampCount: 10,
    };

    customerList.push(createdCustomer);
    return res(ctx.status(201), ctx.set('Location', `customers/${createdCustomer.id}`));
  }),

  // 고객의 쿠폰 조회
  rest.get('/admin/customers/:customerId/coupons', (req, res, ctx) => {
    const cafeIdQueryParam = req.url.searchParams.get('cafe-id');
    const activeQueryParam = req.url.searchParams.get('active');
    const { customerId } = req.params;

    if (!cafeIdQueryParam || !activeQueryParam || !customerId) {
      return res(ctx.status(400));
    }
    const findCouponsResult = coupons.find(
      (coupon) => coupon.customerId === +customerId && activeQueryParam === 'true',
    );

    if (+cafeIdQueryParam !== 1) {
      return res(ctx.status(400));
    }

    if (!findCouponsResult) {
      return res(ctx.status(200), ctx.json({ coupons: [] }));
    }

    return res(ctx.status(200), ctx.json({ coupons: [findCouponsResult] }));
  }),

  // 고객의 리워드 조회
  rest.get('/admin/customers/:customerId/rewards', (req, res, ctx) => {
    const usedQueryParam = req.url.searchParams.get('used');
    const cafeIdQueryParam = req.url.searchParams.get('cafe-id');
    const { customerId } = req.params;

    if (!usedQueryParam || !cafeIdQueryParam || !customerId) {
      return res(ctx.status(400));
    }

    if (+customerId !== 1) return res(ctx.status(200), ctx.json({ rewards: [] }));
    return res(ctx.status(200), ctx.json({ rewards }));
  }),

  // 쿠폰 신규 발급
  rest.post('/admin/customers/:customerId/coupons', async (req, res, ctx) => {
    const { customerId } = req.params;

    const customerIdNum = +customerId;
    const coupon = {
      id: Math.floor(Math.random() * 1000 + 23),
      customerId: customerIdNum,
      nickname: '윤생',
      stampCount: 1,
      expireDate: '2023:08:11',
      isPrevious: 'false',
      maxStampCount: 10,
    };

    coupons.push(coupon);

    return res(ctx.status(201), ctx.json({ couponId: coupon.id }));
  }),

  // 스탬프 적립
  rest.post('/admin/customers/:customerId/coupons/:couponId/stamps', async (req, res, ctx) => {
    const { customerId, couponId } = req.params;
    const { earningStampCount } = await req.json();
    const findCustomer = coupons.find(
      (coupon) => +customerId === coupon.customerId && +couponId === coupon.id,
    );

    if (!findCustomer) {
      return res(ctx.status(400));
    }

    const customerIndex = customerList.findIndex((e) => e.customerId === +customerId);

    if (customerIndex === -1) {
      return res(ctx.status(400));
    }

    customerList[customerIndex].stampCount += +earningStampCount;
    return res(ctx.status(201));
  }),

  // 카페 사장이 고객 목록 조회 가능
  rest.get('/admin/cafes/:cafeId/customers', (req, res, ctx) => {
    const { cafeId } = req.params;

    if (!cafeId) {
      return res(ctx.status(400));
    }
    if (+cafeId !== 1) {
      return res(ctx.status(200), ctx.json({ customers: [] }));
    }

    return res(ctx.status(200), ctx.json({ customers: customerList }));
  }),

  // 리워드 사용
  rest.patch('/admin/customers/:customerId/rewards/:rewardId', async (req, res, ctx) => {
    const { customerId, rewardId } = req.params;
    const { cafeId, used } = await req.json();

    if (!customerId || !rewardId || !cafeId || !used) {
      return res(ctx.status(400));
    }

    return res(ctx.status(200));
  }),

  rest.get('/admin/coupon-setting/:couponId', async (req, res, ctx) => {
    const { couponId } = req.params;
    const cafeIdParam = req.url.searchParams.get('cafe-id');
    const coupon = {
      frontImageUrl: 'https://drive.google.com/uc?export=view&id=1J6HcagcK65D6_i0bDQ7llbvdCnCOkJ7h',
      backImageUrl:
        'https://wemix-dev-s3.s3.amazonaws.com/media/sample/%EC%BF%A0%ED%8F%B0(%EB%AA%85%ED%95%A8)/2019/NC236B.jpg',
      stampImageUrl:
        'https://blog.kakaocdn.net/dn/Idhl1/btqDj3EXl1n/Q8AkpYkKmc3wkAyXJZX3g0/img.png',
      coordinates: [
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
    };
    return res(ctx.status(200), ctx.json(coupon));
  }),

  /** -----------------  아래부터는 고객모드의 api 입니다. ----------------- */

  // 쿠폰 리스트
  rest.get('/coupons', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(customerCoupons));
  }),

  // 카페 정보 조회
  rest.get('/cafes/:cafeId', (req, res, ctx) => {
    const cafe = {
      cafe: {
        id: 1,
        name: '우아한 카페',
        introduction:
          '이 편지는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고 지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 ...',
        openTime: '10:00',
        closeTime: '18:00',
        telephoneNumber: '01012345678',
        cafeImageUrl: 'https://picsum.photos/540/900',
        roadAddress: '서울시 송파구',
        detailAddress: '루터회관',
      },
    };

    return res(ctx.status(200), ctx.json(cafe));
  }),

  // 쿠폰 즐겨찾기 등록, 해제
  rest.post('/cafes/:cafeId/favorites', async (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 사용가능한 리워드 조회
  rest.get('/rewards', (req, res, ctx) => {
    const used = req.url.searchParams.get('used');

    if (used) return res(ctx.status(200), ctx.json(usedCustomerRewards));
    return res(ctx.status(200), ctx.json(customerRewards));
  }),

  // 스탬프 조회
  rest.get('/stamp-history', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(stampHistorys));
  }),

  // 쿠폰 삭제
  rest.delete('/coupons/:couponId', async (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.get('/admin/login', (req, res, ctx) => {
    localStorage.setItem('admin-login-token', 'regorego');
  }),

  rest.get('/profiles', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({ profile: { id: 10, nickname: '강영민', phoneNumber: null, email: null } }),
    );
  }),
];
