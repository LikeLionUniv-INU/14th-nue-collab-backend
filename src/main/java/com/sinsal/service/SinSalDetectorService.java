package com.sinsal.service;

import com.sinsal.model.EarthlyBranch;
import com.sinsal.model.HeavenlyStem;
import com.sinsal.model.SinSalInfo;
import com.sinsal.model.ThreePillars;
import com.sinsal.model.SinSalType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 신살 탐지 서비스 — sin_sal.ts의 findSinSals() 포팅
 *
 * 구현된 신살 15종:
 *   천을귀인, 천덕귀인, 월덕귀인, 문창귀인, 학당귀인, 금여록,
 *   도화살, 역마살, 공망, 화개살, 원진살, 귀문관살, 양인살, 백호살, 고숙살
 */
@Service
public class SinSalDetectorService {

    private final SinSalDataService dataService;

    /**
     * 천을귀인 판단표: 일간 → 해당 지지 목록
     */
    private static final Map<HeavenlyStem, Set<EarthlyBranch>> CHEON_EUL_TABLE = Map.of(
        HeavenlyStem.GAP,    Set.of(EarthlyBranch.CHUK, EarthlyBranch.MI),
        HeavenlyStem.EUL,    Set.of(EarthlyBranch.JA,   EarthlyBranch.SIN),
        HeavenlyStem.BYEONG, Set.of(EarthlyBranch.HAE,  EarthlyBranch.YU),
        HeavenlyStem.JEONG,  Set.of(EarthlyBranch.HAE,  EarthlyBranch.YU),
        HeavenlyStem.MU,     Set.of(EarthlyBranch.CHUK, EarthlyBranch.MI),
        HeavenlyStem.GI,     Set.of(EarthlyBranch.JA,   EarthlyBranch.SIN),
        HeavenlyStem.GYEONG, Set.of(EarthlyBranch.CHUK, EarthlyBranch.MI),
        HeavenlyStem.SIN,    Set.of(EarthlyBranch.IN,   EarthlyBranch.O),
        HeavenlyStem.IM,     Set.of(EarthlyBranch.SA,   EarthlyBranch.MYO),
        HeavenlyStem.GYE,    Set.of(EarthlyBranch.SA,   EarthlyBranch.MYO)
    );

    /**
     * 공망 판단표: 일지 → 공망 지지 쌍
     */
    private static final Map<EarthlyBranch, Set<EarthlyBranch>> GONG_MANG_TABLE = Map.ofEntries(
        Map.entry(EarthlyBranch.JA,   Set.of(EarthlyBranch.SUL,  EarthlyBranch.HAE)),
        Map.entry(EarthlyBranch.CHUK, Set.of(EarthlyBranch.SUL,  EarthlyBranch.HAE)),
        Map.entry(EarthlyBranch.IN,   Set.of(EarthlyBranch.JA,   EarthlyBranch.CHUK)),
        Map.entry(EarthlyBranch.MYO,  Set.of(EarthlyBranch.JA,   EarthlyBranch.CHUK)),
        Map.entry(EarthlyBranch.JIN,  Set.of(EarthlyBranch.IN,   EarthlyBranch.MYO)),
        Map.entry(EarthlyBranch.SA,   Set.of(EarthlyBranch.IN,   EarthlyBranch.MYO)),
        Map.entry(EarthlyBranch.O,    Set.of(EarthlyBranch.JIN,  EarthlyBranch.SA)),
        Map.entry(EarthlyBranch.MI,   Set.of(EarthlyBranch.JIN,  EarthlyBranch.SA)),
        Map.entry(EarthlyBranch.SIN,  Set.of(EarthlyBranch.O,    EarthlyBranch.MI)),
        Map.entry(EarthlyBranch.YU,   Set.of(EarthlyBranch.O,    EarthlyBranch.MI)),
        Map.entry(EarthlyBranch.SUL,  Set.of(EarthlyBranch.SIN,  EarthlyBranch.YU)),
        Map.entry(EarthlyBranch.HAE,  Set.of(EarthlyBranch.SIN,  EarthlyBranch.YU))
    );

