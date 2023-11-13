import { PARAMS_ERROR_MESSAGE } from '../constants/magicString';
import {
  MutateReq,
  RewardReqBody,
  RewardIdParams,
  CustomerIdParams,
  CafeInfoReqBody,
  CafeIdParams,
} from '../types/api/request';
import { ownerInstance } from './axios';

export const patchReward = async ({
  params,
  body,
}: MutateReq<RewardReqBody, RewardIdParams & CustomerIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  await ownerInstance.patch<RewardReqBody>(
    `/admin/customers/${params.customerId}/rewards/${params.rewardId}`,
    body,
  );
};

export const patchCafeInfo = async ({ params, body }: MutateReq<CafeInfoReqBody, CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return (await ownerInstance.patch<CafeInfoReqBody>(`/admin/cafes/${params.cafeId}`, body)).data;
};
