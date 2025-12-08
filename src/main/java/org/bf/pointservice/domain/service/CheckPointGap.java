package org.bf.pointservice.domain.service;

/**
 * TODO : 뱃지 생성 시 포인트 구간 검증
 * - 뱃지 유지 구간이 기존에 있는 뱃지와 겹치지 않도록 설정
 * - 포인트 구간이 누락되어 뱃지를 보유할 수 없는 구간이 없도록 설정
 * */
public interface CheckPointGap {
    boolean validPointGap(long minPoint, long maxPoint);
}