    /**
     * 문창귀인 판단표: 일간 → 해당 지지
     */
    private static final Map<HeavenlyStem, EarthlyBranch> MUN_CHANG_TABLE = Map.of(
        HeavenlyStem.GAP,    EarthlyBranch.SA,
        HeavenlyStem.EUL,    EarthlyBranch.O,
        HeavenlyStem.BYEONG, EarthlyBranch.SIN,
        HeavenlyStem.JEONG,  EarthlyBranch.YU,
        HeavenlyStem.MU,     EarthlyBranch.SIN,
        HeavenlyStem.GI,     EarthlyBranch.YU,
        HeavenlyStem.GYEONG, EarthlyBranch.HAE,
        HeavenlyStem.SIN,    EarthlyBranch.JA,
        HeavenlyStem.IM,     EarthlyBranch.IN,
        HeavenlyStem.GYE,    EarthlyBranch.MYO
    );

    /**
     * 학당귀인 판단표: 일간 → 장생(長生) 위치 지지
     */
    private static final Map<HeavenlyStem, EarthlyBranch> HAK_DANG_TABLE = Map.of(
        HeavenlyStem.GAP,    EarthlyBranch.HAE,
        HeavenlyStem.EUL,    EarthlyBranch.O,
        HeavenlyStem.BYEONG, EarthlyBranch.IN,
        HeavenlyStem.JEONG,  EarthlyBranch.YU,
        HeavenlyStem.MU,     EarthlyBranch.IN,
        HeavenlyStem.GI,     EarthlyBranch.YU,
        HeavenlyStem.GYEONG, EarthlyBranch.SA,
        HeavenlyStem.SIN,    EarthlyBranch.JA,
        HeavenlyStem.IM,     EarthlyBranch.SIN,
        HeavenlyStem.GYE,    EarthlyBranch.MYO
    );

    /**
     * 금여록 판단표: 일간 → 해당 지지
     */
    private static final Map<HeavenlyStem, EarthlyBranch> GEUM_YEO_TABLE = Map.of(
        HeavenlyStem.GAP,    EarthlyBranch.JIN,
        HeavenlyStem.EUL,    EarthlyBranch.SA,
        HeavenlyStem.BYEONG, EarthlyBranch.MI,
        HeavenlyStem.JEONG,  EarthlyBranch.SIN,
        HeavenlyStem.MU,     EarthlyBranch.MI,
        HeavenlyStem.GI,     EarthlyBranch.SIN,
        HeavenlyStem.GYEONG, EarthlyBranch.SUL,
        HeavenlyStem.SIN,    EarthlyBranch.HAE,
        HeavenlyStem.IM,     EarthlyBranch.CHUK,
        HeavenlyStem.GYE,    EarthlyBranch.IN
    );

    /**
     * 양인살 판단표: 일간 → 제왕(帝旺) 위치 지지
     */
    private static final Map<HeavenlyStem, EarthlyBranch> YANG_IN_TABLE = Map.of(
        HeavenlyStem.GAP,    EarthlyBranch.MYO,
        HeavenlyStem.EUL,    EarthlyBranch.JIN,
        HeavenlyStem.BYEONG, EarthlyBranch.O,
        HeavenlyStem.JEONG,  EarthlyBranch.MI,
        HeavenlyStem.MU,     EarthlyBranch.O,
        HeavenlyStem.GI,     EarthlyBranch.MI,
        HeavenlyStem.GYEONG, EarthlyBranch.YU,
        HeavenlyStem.SIN,    EarthlyBranch.SUL,
        HeavenlyStem.IM,     EarthlyBranch.JA,
        HeavenlyStem.GYE,    EarthlyBranch.CHUK
    );

    /**
     * 백호살 판단표: 일간 → 해당 지지
     */
    private static final Map<HeavenlyStem, EarthlyBranch> BAEK_HO_TABLE = Map.of(
        HeavenlyStem.GAP,    EarthlyBranch.JIN,
        HeavenlyStem.EUL,    EarthlyBranch.SA,
        HeavenlyStem.BYEONG, EarthlyBranch.O,
        HeavenlyStem.JEONG,  EarthlyBranch.MI,
        HeavenlyStem.MU,     EarthlyBranch.O,
        HeavenlyStem.GI,     EarthlyBranch.MI,
        HeavenlyStem.GYEONG, EarthlyBranch.SUL,
        HeavenlyStem.SIN,    EarthlyBranch.HAE,
        HeavenlyStem.IM,     EarthlyBranch.JA,
        HeavenlyStem.GYE,    EarthlyBranch.CHUK
    );

