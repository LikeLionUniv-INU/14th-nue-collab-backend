package com.sinsal.service;

import com.sinsal.model.SinSalInfo;
import com.sinsal.model.SinSalType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 신살 설명 데이터 — sin_sal.ts의 SIN_SAL_DATA 포팅
 */
@Service
public class SinSalDataService {

    private final Map<String, SinSalInfo> data;

    public SinSalDataService() {
        data = Map.ofEntries(
            Map.entry("cheon_eul_gwi_in", new SinSalInfo(
                "cheon_eul_gwi_in", "천을귀인", "天乙貴人", SinSalType.LUCKY,
                "가장 강력한 길신으로 귀인의 도움을 받는 운",
                List.of("위기 상황에서 귀인의 도움", "사회적 명예와 지위 상승", "재난과 어려움을 피하는 운"),
                List.of("주변 사람들과의 인연을 소중히 하세요", "어려울 때 주저하지 말고 도움을 청하세요")
            )),
            Map.entry("cheon_deok_gwi_in", new SinSalInfo(
                "cheon_deok_gwi_in", "천덕귀인", "天德貴人", SinSalType.LUCKY,
                "하늘의 덕을 받아 재난을 면하는 운",
                List.of("자연재해나 사고로부터 보호", "어려운 상황에서 탈출", "건강운 상승"),
                List.of("감사하는 마음을 가지세요", "선행을 많이 하면 복이 배가됩니다")
            )),
            Map.entry("wol_deok_gwi_in", new SinSalInfo(
                "wol_deok_gwi_in", "월덕귀인", "月德貴人", SinSalType.LUCKY,
                "달의 덕으로 평안과 안정을 얻는 운",
                List.of("가정의 평화", "안정적인 삶", "여성과 인연이 좋음"),
                List.of("가족과의 시간을 소중히 하세요", "평화로운 환경을 조성하세요")
            )),
            Map.entry("mun_chang_gwi_in", new SinSalInfo(
                "mun_chang_gwi_in", "문창귀인", "文昌貴人", SinSalType.LUCKY,
                "학문과 문학적 재능이 뛰어난 운",
                List.of("학업 성취", "시험 합격운", "문학·예술 재능", "지혜와 총명함"),
                List.of("학문과 교육에 투자하세요", "글쓰기나 창작 활동을 시도해보세요")
            )),
            Map.entry("hak_dang_gwi_in", new SinSalInfo(
                "hak_dang_gwi_in", "학당귀인", "學堂貴人", SinSalType.LUCKY,
                "배움의 터전, 학업 성취가 높은 운",
                List.of("학습 능력 향상", "전문가로 성장", "연구 분야 적합"),
                List.of("평생 학습하는 자세를 유지하세요", "전문 분야를 깊이 파고드세요")
            )),
            Map.entry("geum_yeo_rok", new SinSalInfo(
                "geum_yeo_rok", "금여록", "金輿祿", SinSalType.LUCKY,
                "물질적 풍요와 안정된 재물운",
                List.of("재물운 상승", "안정적인 수입", "부동산운"),
                List.of("부동산이나 안정적인 투자를 고려하세요", "재물 관리를 철저히 하세요")
            )),
            Map.entry("hwa_gae_sal", new SinSalInfo(
                "hwa_gae_sal", "화개살", "華蓋殺", SinSalType.LUCKY,
                "예술적 재능과 종교적 성향, 고독함의 이중성",
                List.of("예술·종교·철학 분야 재능", "독창적 사고", "고독하고 외로울 수 있음", "신비로운 일에 관심"),
                List.of("창작이나 영적 활동에 집중하세요", "고독을 즐기되 고립되지 않도록 주의하세요")
            )),
            Map.entry("yang_in_sal", new SinSalInfo(
                "yang_in_sal", "양인살", "羊刃殺", SinSalType.UNLUCKY,
                "날카로운 칼날, 폭력성과 사고 위험",
                List.of("성격이 급하고 과격함", "사고나 수술 위험", "갈등과 충돌 많음", "단호함과 결단력 (긍정적 측면)"),
                List.of("감정 조절과 인내심을 기르세요", "위험한 일이나 장소를 피하세요", "운동이나 무술로 에너지를 분출하세요")
            )),
            Map.entry("do_hwa_sal", new SinSalInfo(
                "do_hwa_sal", "도화살", "桃花殺", SinSalType.UNLUCKY,
                "복숭아꽃 살, 이성 관계가 복잡하고 유혹 많음",
                List.of("이성에게 인기가 많음", "연애나 결혼 문제 복잡", "외모가 매력적", "유흥이나 향락에 빠질 위험"),
                List.of("이성 관계를 신중히 하세요", "유혹에 흔들리지 않도록 주의하세요", "배우자에 대한 신의를 지키세요")
            )),
            Map.entry("baek_ho_sal", new SinSalInfo(
                "baek_ho_sal", "백호살", "白虎殺", SinSalType.UNLUCKY,
                "흰 호랑이 살, 혈광지사와 질병 위험",
                List.of("사고나 부상 위험", "수술이나 피를 보는 일", "질병에 취약", "갑작스런 불행"),
                List.of("건강 관리를 철저히 하세요", "위험한 활동을 자제하세요", "정기 건강검진을 받으세요")
            )),
            Map.entry("yeok_ma_sal", new SinSalInfo(
                "yeok_ma_sal", "역마살", "驛馬殺", SinSalType.UNLUCKY,
                "역참의 말, 이동수가 많고 활동적",
                List.of("이사나 여행이 잦음", "직업 변동이 많음", "활동적이고 역동적", "안정성 부족"),
                List.of("변화를 긍정적으로 받아들이세요", "해외 진출이나 이동을 활용하세요", "안정이 필요하면 노력이 필요합니다")
            )),
            Map.entry("gwa_suk_sal", new SinSalInfo(
                "gwa_suk_sal", "고숙살", "孤宿殺", SinSalType.UNLUCKY,
                "고독하게 머무르는 살, 외로움과 고립",
                List.of("혼자 있는 시간이 많음", "배우자나 가족과 떨어져 지냄", "사회성 부족", "깊은 사색과 성찰"),
                List.of("사람들과의 교류를 의식적으로 늘리세요", "고독을 창조적 활동으로 승화하세요", "가족과의 유대를 강화하세요")
            )),
            Map.entry("gong_mang", new SinSalInfo(
                "gong_mang", "공망", "空亡", SinSalType.UNLUCKY,
                "허공의 망함, 허무함과 상실감",
                List.of("노력해도 성과가 없음", "허무함과 공허함", "손실과 실패", "집착을 버리는 계기 (긍정적 측면)"),
                List.of("욕심을 버리고 평정심을 유지하세요", "정신적 성장의 기회로 삼으세요", "집착하지 말고 흘러가듯 살아가세요")
            )),
            Map.entry("won_jin_sal", new SinSalInfo(
                "won_jin_sal", "원진살", "元辰殺", SinSalType.UNLUCKY,
                "원한과 적대 관계, 갈등과 대립",
                List.of("타인과의 갈등이 잦음", "원수나 적이 생기기 쉬움", "법적 분쟁 주의", "배신이나 배반을 경험"),
                List.of("언행을 조심하고 겸손하세요", "분쟁은 조기에 해결하세요", "법적 문제에 신중하세요")
            )),
            Map.entry("gwi_mun_gwan_sal", new SinSalInfo(
                "gwi_mun_gwan_sal", "귀문관살", "鬼門關殺", SinSalType.UNLUCKY,
                "귀신의 문, 신비롭고 불길한 기운",
                List.of("신비로운 체험", "악몽이나 환각", "심리적 불안", "영적 감수성 (긍정적 측면)"),
                List.of("정신 건강을 잘 관리하세요", "신비주의에 지나치게 빠지지 마세요", "명상과 수양을 통해 마음을 다스리세요")
            )),

                // 고정신살
                Map.entry("default_sal", new SinSalInfo(
                        "default_sal", "특별한 살", "特別殺", SinSalType.LUCKY,
                        "TODO: 기획 확정 후 설명 추가",
                        List.of("TODO: 기획 확정 후 효과 추가"),
                        List.of("TODO: 기획 확정 후 조언 추가")
                ))
        );
    }

    public SinSalInfo get(String key) {
        return data.get(key);
    }
}
