import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 200 },   // 10초만에 200명 폭주
        { duration: '1m', target: 200 },     // 200명 1분 유지
        { duration: '10s', target: 0 },      // 10초만에 0명
        { duration: '1m', target: 0 },       // 1분간 서버 회복 관찰
    ],
};

const birthDates = [
    '2000-02-29',
    '1995-07-15',
    '2002-04-12',
    '1990-01-01',
    '1988-12-25',
    '2001-06-30',
    '1999-03-14',
    '1985-11-11',
];

export default function () {
    const date = birthDates[Math.floor(Math.random() *
        birthDates.length)];
    const res =
        http.get(`https://api.9su.site/api/sinsals?birthDate=${date}`);

    check(res, {
        '응답 200': (r) => r.status === 200,
        '응답시간 2초 이내': (r) => r.timings.duration < 2000,
    });

    sleep(1);
}