    /**
     * 천덕귀인 판단표: 월지 → 천간 대상 (8개월)
     */
    private static final Map<EarthlyBranch, HeavenlyStem> CHEON_DEOK_STEM_TABLE = Map.of(
        EarthlyBranch.IN,   HeavenlyStem.JEONG,
        EarthlyBranch.JIN,  HeavenlyStem.IM,
        EarthlyBranch.SA,   HeavenlyStem.SIN,
        EarthlyBranch.MI,   HeavenlyStem.GAP,
        EarthlyBranch.SIN,  HeavenlyStem.GYE,
        EarthlyBranch.SUL,  HeavenlyStem.BYEONG,
        EarthlyBranch.HAE,  HeavenlyStem.EUL,
        EarthlyBranch.CHUK, HeavenlyStem.GYEONG
    );

    /**
     * 천덕귀인 판단표: 월지 → 지지 대상 (4개월)
     */
    private static final Map<EarthlyBranch, EarthlyBranch> CHEON_DEOK_BRANCH_TABLE = Map.of(
        EarthlyBranch.MYO, EarthlyBranch.SIN,
        EarthlyBranch.O,   EarthlyBranch.HAE,
        EarthlyBranch.YU,  EarthlyBranch.IN,
        EarthlyBranch.JA,  EarthlyBranch.SA
    );

    public SinSalDetectorService(SinSalDataService dataService) {
        this.dataService = dataService;
    }

    /**
     * 연/월/일주로부터 신살 목록 반환
     */
    public List<SinSalInfo> detect(ThreePillars pillars) {
        HeavenlyStem dayStem    = pillars.getDayPillar().getStem();
        EarthlyBranch dayBranch = pillars.getDayPillar().getBranch();
        EarthlyBranch monthBranch = pillars.getMonthPillar().getBranch();
        EarthlyBranch yearBranch  = pillars.getYearPillar().getBranch();

        Set<EarthlyBranch> branches = EnumSet.of(
            pillars.getYearPillar().getBranch(),
            pillars.getMonthPillar().getBranch(),
            pillars.getDayPillar().getBranch()
        );
        Set<HeavenlyStem> stems = EnumSet.of(
            pillars.getYearPillar().getStem(),
            pillars.getMonthPillar().getStem(),
            pillars.getDayPillar().getStem()
        );

        List<SinSalInfo> result = new ArrayList<>();

        // 기존 7종
        if (hasCheonEulGwiIn(dayStem, branches))   result.add(dataService.get("cheon_eul_gwi_in"));
        if (hasDoHwaSal(branches))                  result.add(dataService.get("do_hwa_sal"));
        if (hasYeokMaSal(branches))                 result.add(dataService.get("yeok_ma_sal"));
        if (hasGongMang(dayBranch, branches))       result.add(dataService.get("gong_mang"));
        if (hasHwaGaeSal(branches))                 result.add(dataService.get("hwa_gae_sal"));
        if (hasWonJinSal(branches))                 result.add(dataService.get("won_jin_sal"));
        if (hasGwiMunGwanSal(branches))             result.add(dataService.get("gwi_mun_gwan_sal"));

        // 추가 8종
        if (hasCheonDeokGwiIn(monthBranch, stems, branches)) result.add(dataService.get("cheon_deok_gwi_in"));
        if (hasWolDeokGwiIn(monthBranch, stems))              result.add(dataService.get("wol_deok_gwi_in"));
        if (hasMunChangGwiIn(dayStem, branches))              result.add(dataService.get("mun_chang_gwi_in"));
        if (hasHakDangGwiIn(dayStem, branches))               result.add(dataService.get("hak_dang_gwi_in"));
        if (hasGeumYeoRok(dayStem, branches))                 result.add(dataService.get("geum_yeo_rok"));
        if (hasYangInSal(dayStem, branches))                  result.add(dataService.get("yang_in_sal"));
        if (hasBaekHoSal(dayStem, branches))                  result.add(dataService.get("baek_ho_sal"));
        if (hasGwaSukSal(yearBranch, branches))               result.add(dataService.get("gwa_suk_sal"));

        if (result.size() <= 6) {
            return result;
        }

        List<SinSalInfo> luckyList = result.stream()
                .filter(s -> s.getType() == SinSalType.LUCKY)
                .collect(Collectors.toList());
        List<SinSalInfo> unluckyList = result.stream()
                .filter(s -> s.getType() == SinSalType.UNLUCKY)
                .collect(Collectors.toList());

        Collections.shuffle(luckyList);
        Collections.shuffle(unluckyList);

        List<SinSalInfo> limited = new ArrayList<>();
        int li = 0, ui = 0;
        while (limited.size() < 6) {
            if (li < luckyList.size()) limited.add(luckyList.get(li++));
            if (limited.size() < 6 && ui < unluckyList.size())
                limited.add(unluckyList.get(ui++));
        }

        return limited;
    }

