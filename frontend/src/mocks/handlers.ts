import { rest } from 'msw';
import {
  cafes,
  customers,
  rewards,
  samples10,
  samples12,
  samples8,
  mockCoupons,
  cafeCustomer,
  customerCoupons,
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
      res(ctx.status(200), ctx.json({ customer: [] }));
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
    const createdCustomer = {
      id: Math.floor(Math.random() * 1000 + 29),
      nickname: '레고(임시회원, 신규)',
      phoneNumber: body.phoneNumber,
    };

    customerList.push(createdCustomer);
    return res(ctx.status(201), ctx.set('Location', `customers/${createdCustomer.id}`));
  }),

  // 고객의 쿠폰 조회
  rest.get('/admin/customers/:customerId/coupons', (req, res, ctx) => {
    const cafeIdQueryParam = req.url.searchParams.get('cafeId');
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
    const cafeIdQueryParam = req.url.searchParams.get('cafeId');
    const { customerId } = req.params;

    if (!usedQueryParam || !cafeIdQueryParam || !customerId) {
      return res(ctx.status(400));
    }

    if (+customerId !== 1) return res(ctx.status(200), ctx.json({ rewards: [] }));
    return res(ctx.status(200), ctx.json({ rewards }));
  }),

  // 쿠폰 신규 발급
  rest.post('/customers/:customerId/coupons', async (req, res, ctx) => {
    const { customerId } = req.params;

    const customerIdNum = +customerId;
    const coupon = {
      id: Math.floor(Math.random() * 1000 + 23),
      customerId: customerIdNum,
      nickname: '윤생',
      stampCount: 1,
      expireDate: '2023:08:11',
      isPrevious: 'false',
    };

    coupons.push(coupon);

    return res(ctx.status(201), ctx.json({ couponId: coupon.id }));
  }),

  // 스탬프 적립
  rest.post(
    '/admin/customers/:customerId/coupons/:couponId/stamps/:ownerId',
    async (req, res, ctx) => {
      const { customerId, couponId } = req.params;
      const { earningStampCount } = await req.json();
      const findCustomer = coupons.find(
        (coupon) => +customerId === coupon.customerId && +couponId === coupon.id,
      );

      if (!findCustomer) {
        return res(ctx.status(400));
      }

      findCustomer.stampCount += +earningStampCount;

      return res(ctx.status(201));
    },
  ),

  // 카페 사장이 고객 목록 조회 가능
  rest.get('/admin/cafes/:cafeId/customers', (req, res, ctx) => {
    const { cafeId } = req.params;

    if (!cafeId) {
      return res(ctx.status(400));
    }
    if (+cafeId !== 1) {
      return res(ctx.status(200), ctx.json({ customers: [] }));
    }

    return res(ctx.status(200), ctx.json({ customers: cafeCustomer }));
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

  /** -----------------  아래부터는 고객모드의 api 입니다. ----------------- */

  // 쿠폰 리스트
  rest.get('/coupons', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(customerCoupons));
  }),

  // 카페 정보 조회
  rest.get('/cafes/:cafeId', (req, res, ctx) => {
    const cafe = {
      id: 1,
      name: '우아한 카페',
      introduction: '안녕하세요 우아한 카페입니다',
      openTime: '10:00',
      closeTime: '18:00',
      telephoneNumber: '01012345678',
      cafeImageUrl: 'http://sdfjs.sfm/dfjnvd',
      roadAddress: '서울시 송파구',
      detailAddress: '루터회관',
    };

    return res(ctx.status(200), ctx.json(cafe));
  }),

  // 쿠폰 즐겨찾기 등록, 해제
  rest.post('/coupons/:couponId/favorites', async (req, res, ctx) => {
    const { isFavorites } = await req.json();

    if (!isFavorites) return res(ctx.status(400));

    return res(ctx.status(200));
  }),
];
