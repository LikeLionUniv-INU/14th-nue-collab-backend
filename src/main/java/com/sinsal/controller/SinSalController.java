package com.sinsal.controller;

import com.sinsal.dto.ErrorResponse;
import com.sinsal.dto.SinSalResponse;
import com.sinsal.exception.BusinessException;
import com.sinsal.exception.ErrorCode;
import com.sinsal.model.SinSalInfo;
import com.sinsal.model.ThreePillars;
import com.sinsal.service.SajuCalculatorService;
import com.sinsal.service.SinSalDetectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SinSalController {

    private final SajuCalculatorService sajuCalculator;
    private final SinSalDetectorService sinSalDetector;

    public SinSalController(SajuCalculatorService sajuCalculator,
                            SinSalDetectorService sinSalDetector) {
        this.sajuCalculator = sajuCalculator;
        this.sinSalDetector = sinSalDetector;
    }

    /**
     * GET /api/sinsals?birthDate=2002-04-12
     */

    @Operation(summary = "신살 분석")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상",
                    content = @Content(schema = @Schema(implementation = SinSalResponse.class))),
            @ApiResponse(responseCode = "400", description = """
                    잘못된 요청
                    - SINSAL_001: 생년월일은 필수입니다.
                    - SINSAL_002: 생년월일 형식이 올바르지 않습니다. (예: 2002-04-12)
                    - SINSAL_003: 생년월일은 과거 날짜여야 합니다.""",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = """
                    서버 내부 오류
                    - SINSAL_500: 서버 내부 오류가 발생했습니다.""",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/sinsals")
    public ResponseEntity<?> getSinSals(@Parameter(description = "생년월일", example = "2002-04-12") @RequestParam String birthDate) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCode.BIRTH_DATE_INVALID_FORMAT);
        }

        if (!parsedDate.isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCode.BIRTH_DATE_NOT_PAST);
        }

        ThreePillars pillars = sajuCalculator.calculate(parsedDate);
        List<SinSalInfo> sinSalInfos = sinSalDetector.detect(pillars);

        String pillarsStr = pillars.getYearPillar()  + "년 " +
                pillars.getMonthPillar() + "월 " +
                pillars.getDayPillar()   + "일";

        List<SinSalResponse.SinSalItem> items = sinSalInfos.stream()
                .map(SinSalResponse.SinSalItem::from)
                .toList();

        return ResponseEntity.ok(new SinSalResponse(parsedDate, pillarsStr, items));
    }
}