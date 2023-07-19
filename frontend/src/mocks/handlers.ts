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
} from './mockData';

const customerList = [...customers];
const coupons = [...mockCoupons];

export const handlers = [
  // 내 카페 조회
  rest.get('/cafes', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(cafes));
  }),

  // 카페 등록
  rest.post('/cafes', async (req, res, ctx) => {
    const { businessRegistrationNumber, name, roadAddress, detailAddress } = await req.json();

    if (!businessRegistrationNumber || !name || !roadAddress || !detailAddress) {
      return res(ctx.status(400));
    }
    return res(ctx.status(201), ctx.set('Location', '/cafes/1'));
  }),

  // 스탬프 개수별로 기본 샘플 조회
  rest.get('/coupon-samples', (req, res, ctx) => {
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
  rest.post('/coupon-setting', async (req, res, ctx) => {
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
  rest.get('/customers', (req, res, ctx) => {
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
  rest.post('/temporary-customers', async (req, res, ctx) => {
    const body = await req.json();
    const createdCustomer = {
      id: Math.floor(Math.random() * 1000 + 29),
      nickname: '레고(임시회원, 신규)',
      phoneNumber: body.phoneNumber,
    };

    customerList.push(createdCustomer);
    return res(ctx.status(201), ctx.set('Location', `customers/${createdCustomer.id}`));
  }),

  //고객의 쿠폰 조회
  rest.get('/customers/:customerId/coupons', (req, res, ctx) => {
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

  //고객의 리워드 조회
  rest.get('/customers/:customerId/rewards', (req, res, ctx) => {
    const usedQueryParam = req.url.searchParams.get('used');
    const cafeIdQueryParam = req.url.searchParams.get('cafeId');
    const { customerId } = req.params;

    if (!usedQueryParam || !cafeIdQueryParam || !customerId) {
      return res(ctx.status(400));
    }

    if (+customerId !== 1) return res(ctx.status(200), ctx.json({ rewards: [] }));
    return res(ctx.status(200), ctx.json(rewards));
  }),

  //쿠폰 신규 발급
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
  rest.post('/customers/:customerId/coupons/:couponId/stamps', async (req, res, ctx) => {
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
  }),

  // 카페 사장이 고객 목록 조회 가능
  rest.get('/cafes/:cafeId/customers', (req, res, ctx) => {
    const { cafeId } = req.params;

    if (!cafeId) {
      return res(ctx.status(400));
    }
    if (+cafeId !== 1) {
      return res(ctx.status(200), ctx.json({ customers: [] }));
    }

    return res(ctx.status(200), ctx.json({ customers: cafeCustomer }));
  }),

  // 리워드 사용 기능
  rest.patch('/customers/:customerId/rewards/:rewardId', async (req, res, ctx) => {
    const { customerId, rewardId } = req.params;
    const { cafeId, used } = await req.json();

    if (!customerId || !rewardId || !cafeId || !used) {
      return res(ctx.status(400));
    }

    return res(ctx.status(200));
  }),
];