    // ── 개별 신살 판단 ──────────────────────────────────────────

    /** 천을귀인: 일간 기준 해당 지지가 사주에 있으면 */
    private boolean hasCheonEulGwiIn(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        Set<EarthlyBranch> targets = CHEON_EUL_TABLE.get(dayStem);
        return targets != null && branches.stream().anyMatch(targets::contains);
    }

    /**
     * 도화살
     *   인오술 삼합에 묘가 있거나
     *   사유축 삼합에 오가 있거나
     *   신자진 삼합에 유가 있거나
     *   해묘미 삼합에 자가 있으면
     */
    private boolean hasDoHwaSal(Set<EarthlyBranch> b) {
        return (anyOf(b, EarthlyBranch.IN, EarthlyBranch.O, EarthlyBranch.SUL)  && b.contains(EarthlyBranch.MYO)) ||
               (anyOf(b, EarthlyBranch.SA, EarthlyBranch.YU, EarthlyBranch.CHUK) && b.contains(EarthlyBranch.O))   ||
               (anyOf(b, EarthlyBranch.SIN, EarthlyBranch.JA, EarthlyBranch.JIN) && b.contains(EarthlyBranch.YU))  ||
               (anyOf(b, EarthlyBranch.HAE, EarthlyBranch.MYO, EarthlyBranch.MI) && b.contains(EarthlyBranch.JA));
    }

    /**
     * 역마살
     *   인오술 삼합에 신이 있거나
     *   사유축 삼합에 해가 있거나
     *   신자진 삼합에 인이 있거나
     *   해묘미 삼합에 사가 있으면
     */
    private boolean hasYeokMaSal(Set<EarthlyBranch> b) {
        return (anyOf(b, EarthlyBranch.IN, EarthlyBranch.O, EarthlyBranch.SUL)   && b.contains(EarthlyBranch.SIN)) ||
               (anyOf(b, EarthlyBranch.SA, EarthlyBranch.YU, EarthlyBranch.CHUK) && b.contains(EarthlyBranch.HAE)) ||
               (anyOf(b, EarthlyBranch.SIN, EarthlyBranch.JA, EarthlyBranch.JIN) && b.contains(EarthlyBranch.IN))  ||
               (anyOf(b, EarthlyBranch.HAE, EarthlyBranch.MYO, EarthlyBranch.MI) && b.contains(EarthlyBranch.SA));
    }

    /** 공망: 일지 기준 공망 지지가 사주에 있으면 */
    private boolean hasGongMang(EarthlyBranch dayBranch, Set<EarthlyBranch> branches) {
        Set<EarthlyBranch> gongMang = GONG_MANG_TABLE.get(dayBranch);
        return gongMang != null && branches.stream().anyMatch(gongMang::contains);
    }

    /**
     * 화개살
     *   인오술 삼합에 술이 있거나
     *   사유축 삼합에 축이 있거나
     *   신자진 삼합에 진이 있거나
     *   해묘미 삼합에 미가 있으면
     */
    private boolean hasHwaGaeSal(Set<EarthlyBranch> b) {
        return (anyOf(b, EarthlyBranch.IN, EarthlyBranch.O)   && b.contains(EarthlyBranch.SUL)) ||
               (anyOf(b, EarthlyBranch.SA, EarthlyBranch.YU)  && b.contains(EarthlyBranch.CHUK)) ||
               (anyOf(b, EarthlyBranch.SIN, EarthlyBranch.JA) && b.contains(EarthlyBranch.JIN)) ||
               (anyOf(b, EarthlyBranch.HAE, EarthlyBranch.MYO) && b.contains(EarthlyBranch.MI));
    }

