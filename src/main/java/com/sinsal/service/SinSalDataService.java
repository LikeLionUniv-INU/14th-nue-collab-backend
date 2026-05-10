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
                List.of("위기 상황에서 귀인의 도움을 받습니다", "사회적 명예와 지위가 상승합니다", "재난과 어려움을 피할 수 있습니다"),
                List.of("주변 사람들과의 인연을 소중히 하세요", "어려울 때 주저하지 말고 도움을 청하세요"),
                "천을귀인이 보이는군... 위기의 순간 도움을 받게 될 것이야."
            )),
            Map.entry("cheon_deok_gwi_in", new SinSalInfo(
                "cheon_deok_gwi_in", "천덕귀인", "天德貴人", SinSalType.LUCKY,
                "하늘의 덕을 받아 재난을 면하는 운",
                List.of("자연재해나 사고로부터 보호받습니다", "어려운 상황에서 벗어날 수 있습니다", "건강운이 상승합니다"),
                List.of("감사하는 마음을 가지세요", "선행을 많이 하면 복이 배가됩니다"),
                "천덕귀인이라... 하늘이 너를 지켜주고 있구나."
            )),
            Map.entry("wol_deok_gwi_in", new SinSalInfo(
                "wol_deok_gwi_in", "월덕귀인", "月德貴人", SinSalType.LUCKY,
                "달의 덕으로 평안과 안정을 얻는 운",
                List.of("가정에 평화가 깃듭니다", "안정적인 삶을 누릴 수 있습니다", "여성과 좋은 인연을 맺습니다"),
                List.of("가족과의 시간을 소중히 하세요", "평화로운 환경을 조성하세요"),
                "월덕귀인이 함께하는구나... 평안한 삶이 너를 기다리고 있어."
            )),
            Map.entry("mun_chang_gwi_in", new SinSalInfo(
                "mun_chang_gwi_in", "문창귀인", "文昌貴人", SinSalType.LUCKY,
                "학문과 문학적 재능이 뛰어난 운",
                List.of("학업에서 높은 성취를 이룹니다", "시험에 합격할 운이 있습니다", "문학·예술 분야에서 재능을 발휘합니다", "지혜롭고 총명한 기운을 타고났습니다"),
                List.of("학문과 교육에 투자하세요", "글쓰기나 창작 활동을 시도해보세요"),
                "문창귀인이 빛나고 있어... 학문의 길에서 크게 성취할 운명이구나."
            )),
            Map.entry("hak_dang_gwi_in", new SinSalInfo(
                "hak_dang_gwi_in", "학당귀인", "學堂貴人", SinSalType.LUCKY,
                "배움의 길에서 높은 성취를 이루는 운",
                List.of("학습 능력이 뛰어나게 향상됩니다", "전문가로 성장할 수 있습니다", "연구 분야에서 두각을 나타냅니다"),
                List.of("평생 학습하는 자세를 유지하세요", "전문 분야를 깊이 파고드세요"),
                "학당귀인이 보이는군... 배움을 놓지 않으면 큰 인물이 될 것이야."
            )),
            Map.entry("geum_yeo_rok", new SinSalInfo(
                "geum_yeo_rok", "금여록", "金輿祿", SinSalType.LUCKY,
                "물질적 풍요와 안정된 재물을 얻는 운",
                List.of("재물운이 크게 상승합니다", "안정적인 수입을 얻을 수 있습니다", "부동산운이 좋습니다"),
                List.of("부동산이나 안정적인 투자를 고려하세요", "재물 관리를 철저히 하세요"),
                "금여록이라... 재물의 기운이 넘치는구나. 풍요로운 삶을 누리게 될 거야."
            )),
            Map.entry("hwa_gae_sal", new SinSalInfo(
                "hwa_gae_sal", "화개살", "華蓋殺", SinSalType.LUCKY,
                "예술적 재능과 영적 감수성이 뛰어난 운",
                List.of("예술·종교·철학 분야에서 재능을 발휘합니다", "독창적인 사고력을 지니고 있습니다", "신비로운 분야에서 두각을 나타냅니다"),
                List.of("창작이나 영적 활동에 집중하세요", "고독을 즐기되 고립되지 않도록 주의하세요"),
                "화개살이 피었구나... 예술과 영성의 재능이 빛나고 있어."
            )),
            Map.entry("yang_in_sal", new SinSalInfo(
                "yang_in_sal", "양인살", "羊刃殺", SinSalType.UNLUCKY,
                "날카로운 기운으로 사고와 충돌을 부르는 살",
                List.of("성격이 급하고 과격해질 수 있습니다", "사고나 수술의 위험이 있습니다", "주변과 갈등과 충돌이 잦습니다"),
                List.of("감정 조절과 인내심을 기르세요", "위험한 일이나 장소를 피하세요", "운동이나 무술로 에너지를 분출하세요"),
                "양인살이 서려 있구나... 날카로운 기운을 다스려야 할 것이야."
            )),
            Map.entry("do_hwa_sal", new SinSalInfo(
                "do_hwa_sal", "도화살", "桃花殺", SinSalType.UNLUCKY,
                "이성 관계가 복잡하고 유혹이 많은 살",
                List.of("연애나 결혼 문제가 복잡해질 수 있습니다", "유흥이나 향락에 빠질 위험이 있습니다", "이성 문제로 구설수에 오를 수 있습니다"),
                List.of("이성 관계를 신중히 하세요", "유혹에 흔들리지 않도록 주의하세요", "배우자에 대한 신의를 지키세요"),
                "도화살이 끼었구나... 이성의 유혹을 조심해야 할 것이야."
            )),
            Map.entry("baek_ho_sal", new SinSalInfo(
                "baek_ho_sal", "백호살", "白虎殺", SinSalType.UNLUCKY,
                "혈광지사와 질병의 위험을 부르는 살",
                List.of("사고나 부상의 위험이 있습니다", "수술이나 피를 볼 수 있습니다", "질병에 취약할 수 있습니다", "갑작스러운 불행이 닥칠 수 있습니다"),
                List.of("건강 관리를 철저히 하세요", "위험한 활동을 자제하세요", "정기 건강검진을 받으세요"),
                "백호살이 도사리고 있구나... 건강과 안전에 각별히 신경 써야 해."
            )),
            Map.entry("yeok_ma_sal", new SinSalInfo(
                "yeok_ma_sal", "역마살", "驛馬殺", SinSalType.UNLUCKY,
                "잦은 이동과 변동으로 안정을 잃는 살",
                List.of("이사나 여행이 잦아 정착이 어렵습니다", "직업 변동이 많아 불안정합니다", "한곳에 머무르기 힘든 기운이 있습니다"),
                List.of("변화를 긍정적으로 받아들이세요", "해외 진출이나 이동을 활용하세요", "안정이 필요하면 노력이 필요합니다"),
                "역마살이 뛰고 있구나... 한곳에 머무르기 어려운 운명이야."
            )),
            Map.entry("gwa_suk_sal", new SinSalInfo(
                "gwa_suk_sal", "고숙살", "孤宿殺", SinSalType.UNLUCKY,
                "외로움과 고립 속에 머무르는 살",
                List.of("혼자 있는 시간이 많아집니다", "배우자나 가족과 떨어져 지낼 수 있습니다", "사회성이 부족해질 수 있습니다"),
                List.of("사람들과의 교류를 의식적으로 늘리세요", "고독을 창조적 활동으로 승화하세요", "가족과의 유대를 강화하세요"),
                "고숙살이 깃들어 있구나... 외로움이 따라다니니 주변을 잘 살펴야 해."
            )),
            Map.entry("gong_mang", new SinSalInfo(
                "gong_mang", "공망", "空亡", SinSalType.UNLUCKY,
                "노력이 허무하게 사라지는 살",
                List.of("노력해도 성과를 얻기 어렵습니다", "허무함과 공허함을 느끼기 쉽습니다", "손실과 실패를 겪을 수 있습니다"),
                List.of("욕심을 버리고 평정심을 유지하세요", "정신적 성장의 기회로 삼으세요", "집착하지 말고 흘러가듯 살아가세요"),
                "공망이 자리하고 있구나... 욕심을 내려놓아야 길이 보일 것이야."
            )),
            Map.entry("won_jin_sal", new SinSalInfo(
                "won_jin_sal", "원진살", "元辰殺", SinSalType.UNLUCKY,
                "원한과 갈등이 끊이지 않는 살",
                List.of("타인과의 갈등이 잦습니다", "원수나 적이 생기기 쉽습니다", "법적 분쟁에 휘말릴 수 있습니다", "배신이나 배반을 경험할 수 있습니다"),
                List.of("언행을 조심하고 겸손하세요", "분쟁은 조기에 해결하세요", "법적 문제에 신중하세요"),
                "원진살이 얽혀 있구나... 사람과의 관계에서 늘 조심해야 할 것이야."
            )),
            Map.entry("gwi_mun_gwan_sal", new SinSalInfo(
                "gwi_mun_gwan_sal", "귀문관살", "鬼門關殺", SinSalType.UNLUCKY,
                "불길한 기운과 심리적 불안을 부르는 살",
                List.of("악몽이나 환각에 시달릴 수 있습니다", "심리적 불안감이 커질 수 있습니다", "불길한 기운에 영향을 받기 쉽습니다"),
                List.of("정신 건강을 잘 관리하세요", "신비주의에 지나치게 빠지지 마세요", "명상과 수양을 통해 마음을 다스리세요"),
                "귀문관살이 드리워져 있구나... 마음을 단단히 다잡아야 할 것이야."
            )),

                // 고정신살
                Map.entry("default_sal", new SinSalInfo(
                        "default_sal", "특별한 살", "特別殺", SinSalType.LUCKY,
                        "정해진 틀 없이 스스로 길을 만들어가는 운",
                        List.of("운명의 제약 없이 자유롭게 살아갑니다", "본인의 의지대로 삶을 설계할 수 있습니다", "어떤 분야든 도전할 수 있는 가능성이 열려 있습니다"),
                        List.of("자신의 선택을 믿고 나아가세요", "어떤 길이든 당신의 의지로 개척할 수 있습니다"),
                        "허허, 2%의 확률을 뚫고 특별한 살이 나왔구나... 네 운명은 네가 직접 써 내려갈 것이야."
                ))
        );
    }

    public SinSalInfo get(String key) {
        return data.get(key);
    }
}
