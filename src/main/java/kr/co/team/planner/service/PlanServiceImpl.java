package kr.co.team.planner.service;

import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.dto.PlanDTO;
import kr.co.team.planner.entity.Plan;
import kr.co.team.planner.entity.PlanImage;
import kr.co.team.planner.repository.PlanImageRepository;
import kr.co.team.planner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository; //final
    private final PlanImageRepository imageRepository; //final

    @Transactional
    @Override
    public Long register(PlanDTO planDTO) {
        System.out.println("planDTO:" + planDTO);
        Map<String, Object> entityMap = dtoToEntity(planDTO);
        Plan plan = (Plan)entityMap.get("plan");
        System.out.println("plan: " + plan);
        List<PlanImage> planImageList = (List<PlanImage>)entityMap.get("imgList");
        System.out.println("planImageList:" + planImageList);
        planRepository.save(plan);
        planImageList.forEach(planImage -> { imageRepository.save(planImage); });
        return plan.getPno();
    }

    @Override
    public PageResponseDTO<PlanDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("pno").descending());
        Page<Object[]> result = planRepository.getListPage(pageable);

        Function<Object[], PlanDTO> fn = (arr -> entitiesToDTO(
                (Plan)arr[0] ,
                (List<PlanImage>)(Arrays.asList((PlanImage)arr[1])),
                (Double)arr[2],
                (Long)arr[3])
        );
        System.out.println("#$%^*&^%$#여기까지 잘 오는지 확인!ㄸ$#$");
        System.out.println("result : " + result);
        System.out.println("fn : " + fn);
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public List<Plan> getOnlyPlanList() {
        List<Plan> plans = planRepository.findAll();
        return plans;
    }

    @Override
    public PlanDTO getPlan(Long pno) {
        List<Object[]> result = planRepository.getPlanWithAll(pno);
        Plan plan = (Plan)result.get(0)[0]; // Plan 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값

        List<PlanImage> planImageList = new ArrayList<>(); //plan 의 이미지 개수만큼 PlanImage 객체 필요
        result.forEach(arr -> {
            PlanImage planImage = (PlanImage)arr[1];
            planImageList.add(planImage);
        });

        System.out.println("plan 에 현재 들어있는 데이터 내용: " + plan);

        Double avg = (Double)result.get(0)[2]; //평균 평점 - 모든 Row가 동일한 값
        Long reviewCnt = (Long)result.get(0)[3]; //리뷰 개수 - 모든 Row가 동일한 값
        return entitiesToDTO(plan, planImageList, avg, reviewCnt);
    }

}