    /**
     * 원진살: 육충 관계 쌍이 사주에 모두 있으면
     *   자-오, 축-미, 인-신, 묘-유, 진-술, 사-해
     */
    private boolean hasWonJinSal(Set<EarthlyBranch> b) {
        EarthlyBranch[][] pairs = {
            {EarthlyBranch.JA, EarthlyBranch.O},
            {EarthlyBranch.CHUK, EarthlyBranch.MI},
            {EarthlyBranch.IN, EarthlyBranch.SIN},
            {EarthlyBranch.MYO, EarthlyBranch.YU},
            {EarthlyBranch.JIN, EarthlyBranch.SUL},
            {EarthlyBranch.SA, EarthlyBranch.HAE}
        };
        for (EarthlyBranch[] pair : pairs) {
            if (b.contains(pair[0]) && b.contains(pair[1])) return true;
        }
        return false;
    }

    /**
     * 귀문관살: 인·신·사·해 중 2개 이상 있으면
     */
    private boolean hasGwiMunGwanSal(Set<EarthlyBranch> b) {
        long count = List.of(EarthlyBranch.IN, EarthlyBranch.SIN, EarthlyBranch.SA, EarthlyBranch.HAE)
                        .stream().filter(b::contains).count();
        return count >= 2;
    }

    // ── 추가 8종 신살 판단 ──────────────────────────────────────

    /** 천덕귀인: 월지 기준 해당 천간 또는 지지가 사주에 있으면 */
    private boolean hasCheonDeokGwiIn(EarthlyBranch monthBranch, Set<HeavenlyStem> stems, Set<EarthlyBranch> branches) {
        HeavenlyStem stemTarget = CHEON_DEOK_STEM_TABLE.get(monthBranch);
        if (stemTarget != null && stems.contains(stemTarget)) return true;
        EarthlyBranch branchTarget = CHEON_DEOK_BRANCH_TABLE.get(monthBranch);
        return branchTarget != null && branches.contains(branchTarget);
    }

    /** 월덕귀인: 월지 삼합 그룹 기준 해당 천간이 사주에 있으면 */
    private boolean hasWolDeokGwiIn(EarthlyBranch monthBranch, Set<HeavenlyStem> stems) {
        HeavenlyStem target = switch (monthBranch) {
            case IN, O, SUL    -> HeavenlyStem.BYEONG;
            case SIN, JA, JIN  -> HeavenlyStem.IM;
            case HAE, MYO, MI  -> HeavenlyStem.GAP;
            case SA, YU, CHUK  -> HeavenlyStem.GYEONG;
        };
        return stems.contains(target);
    }

    /** 문창귀인: 일간 기준 해당 지지가 사주에 있으면 */
    private boolean hasMunChangGwiIn(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        EarthlyBranch target = MUN_CHANG_TABLE.get(dayStem);
        return target != null && branches.contains(target);
    }

    /** 학당귀인: 일간 기준 장생 위치 지지가 사주에 있으면 */
    private boolean hasHakDangGwiIn(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        EarthlyBranch target = HAK_DANG_TABLE.get(dayStem);
        return target != null && branches.contains(target);
    }

    /** 금여록: 일간 기준 해당 지지가 사주에 있으면 */
    private boolean hasGeumYeoRok(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        EarthlyBranch target = GEUM_YEO_TABLE.get(dayStem);
        return target != null && branches.contains(target);
    }

    /** 양인살: 일간 기준 제왕 위치 지지가 사주에 있으면 */
    private boolean hasYangInSal(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        EarthlyBranch target = YANG_IN_TABLE.get(dayStem);
        return target != null && branches.contains(target);
    }

    /** 백호살: 일간 기준 해당 지지가 사주에 있으면 */
    private boolean hasBaekHoSal(HeavenlyStem dayStem, Set<EarthlyBranch> branches) {
        EarthlyBranch target = BAEK_HO_TABLE.get(dayStem);
        return target != null && branches.contains(target);
    }

    /** 고숙살: 연지 삼합 그룹 기준 해당 지지가 사주에 있으면 */
    private boolean hasGwaSukSal(EarthlyBranch yearBranch, Set<EarthlyBranch> branches) {
        EarthlyBranch target = switch (yearBranch) {
            case IN, MYO, JIN  -> EarthlyBranch.SA;
            case SA, O, MI     -> EarthlyBranch.SIN;
            case SIN, YU, SUL  -> EarthlyBranch.HAE;
            case HAE, JA, CHUK -> EarthlyBranch.IN;
        };
        return branches.contains(target);
    }

    private boolean anyOf(Set<EarthlyBranch> branches, EarthlyBranch... candidates) {
        for (EarthlyBranch c : candidates) {
            if (branches.contains(c)) return true;
        }
        return false;
    }
}
